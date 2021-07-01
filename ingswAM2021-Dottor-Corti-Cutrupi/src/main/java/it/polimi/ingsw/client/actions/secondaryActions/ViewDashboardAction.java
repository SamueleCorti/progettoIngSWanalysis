package it.polimi.ingsw.client.actions.secondaryActions;

import it.polimi.ingsw.controller.GameHandler;

/**
 * Action used to view a dashboard. Contains the index to access the player via gameHandler.
 */
public class ViewDashboardAction implements SecondaryAction{
    private final int playerOrder;

    /**
     * @param playerID: order of the player that created this request
     */
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
    public void execute(GameHandler gameHandler) {
        gameHandler.viewDashboard(playerOrder,-1);
    }

    /**
     * Shows the player his dashboard
     */
    public void execute(GameHandler gameHandler, int id) {

        gameHandler.viewDashboard(playerOrder,id);
    }
}
