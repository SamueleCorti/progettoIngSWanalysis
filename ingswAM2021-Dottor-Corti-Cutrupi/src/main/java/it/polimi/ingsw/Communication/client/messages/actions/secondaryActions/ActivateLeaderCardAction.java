package it.polimi.ingsw.Communication.client.messages.actions.secondaryActions;

public class ActivateLeaderCardAction implements SecondaryAction{
    int index;

    public ActivateLeaderCardAction(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
