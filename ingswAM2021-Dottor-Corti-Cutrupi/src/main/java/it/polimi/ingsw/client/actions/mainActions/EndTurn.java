package it.polimi.ingsw.client.actions.mainActions;

import it.polimi.ingsw.controller.GameHandler;

/**
 * Action sent to signal that the player wishes to end his turn
 */
public class EndTurn implements MainAction{

    @Override
    public void execute(GameHandler gameHandler) {

        gameHandler.endTurn();


    }
}
