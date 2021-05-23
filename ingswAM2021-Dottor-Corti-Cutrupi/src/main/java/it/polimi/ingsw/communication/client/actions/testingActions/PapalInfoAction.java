package it.polimi.ingsw.communication.client.actions.testingActions;

import it.polimi.ingsw.communication.client.actions.secondaryActions.SecondaryAction;
import it.polimi.ingsw.communication.server.GameHandler;

public class PapalInfoAction implements SecondaryAction {

    @Override
    public void execute(GameHandler gameHandler) {
        gameHandler.papalInfo(gameHandler.activePlayer());
    }
}
