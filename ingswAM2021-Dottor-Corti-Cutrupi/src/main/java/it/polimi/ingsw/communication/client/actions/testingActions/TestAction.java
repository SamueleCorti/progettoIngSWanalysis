package it.polimi.ingsw.communication.client.actions.testingActions;

import it.polimi.ingsw.communication.client.actions.secondaryActions.SecondaryAction;
import it.polimi.ingsw.communication.server.GameHandler;

public class TestAction implements SecondaryAction {
    private final String type;

    public TestAction(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "TestAction";
    }

    @Override
    public void execute(GameHandler gameHandler) {

    }
}
