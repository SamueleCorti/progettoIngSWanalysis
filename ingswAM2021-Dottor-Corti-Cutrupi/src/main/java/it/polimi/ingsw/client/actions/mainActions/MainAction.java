package it.polimi.ingsw.client.actions.mainActions;

import it.polimi.ingsw.client.actions.ExecutableAction;

/**
 * Interface that will be implemented by {@link ProductionAction}, {@link DevelopmentAction}, {@link MarketAction}, i.e. the actions that can't be performed together
 * in the same turn
 */
public interface MainAction extends ExecutableAction {

}
