package it.polimi.ingsw.client.actions.mainActions;

import it.polimi.ingsw.controller.GameHandler;

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
