package it.polimi.ingsw.communication.client.actions.testingActions;

import it.polimi.ingsw.communication.client.actions.secondaryActions.SecondaryAction;

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
}
