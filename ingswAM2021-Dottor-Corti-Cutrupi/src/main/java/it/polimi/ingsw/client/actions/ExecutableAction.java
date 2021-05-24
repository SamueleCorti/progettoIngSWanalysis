package it.polimi.ingsw.client.actions;

import it.polimi.ingsw.controller.GameHandler;

public interface ExecutableAction extends Action{
    void execute(GameHandler gameHandler);
}
