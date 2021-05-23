package it.polimi.ingsw.communication.client.actions.secondaryActions;

import it.polimi.ingsw.communication.server.GameHandler;

/**
 * Action used to view a dashboard. Contains the index to access the player via gameHandler.
 */
public class ViewDashboardAction implements SecondaryAction{
    int playerOrder;

    public ViewDashboardAction(int playerID) {
        this.playerOrder = playerID;
    }

    public ViewDashboardAction() {
        this.playerOrder = 0;
    }

    public int getPlayerOrder() {
        return playerOrder;
    }

    @Override
    public String toString() {
        return "ViewDashboardAction{" +
                "playerOrder=" + playerOrder +
                '}';
    }

    @Override
    public void execute(GameHandler gameHandler) {
        gameHandler.viewDashboard(playerOrder);
    }
}
