package it.polimi.ingsw.client.actions.mainActions.productionActions;

import it.polimi.ingsw.client.actions.mainActions.ProductionAction;
import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.server.messages.printableMessages.*;

import java.util.ArrayList;

/**
 * Created when the client decides to activate the basic production
 */
public class BaseProductionAction implements ProductionAction {
    private final ArrayList<ResourceType> resourcesToUse;
    private final ArrayList<ResourceType> resourcesWanted;

    public BaseProductionAction(ArrayList<ResourceType> resourcesUsed, ArrayList<ResourceType> resourcesWanted) {
        this.resourcesToUse = resourcesUsed;
        this.resourcesWanted = resourcesWanted;
    }

    @Override
    public String toString() {
        return "BaseProductionAction{" +
                "resourcesUsed=" + resourcesToUse +
                ", resourcesWanted=" + resourcesWanted +
                '}';
    }

    public ArrayList<ResourceType> getResourcesToUse() {
        return resourcesToUse;
    }

    public ArrayList<ResourceType> getResourcesWanted() {
        return resourcesWanted;
    }

    @Override
    public void execute(GameHandler gameHandler) {
        if(gameHandler.actionPerformedOfActivePlayer()==0 || gameHandler.actionPerformedOfActivePlayer()==2){
            boolean[] productions= gameHandler.productionsActivatedInThisTurn();
            //CORRECT PATH: USER DIDN'T ACTIVATE BASE PRODUCTION IN THIS TURN
            if(!productions[0]){
                if (gameHandler.baseProduction(resourcesToUse,resourcesWanted)) {
                    gameHandler.sendMessageToActivePlayer(new ProductionAck());
                    gameHandler.sendMessageToActivePlayer(new ResourcesUsableForProd(gameHandler.parseListOfResources(gameHandler.activePlayer().resourcesUsableForProd())));
                    gameHandler.sendMessageToActivePlayer(new ResourcesProduced(gameHandler.parseListOfResources(gameHandler.activePlayer().resourcesProduced())));
                    gameHandler.updateValueOfActionPerformed(2);
                }
            }

            //WRONG PATH: USER ALREADY ACTIVATED BASE PRODUCTION IN THIS TURN
            else {
                gameHandler.sendMessageToActivePlayer(new ProductionAlreadyActivatedInThisTurn());
            }
        }
        else gameHandler.sendMessageToActivePlayer(new MainActionAlreadyDoneMessage());
    }
}
