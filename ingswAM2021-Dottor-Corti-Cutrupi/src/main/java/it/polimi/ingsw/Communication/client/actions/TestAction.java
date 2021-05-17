package it.polimi.ingsw.Communication.client.actions;

import it.polimi.ingsw.Communication.client.actions.secondaryActions.SecondaryAction;

public class TestAction implements SecondaryAction {
    private final String type;

    public TestAction(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
