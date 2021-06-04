package it.polimi.ingsw.client.actions.tertiaryActions;

import it.polimi.ingsw.client.actions.ExecutableAction;
import it.polimi.ingsw.controller.GameHandler;

public interface TertiaryAction extends ExecutableAction {
    public void execute(GameHandler gameHandler, int clientID);
}
