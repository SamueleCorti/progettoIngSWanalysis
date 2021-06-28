package it.polimi.ingsw.client.actions.secondaryActions;

import it.polimi.ingsw.controller.GameHandler;

/**
 *The player asks to see the gameboard
 */
public class ViewGameboardAction implements SecondaryAction{
    @Override
    public void execute(GameHandler gameHandler) {
        gameHandler.viewGameBoard(-1);
    }

    /**
     * Calls {@link GameHandler#viewGameBoard(int id)}
     */
    public void execute(GameHandler gameHandler, int id) {
        gameHandler.viewGameBoard(id);
    }
}
