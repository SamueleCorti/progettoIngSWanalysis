package it.polimi.ingsw.communication.client.actions.secondaryActions;

import it.polimi.ingsw.communication.server.GameHandler;

public class PrintMarketAction implements SecondaryAction {
    @Override
    public String toString() {
        return "PrintMarketAction";
    }

    @Override
    public void execute(GameHandler gameHandler) {
        gameHandler.printMarket();
    }
}
