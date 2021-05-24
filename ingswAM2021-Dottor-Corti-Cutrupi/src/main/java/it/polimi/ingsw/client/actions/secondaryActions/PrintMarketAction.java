package it.polimi.ingsw.client.actions.secondaryActions;

import it.polimi.ingsw.controller.GameHandler;

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
