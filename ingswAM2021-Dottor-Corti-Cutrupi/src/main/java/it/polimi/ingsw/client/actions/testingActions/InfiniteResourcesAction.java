package it.polimi.ingsw.client.actions.testingActions;

import it.polimi.ingsw.client.actions.secondaryActions.SecondaryAction;
import it.polimi.ingsw.controller.GameHandler;

public class InfiniteResourcesAction implements SecondaryAction {
    @Override
    public String toString() {
        return "InfiniteResourcesAction";
    }

    @Override
    public void execute(GameHandler gameHandler) {
            gameHandler.addInfiniteResources();
    }
}
