package it.polimi.ingsw.Communication.client;

import it.polimi.ingsw.Communication.client.messages.NewTurnAction;
import it.polimi.ingsw.Communication.client.messages.QuitAction;
import it.polimi.ingsw.Communication.client.messages.actions.Action;
import it.polimi.ingsw.Communication.client.messages.actions.mainActions.MarketAction;
import org.jetbrains.annotations.NotNull;

public class ActionParser {


    public Action parseInput(@NotNull String input){
        String[] in = input.split(" ");
        String command = in[0];
        Action actionToSend;
        System.out.println("il nostro vettore in input è"+in[0]+in[1]+in[2]);
        switch(command.toLowerCase()){
            case"quit": {actionToSend = new QuitAction(); break;}

            //the user inserts: marketaction, number of the row/column, and if is a row or a column
            case"marketaction": {
                boolean bool;
                if (in[2].equals("row")){bool=true;}
                else {bool=false;}
                actionToSend = new MarketAction(Integer.parseInt(in[1]),bool);
                System.out.println("is a market action; we created a market action "+ actionToSend); break;
            }

            default:{
                System.out.println("probably a mistake");
                actionToSend = new NewTurnAction();
                break;}
        }
        return actionToSend;
    }

}
