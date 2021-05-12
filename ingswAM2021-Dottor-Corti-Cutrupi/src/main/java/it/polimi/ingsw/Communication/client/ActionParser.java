package it.polimi.ingsw.Communication.client;

import it.polimi.ingsw.Communication.client.messages.NewTurnAction;
import it.polimi.ingsw.Communication.client.messages.QuitAction;
import it.polimi.ingsw.Communication.client.messages.actions.Action;
import it.polimi.ingsw.Communication.client.messages.actions.mainActions.DevelopmentAction;
import it.polimi.ingsw.Communication.client.messages.actions.mainActions.MarketAction;
import it.polimi.ingsw.Communication.client.messages.actions.mainActions.productionActions.DevelopmentProductionAction;
import it.polimi.ingsw.Communication.client.messages.actions.secondaryActions.ActivateLeaderCardAction;
import it.polimi.ingsw.Communication.client.messages.actions.secondaryActions.ViewDashboardAction;
import it.polimi.ingsw.developmentcard.Color;
import it.polimi.ingsw.resource.*;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class ActionParser {


    public Action parseInput(@NotNull String input){
        String[] in = input.split(" ");
        String command = in[0];
        Action actionToSend;
        System.out.println("il nostro vettore in input è"+in[0]+in[1]+in[2]);
        switch(command.toLowerCase()){

            case"quit": {actionToSend = new QuitAction(); break;}

            case "activateleadercardaction":{
                actionToSend = new ActivateLeaderCardAction(Integer.parseInt(in[1]));
                break;
            }
            case "viewdashboardaction":{
                actionToSend = new ViewDashboardAction(Integer.parseInt(in[1]));
                break;
            }
            case "developmentaction":{
                actionToSend = new DevelopmentAction(colorParser(in[1]),Integer.parseInt(in[2]),Integer.parseInt(in[3]));
                break;
            }
            //the user inserts: marketaction, number of the row/column, and if is a row or a column
            case"marketaction": {
                boolean bool;
                if (in[2].equals("row")){bool=true;}
                else {bool=false;}
                actionToSend = new MarketAction(Integer.parseInt(in[1]),bool);
                break;
            }
            //TODO: these methods that require array lists of resources are problematic
            case "marketdoublewhitetocoloraction":{
                actionToSend = new QuitAction();
                break;
            }
            case "baseproductionaction":{
                actionToSend = new QuitAction();
                break;
            }
            case "developmentproductionaction":{
                actionToSend = new DevelopmentProductionAction(Integer.parseInt(in[1]));
                break;
            }
            case "leaderproductionaction":{
                actionToSend = new QuitAction();
                break;
            }
            case "resourcescommunication":{
                actionToSend = new QuitAction();
                break;
            }
            default:{
                System.out.println("uncorrect input inserted");
                actionToSend = null;
                break;}
        }
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

    public Resource parseResource(String string){
        switch (string){
            case "coin": return new CoinResource();
            case "stone": return new StoneResource();
            case "servant": return new ServantResource();
            case "shield": return new ShieldResource();
        }
        return null;
    }
}
