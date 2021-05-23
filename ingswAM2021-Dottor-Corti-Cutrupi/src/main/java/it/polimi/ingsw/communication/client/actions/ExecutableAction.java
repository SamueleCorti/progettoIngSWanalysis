package it.polimi.ingsw.communication.client.actions;

import it.polimi.ingsw.communication.server.GameHandler;

public interface ExecutableAction extends Action{
    void execute(GameHandler gameHandler);
}
