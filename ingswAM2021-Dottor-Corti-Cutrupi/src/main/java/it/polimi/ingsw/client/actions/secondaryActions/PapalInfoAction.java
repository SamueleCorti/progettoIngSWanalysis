package it.polimi.ingsw.client.actions.secondaryActions;

import it.polimi.ingsw.controller.GameHandler;

public class PapalInfoAction implements SecondaryAction {

    @Override
    public void execute(GameHandler gameHandler) {
        gameHandler.papalInfo(gameHandler.activePlayer());
    }

}