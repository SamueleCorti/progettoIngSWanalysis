package it.polimi.ingsw.client.actions.secondaryActions;

import it.polimi.ingsw.controller.GameHandler;

public class ViewGameboardAction implements SecondaryAction{
    @Override
    public void execute(GameHandler gameHandler) {
        gameHandler.viewGameBoard(-1);
    }

    public void execute(GameHandler gameHandler, int id) {
        gameHandler.viewGameBoard(id);
    }
}
