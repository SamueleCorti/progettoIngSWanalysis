package it.polimi.ingsw.Communication.client.messages.actions.secondaryActions;

public class ViewDashboardAction implements SecondaryAction{
    int playerID;

    public ViewDashboardAction(int playerID) {
        this.playerID = playerID;
    }

    public int getPlayerID() {
        return playerID;
    }
}
