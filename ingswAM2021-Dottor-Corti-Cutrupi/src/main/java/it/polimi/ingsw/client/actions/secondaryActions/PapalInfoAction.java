package it.polimi.ingsw.client.actions.secondaryActions;

import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.server.messages.printableMessages.PapalInfoMessage;

/**
 * Used when the player asks for info regarding the papal path
 */
public class PapalInfoAction implements SecondaryAction {

    @Override
    public void execute(GameHandler gameHandler) {
        gameHandler.sendMessageToActivePlayer(new PapalInfoMessage(gameHandler));
    }

}