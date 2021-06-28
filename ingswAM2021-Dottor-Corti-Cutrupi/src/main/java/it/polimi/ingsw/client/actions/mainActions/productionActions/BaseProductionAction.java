package it.polimi.ingsw.client.actions.mainActions.productionActions;

import it.polimi.ingsw.client.actions.mainActions.ProductionAction;
import it.polimi.ingsw.client.gui.utility.DevelopmentCardForGUI;
import it.polimi.ingsw.client.gui.utility.LeaderCardForGUI;
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

    /**
     * Sets the resources to discard and those to produce
     * @param resourcesUsed: what the player wants to discard
     * @param resourcesWanted: what the player wants to produce
     */
    public BaseProductionAction(ArrayList<ResourceType> resourcesUsed, ArrayList<ResourceType> resourcesWanted) {
        this.resourcesToUse = resourcesUsed;
        this.resourcesWanted = resourcesWanted;
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
        else if(gameHandler.actionPerformedOfActivePlayer()==3){
            gameHandler.sendMessageToActivePlayer(new YouMustDeleteADepotFirst());
        }
        else if(gameHandler.actionPerformedOfActivePlayer()==4){
            gameHandler.sendMessageToActivePlayer(new YouMustDiscardResourcesFirst());
        }
        else if(gameHandler.actionPerformedOfActivePlayer()==5){
            gameHandler.sendMessageToActivePlayer(new YouMustSelectWhiteToColorsFirst());
        }
        else gameHandler.sendMessageToActivePlayer(new MainActionAlreadyDoneMessage());
    }
}
