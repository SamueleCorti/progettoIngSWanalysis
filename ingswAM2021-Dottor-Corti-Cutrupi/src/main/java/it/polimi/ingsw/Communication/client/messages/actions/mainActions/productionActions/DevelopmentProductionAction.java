package it.polimi.ingsw.Communication.client.messages.actions.mainActions.productionActions;

import it.polimi.ingsw.Communication.client.messages.actions.mainActions.ProductionAction;

public class DevelopmentProductionAction implements ProductionAction {
    int developmentCardZone;

    public DevelopmentProductionAction(int developmentCardZone) {
        this.developmentCardZone = developmentCardZone;
    }

    public int getDevelopmentCardZone() {
        return developmentCardZone;
    }
}
