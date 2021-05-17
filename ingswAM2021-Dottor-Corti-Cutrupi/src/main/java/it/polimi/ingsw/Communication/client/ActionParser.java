package it.polimi.ingsw.Communication.client;

import it.polimi.ingsw.Communication.client.actions.*;
import it.polimi.ingsw.Communication.client.actions.mainActions.DevelopmentAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.EndTurn;
import it.polimi.ingsw.Communication.client.actions.mainActions.MarketAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.productionActions.BaseProductionAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.productionActions.DevelopmentProductionAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.productionActions.LeaderProductionAction;
import it.polimi.ingsw.Communication.client.actions.secondaryActions.ActivateLeaderCardAction;
import it.polimi.ingsw.Communication.client.actions.secondaryActions.DiscardLeaderCard;
import it.polimi.ingsw.Communication.client.actions.secondaryActions.ViewDashboardAction;
import it.polimi.ingsw.Model.developmentcard.Color;
import it.polimi.ingsw.Model.resource.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Class whose purpose is to create {@link Action} messages from stdIn.
 */
public class ActionParser {

    /**
     * Constructor, called everytime the player writes something in stdIn.
     * @param input: string created by the stdIn that is going to get parsed
     * @return: the correctly parsed {@link Action}, created from the string
     */
    public Action parseInput(@NotNull String input){
        List<String> in = new ArrayList<>(Arrays.asList(input.split(" ")));
        String command = in.get(0);
        Action actionToSend;
        switch(command.toLowerCase()){

            case"endturn": {actionToSend = new EndTurn(); break;}

            case"help": {
                System.out.println("here is the list of commands you might insert:");
                actionToSend = null;
            }

            case "setupdiscard": {
                actionToSend= new DiscardTwoLeaderCardsAction(Integer.parseInt(in.get(1)), Integer.parseInt(in.get(2)));
                System.out.println("Discard action about to be sent to the game handler!");
                break;
            }

            case "headstart": {
                if (in.get(2)!=null)     actionToSend= new BonusResourcesAction(parseResource(in.get(1)),parseResource(in.get(2)));
                else if(in.get(1)!=null)    actionToSend= new BonusResourcesAction(parseResource(in.get(1)));
                else actionToSend=null;
                break;
            }

            case "activateleadercard":{
                actionToSend = new ActivateLeaderCardAction(Integer.parseInt(in.get(1)));
                break;
            }
            case "viewdashboard":{
                actionToSend = new ViewDashboardAction(Integer.parseInt(in.get(1)));
                break;
            }
            case "buydevelopmentcard":{
                actionToSend = new DevelopmentAction(colorParser(in.get(1)),Integer.parseInt(in.get(2)),Integer.parseInt(in.get(3)));
                break;
            }
            //the user inserts: marketaction, number of the row/column, and if is a row or a column
            case"market": {
                boolean isRow;
                //the 'else' false might cause problems
                if (in.get(2).equals("row")){isRow=true;}
                else {isRow=false;}
                actionToSend = new MarketAction(Integer.parseInt(in.get(1)),isRow);
                break;
            }
            case "developmentproduction":{
                actionToSend = new DevelopmentProductionAction(Integer.parseInt(in.get(1)));
                break;
            }
            case "leaderproduction":{
                actionToSend = new LeaderProductionAction(Integer.parseInt(in.get(1)),parseResource(in.get(2)));
                break;
            }
            case "printmarket":
                actionToSend= new PrintMarketAction();
                break;
            case "test":
                actionToSend= new TestAction("white");
                break;
            /*
            case "marketdoublewhitetocoloraction":{
                boolean bool;
                if (in.get(2).equals("row")){bool=true;}
                else {bool=false;}
                ArrayList <ResourceType> resourcesParsed= new ArrayList<ResourceType>();
                for(int i=3;i<in.size();i++){
                    resourcesParsed.add(parseResource(in.get(i)));
                }
                actionToSend = new MarketDoubleWhiteToColorAction(Integer.parseInt(in.get(1)),bool,resourcesParsed);
                break;
            }*/
            case "baseproductionaction":{
                ArrayList <ResourceType> resourcesParsed1= new ArrayList<ResourceType>();
                ArrayList <ResourceType> resourcesParsed2= new ArrayList<ResourceType>();
                int i;
                if (in.get(1).equals("used:")){
                    for(i=3;!in.get(i).equals("wanted:");i++){
                        resourcesParsed1.add(parseResource(in.get(i)));
                    }
                    while(i<in.size()){
                        resourcesParsed2.add(parseResource(in.get(i)));
                        i++;
                    }
                }
                actionToSend = new BaseProductionAction(resourcesParsed1,resourcesParsed1);
                break;
            }
            case "discardleader":
                actionToSend = new DiscardLeaderCard(Integer.parseInt(in.get(1)));
                break;
            case "wtcchoice":
                System.out.println("You may now insert what resources you want");
                actionToSend= null;
                break;
            default:{
                System.out.println("uncorrect action type inserted,try again");
                actionToSend = null;
                break;}
        }
        System.out.println("the action inserted is "+actionToSend);
        return actionToSend;
    }

    /**
     * Parser a string to a {@link Color}
     * @param colorToParse: string representing a color
     * @return {@link Color}
     */

    public Color colorParser(String colorToParse){
        switch(colorToParse.toLowerCase()){
            case "blue": return Color.Blue;
            case "yellow": return Color.Yellow;
            case "green": return Color.Green;
            case "purple": return Color.Purple;
        }
        return null;
    }


    /**
     * Parser a string to a {@link ResourceType}
     * @param string: string representing a resource type
     * @return {@link ResourceType}
     */

    public ResourceType parseResource(String string){
        switch (string.toLowerCase(Locale.ROOT)){
            case "coin": return ResourceType.Coin;
            case "stone": return ResourceType.Stone;
            case "servant": return ResourceType.Servant;
            case "shield": return ResourceType.Shield;
        }
        return null;
    }
}
