package it.polimi.ingsw.communication.client.actions.secondaryActions;

/**
 * Action used to activate a leader card. Contains the index to access the card via leader card zone.
 */
public class ActivateLeaderCardAction implements SecondaryAction{
    int index;

    public ActivateLeaderCardAction(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "ActivateLeaderCardAction{" +
                "index=" + index +
                '}';
    }
}
