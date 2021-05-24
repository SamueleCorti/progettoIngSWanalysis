package it.polimi.ingsw.client.actions.secondaryActions;

import it.polimi.ingsw.controller.GameHandler;

public class ViewGameboardAction implements SecondaryAction{
    @Override
    public void execute(GameHandler gameHandler) {
        gameHandler.viewGameBoard();
    }
}
