package it.polimi.ingsw.communication.client.actions.mainActions.productionActions;

import it.polimi.ingsw.communication.client.actions.mainActions.ProductionAction;
import it.polimi.ingsw.communication.server.GameHandler;
import it.polimi.ingsw.communication.server.messages.printableMessages.*;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.ArrayList;

/**
 * Type of action created when the player decides to activate the production of on of his leader cards
 */
public class LeaderProductionAction implements ProductionAction {
    private int leaderCardZoneIndex;
    private final ArrayList<ResourceType> resourcesWanted;

    public LeaderProductionAction(int leaderCardZoneIndex,ArrayList <ResourceType> resourcesWanted) {
        this.leaderCardZoneIndex = leaderCardZoneIndex;
        this.resourcesWanted = resourcesWanted;
    }

    @Override
    public String toString() {
        return "LeaderProductionAction{" +
                "leaderCardZoneIndex=" + leaderCardZoneIndex +
                ", resourcesWanted=" + resourcesWanted +
                '}';
    }

    public int getLeaderCardZoneIndex() {
        return leaderCardZoneIndex;
    }
    public ArrayList <ResourceType> getResourcesWanted() {
        return resourcesWanted;
    }

    @Override
    public void execute(GameHandler gameHandler) {
        if(gameHandler.actionPerformedOfActivePlayer()==0 || gameHandler.actionPerformedOfActivePlayer()==2) {
            boolean[] productions = gameHandler.productionsActivatedInThisTurn();
            leaderCardZoneIndex--;
            if (leaderCardZoneIndex < 0 || leaderCardZoneIndex > ((gameHandler.getNumOfLeaderCardsKept()) - 1)) {
                gameHandler.sendMessageToActivePlayer(new WrongLeaderCardIndex());
            } else {
                //CORRECT PATH: USER DIDN'T ACTIVATE THE LEADER CARD PRODUCTION OF THE SELECTED CARD IN THIS TURN
                if (!productions[leaderCardZoneIndex + 4] && gameHandler.activePlayer().numOfLeaderCards()> leaderCardZoneIndex) {
                    if (gameHandler.leaderProduction(leaderCardZoneIndex,resourcesWanted)) {
                        gameHandler.sendMessageToActivePlayer(new ProductionAck());
                        gameHandler.sendMessageToActivePlayer(new ResourcesUsableForProd(gameHandler.parseListOfResources(gameHandler.activePlayer().resourcesUsableForProd())));
                        gameHandler.sendMessageToActivePlayer(new ResourcesProduced(gameHandler.parseListOfResources(gameHandler.activePlayer().resourcesProduced())));
                        gameHandler.updateValueOfActionPerformed(2);
                    }
                }

                //WRONG PATH: USER ASKED FOR A PRODUCTION HE ALREADY ACTIVATED IN THIS TURN
                else if (productions[leaderCardZoneIndex + 4])
                    gameHandler.sendMessageToActivePlayer(new ProductionAlreadyActivatedInThisTurn());
                else {
                    gameHandler.sendMessageToActivePlayer(new WrongLeaderCardIndex());
                }
            }
        }
    }
}
