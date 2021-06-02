package it.polimi.ingsw.client.actions.mainActions;

import it.polimi.ingsw.client.actions.secondaryActions.SecondaryAction;
import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.server.messages.printableMessages.IncorrectPhaseMessage;

import java.util.ArrayList;

public class WhiteToColorAction implements SecondaryAction {
    ArrayList<Integer> indexes = new ArrayList<>();
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
            if(createdInGUI)    gameHandler.whiteToColorAction(indexes);
            else    gameHandler.marketSpecialAction(indexes);
        }
        else gameHandler.sendMessageToActivePlayer(new IncorrectPhaseMessage());
    }
}
