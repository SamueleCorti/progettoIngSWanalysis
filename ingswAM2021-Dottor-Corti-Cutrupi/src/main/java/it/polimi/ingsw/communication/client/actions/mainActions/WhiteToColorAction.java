package it.polimi.ingsw.communication.client.actions.mainActions;

import it.polimi.ingsw.communication.client.actions.secondaryActions.SecondaryAction;
import it.polimi.ingsw.communication.server.GameHandler;
import it.polimi.ingsw.communication.server.messages.printableMessages.MainActionAlreadyDoneMessage;

import java.util.ArrayList;

public class WhiteToColorAction implements SecondaryAction {
    ArrayList<Integer> cardsToActivate = new ArrayList<>();

    public ArrayList<Integer> getCardsToActivate() {
        return cardsToActivate;
    }

    @Override
    public String toString() {
        return "WhiteToColorAction{" +
                "resourceTypes=" + resourcesList() +
                '}';
    }

    private String resourcesList(){
        String s = "";
        for (Integer type: cardsToActivate) {
            s+=type;
        }
        return s;
    }

    public WhiteToColorAction(ArrayList<Integer> resourceTypes) {
        this.cardsToActivate = resourceTypes;
    }

    @Override
    public void execute(GameHandler gameHandler) {
        if(gameHandler.actionPerformedOfActivePlayer()==5){
            gameHandler.marketSpecialAction(cardsToActivate);
        }
        else gameHandler.sendMessageToActivePlayer(new MainActionAlreadyDoneMessage());
    }
}
