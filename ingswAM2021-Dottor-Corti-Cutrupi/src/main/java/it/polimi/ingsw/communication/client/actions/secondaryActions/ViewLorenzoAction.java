package it.polimi.ingsw.communication.client.actions.secondaryActions;

import it.polimi.ingsw.communication.server.GameHandler;

public class ViewLorenzoAction implements SecondaryAction{
    @Override
    public void execute(GameHandler gameHandler) {
        gameHandler.viewLorenzo();
    }
}
