package it.polimi.ingsw.communication.client.actions.mainActions;

import it.polimi.ingsw.communication.server.GameHandler;

public class EndTurn implements MainAction{
    @Override
    public String toString() {
        return "EndTurn";
    }

    @Override
    public void execute(GameHandler gameHandler) {

        gameHandler.endTurn();


    }
}
