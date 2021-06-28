package it.polimi.ingsw.client.actions.tertiaryActions;

import it.polimi.ingsw.client.actions.ExecutableAction;
import it.polimi.ingsw.controller.GameHandler;

/**
 * Mandatory actions (the player can't do anything until he performed these), can be called only under specific circumstances
 */
public interface TertiaryAction extends ExecutableAction {
    void execute(GameHandler gameHandler, int clientID);
}
