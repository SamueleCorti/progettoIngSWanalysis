package it.polimi.ingsw.client.actions.mainActions;

import it.polimi.ingsw.client.actions.tertiaryActions.TertiaryAction;
import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.server.messages.printableMessages.IncorrectPhaseMessage;

import java.util.ArrayList;

public class WhiteToColorAction implements TertiaryAction {
    ArrayList<Integer> indexes;
    private boolean createdInGUI;

    public ArrayList<Integer> getIndexes() {
        return indexes;
    }

    @Override
    public String toString() {
        return "WhiteToColorAction{" +
                "resourceTypes=" + resourcesList() +
                '}';
    }

    private String resourcesList(){
        String s = "";
        for (Integer type: indexes) {
            s+=type;
        }
        return s;
    }

    public WhiteToColorAction(ArrayList<Integer> resourceTypes, boolean createdInGUI) {
        this.indexes = resourceTypes;
        this.createdInGUI=createdInGUI;
    }

    @Override
    public void execute(GameHandler gameHandler) {
        if(gameHandler.actionPerformedOfActivePlayer()==5){
            if(createdInGUI)    gameHandler.whiteToColorAction(indexes,-1);
            else    gameHandler.marketSpecialAction(indexes);
        }
        else gameHandler.sendMessageToActivePlayer(new IncorrectPhaseMessage());
    }

    @Override
    public void execute(GameHandler gameHandler, int clientID) {
        if(gameHandler.turnPhaseGivenNickname(clientID)==5){
            if(createdInGUI)    gameHandler.whiteToColorAction(indexes,clientID);
            else    gameHandler.marketSpecialAction(indexes);
        }
        else gameHandler.sendMessageToActivePlayer(new IncorrectPhaseMessage());
    }
}
