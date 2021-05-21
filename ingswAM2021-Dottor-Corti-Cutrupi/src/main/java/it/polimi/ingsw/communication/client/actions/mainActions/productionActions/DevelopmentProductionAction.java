package it.polimi.ingsw.communication.client.actions.mainActions.productionActions;

import it.polimi.ingsw.communication.client.actions.mainActions.ProductionAction;

/**
 * Type of action created when the player decides to activate the production of on of his development cards
 */
public class DevelopmentProductionAction implements ProductionAction {
    private final int developmentCardZone;

    @Override
    public String toString() {
        return "DevelopmentProductionAction{" +
                "developmentCardZone=" + developmentCardZone +
                '}';
    }

    public DevelopmentProductionAction(int developmentCardZone) {
        this.developmentCardZone = developmentCardZone;
    }

    public int getDevelopmentCardZone() {
        return developmentCardZone;
    }
}
