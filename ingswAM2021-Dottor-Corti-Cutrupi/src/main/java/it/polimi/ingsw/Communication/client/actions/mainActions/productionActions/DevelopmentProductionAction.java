package it.polimi.ingsw.Communication.client.actions.mainActions.productionActions;

import it.polimi.ingsw.Communication.client.actions.mainActions.ProductionAction;

public class DevelopmentProductionAction implements ProductionAction {
    int developmentCardZone;

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
