package it.polimi.ingsw.client.actions.secondaryActions;

import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.server.messages.printableMessages.PapalInfoMessage;

public class PapalInfoAction implements SecondaryAction {

    @Override
    public void execute(GameHandler gameHandler) {
        PapalInfoMessage message= new PapalInfoMessage(gameHandler);
    }

}