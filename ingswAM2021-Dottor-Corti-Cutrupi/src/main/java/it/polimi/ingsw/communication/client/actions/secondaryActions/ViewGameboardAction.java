package it.polimi.ingsw.communication.client.actions.secondaryActions;

import it.polimi.ingsw.communication.server.GameHandler;

public class ViewGameboardAction implements SecondaryAction{
    @Override
    public void execute(GameHandler gameHandler) {
        gameHandler.viewGameBoard();
    }

    public static class PapalInfoAction implements SecondaryAction {

        @Override
        public void execute(GameHandler gameHandler) {
            gameHandler.papalInfo(gameHandler.activePlayer());
        }
    }

    public static class ViewDepotsAction implements SecondaryAction {
        @Override
        public void execute(GameHandler gameHandler) {
                gameHandler.printDepotsOfActivePlayer();
        }
    }
}
