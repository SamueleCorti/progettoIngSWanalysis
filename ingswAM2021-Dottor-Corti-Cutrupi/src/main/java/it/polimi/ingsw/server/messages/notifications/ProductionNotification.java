package it.polimi.ingsw.server.messages.notifications;

import it.polimi.ingsw.client.shared.ClientSideSocket;

public class ProductionNotification implements Notification{
    private final boolean[] productions;

    public ProductionNotification(boolean[] productions) {
        this.productions = productions;
    }

    public boolean[] getProductions() {
        return productions;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {

    }
}
