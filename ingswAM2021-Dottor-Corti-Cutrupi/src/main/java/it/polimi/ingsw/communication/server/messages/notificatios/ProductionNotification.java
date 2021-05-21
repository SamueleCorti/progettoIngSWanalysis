package it.polimi.ingsw.communication.server.messages.notificatios;

public class ProductionNotification implements Notification{
    private final boolean[] productions;

    public ProductionNotification(boolean[] productions) {
        this.productions = productions;
    }

    public boolean[] getProductions() {
        return productions;
    }
}
