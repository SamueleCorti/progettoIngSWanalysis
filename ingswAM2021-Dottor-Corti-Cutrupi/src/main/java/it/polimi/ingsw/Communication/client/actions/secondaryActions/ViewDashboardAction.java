package it.polimi.ingsw.Communication.client.actions.secondaryActions;
/**
 * Action used to view a dashboard. Contains the index to access the player via gameHandler.
 */
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
