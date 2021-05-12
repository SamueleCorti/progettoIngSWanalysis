package it.polimi.ingsw.Communication.client.actions.secondaryActions;

public class ViewDashboardAction implements SecondaryAction{
    int playerID;

    public ViewDashboardAction(int playerID) {
        this.playerID = playerID;
    }

    public int getPlayerID() {
        return playerID;
    }

    @Override
    public String toString() {
        return "ViewDashboardAction{" +
                "playerID=" + playerID +
                '}';
    }
}
