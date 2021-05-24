package it.polimi.ingsw.communication.client.actions.secondaryActions;

import it.polimi.ingsw.communication.server.GameHandler;

public class ViewDepotsAction implements SecondaryAction {
    @Override
    public void execute(GameHandler gameHandler) {
        gameHandler.printDepotsOfActivePlayer();
    }
}
