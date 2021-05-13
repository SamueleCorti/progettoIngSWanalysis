package it.polimi.ingsw.Communication.client;

import it.polimi.ingsw.Communication.client.actions.QuitAction;
import it.polimi.ingsw.Communication.client.actions.Action;
import it.polimi.ingsw.Communication.client.actions.mainActions.DevelopmentAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.MarketAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.MarketDoubleWhiteToColorAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.productionActions.BaseProductionAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.productionActions.DevelopmentProductionAction;
import it.polimi.ingsw.Communication.client.actions.mainActions.productionActions.LeaderProductionAction;
import it.polimi.ingsw.Communication.client.actions.secondaryActions.ActivateLeaderCardAction;
import it.polimi.ingsw.Communication.client.actions.secondaryActions.ViewDashboardAction;
import it.polimi.ingsw.developmentcard.Color;
import it.polimi.ingsw.resource.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActionParser {


    public Action parseInput(@NotNull String input){
        List<String> in = new ArrayList<>(Arrays.asList(input.split(" ")));
        String command = in.get(0);
        Action actionToSend;
        switch(command.toLowerCase()){

            case"quit": {actionToSend = new QuitAction(); break;}

            case "activateleadercardaction":{
                actionToSend = new ActivateLeaderCardAction(Integer.parseInt(in.get(1)));
                break;
            }
            case "viewdashboardaction":{
                actionToSend = new ViewDashboardAction(Integer.parseInt(in.get(1)));
                break;
            }
            case "developmentaction":{
                actionToSend = new DevelopmentAction(colorParser(in.get(1)),Integer.parseInt(in.get(2)),Integer.parseInt(in.get(3)));
                break;
            }
            //the user inserts: marketaction, number of the row/column, and if is a row or a column
            case"marketaction": {
                boolean bool;
                //the 'else' false might cause problems
                if (in.get(2).equals("row")){bool=true;}
                else {bool=false;}
                actionToSend = new MarketAction(Integer.parseInt(in.get(1)),bool);
                break;
            }

            case "developmentproductionaction":{
                actionToSend = new DevelopmentProductionAction(Integer.parseInt(in.get(1)));
                break;
            }
            //error during send process
            case "leaderproductionaction":{
                actionToSend = new LeaderProductionAction(Integer.parseInt(in.get(1)),parseResource(in.get(2)));
                actionToSend=(LeaderProductionAction) actionToSend;
                break;
            }
            //error during send process
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
            }
            //error during send process
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
            default:{
                System.out.println("uncorrect action type inserted,try again");
                actionToSend = null;
                break;}
        }
        System.out.println("the action inserted is "+actionToSend);
        return actionToSend;
    }

    public Color colorParser(String colorToParse){
        switch(colorToParse.toLowerCase()){
            case "blue": return Color.Blue;
            case "yellow": return Color.Yellow;
            case "green": return Color.Green;
            case "purple": return Color.Purple;
        }
        return null;
    }

    public ResourceType parseResource(String string){
        switch (string){
            case "coin": return ResourceType.Coin;
            case "stone": return ResourceType.Stone;
            case "servant": return ResourceType.Servant;
            case "shield": return ResourceType.Shield;
        }
        return null;
    }
}
