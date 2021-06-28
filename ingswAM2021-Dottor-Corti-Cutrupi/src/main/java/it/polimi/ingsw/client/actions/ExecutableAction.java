package it.polimi.ingsw.client.actions;

import it.polimi.ingsw.controller.GameHandler;

/**
 * Interface extended by all type of actions, used to implement the method {@link #execute(GameHandler)}
 */
public interface ExecutableAction extends Action{
    /**
     * Performs the action
     * @param gameHandler: used to call the method used to implement the action performed
     */
    void execute(GameHandler gameHandler);
}
