package it.polimi.ingsw.Communication.client.actions.secondaryActions;

public class DiscardExcedingDepotAction implements SecondaryAction{
    private final int index;

    public int getIndex() {
        return index;
    }

    public DiscardExcedingDepotAction(int index) {
        this.index = index;
    }
}
