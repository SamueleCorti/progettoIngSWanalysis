package it.polimi.ingsw.server.messages.notifications;

public class ProductionNotification implements Notification{
    private final boolean[] productions;

    public ProductionNotification(boolean[] productions) {
        this.productions = productions;
    }

    public boolean[] getProductions() {
        return productions;
    }
}