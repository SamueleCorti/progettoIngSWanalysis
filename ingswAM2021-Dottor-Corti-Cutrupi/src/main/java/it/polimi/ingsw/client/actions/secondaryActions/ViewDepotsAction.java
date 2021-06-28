package it.polimi.ingsw.client.actions.secondaryActions;

import it.polimi.ingsw.controller.GameHandler;

/**
 * Created when the player wishes to see his depots (CLI) or refresh them (GUI)
 */
public class ViewDepotsAction implements SecondaryAction {
    @Override
    public void execute(GameHandler gameHandler) {
        gameHandler.printDepotsOfActivePlayer();
    }
}
