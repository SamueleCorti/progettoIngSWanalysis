package it.polimi.ingsw.client.actions.secondaryActions;

import it.polimi.ingsw.controller.GameHandler;

/**
 * Sends the player a string representing the market
 */
public class PrintMarketAction implements SecondaryAction {

    @Override
    public void execute(GameHandler gameHandler) {
        gameHandler.printMarket();
    }
}
