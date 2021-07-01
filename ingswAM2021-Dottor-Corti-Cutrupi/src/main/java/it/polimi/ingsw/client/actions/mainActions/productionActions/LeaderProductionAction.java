package it.polimi.ingsw.client.actions.mainActions.productionActions;

import it.polimi.ingsw.adapters.Parser;
import it.polimi.ingsw.client.actions.mainActions.ProductionAction;
import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.server.messages.printableMessages.*;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Type of action created when the player decides to activate the production of on of his leader cards
 */
public class LeaderProductionAction implements ProductionAction {
    private int leaderCardZoneIndex;
    private final ArrayList<ResourceType> resourcesWanted;

    /**
     * Used to activate the production of a leader card
     * @param leaderCardZoneIndex:  index representing the {@link it.polimi.ingsw.model.leadercard.LeaderCardZone} containing the card
     * @param resourcesWanted:      resource chosen as the production
     */
    public LeaderProductionAction(int leaderCardZoneIndex,ArrayList <ResourceType> resourcesWanted) {
        this.leaderCardZoneIndex = leaderCardZoneIndex;
        this.resourcesWanted = resourcesWanted;
    }

    @Override
    public void execute(GameHandler gameHandler) {
        Parser parser = new Parser();
        if((gameHandler.actionPerformedOfActivePlayer()==0 || gameHandler.actionPerformedOfActivePlayer()==2)&&
                (gameHandler.turnPhaseGivenNick(gameHandler.activePlayer().getNickname())<3)) {
            boolean[] productions = gameHandler.productionsActivatedInThisTurn();
            leaderCardZoneIndex--;
            if (leaderCardZoneIndex < 0 || leaderCardZoneIndex > ((gameHandler.getNumOfLeaderCardsKept()) - 1)) {
                gameHandler.sendMessageToActivePlayer(new WrongLeaderCardIndex());
            } else {
                //CORRECT PATH: USER DIDN'T ACTIVATE THE LEADER CARD PRODUCTION OF THE SELECTED CARD IN THIS TURN
                if (!productions[leaderCardZoneIndex + 4] && gameHandler.activePlayer().numOfLeaderCards()> leaderCardZoneIndex) {
                    if (gameHandler.leaderProduction(leaderCardZoneIndex,resourcesWanted)) {
                        gameHandler.sendMessageToActivePlayer(new ProductionAck());
                        gameHandler.sendMessageToActivePlayer(new ResourcesUsableForProd(parser.parseListOfResources(gameHandler.activePlayer().resourcesUsableForProd())));
                        gameHandler.sendMessageToActivePlayer(new ResourcesProduced(parser.parseListOfResources(gameHandler.activePlayer().resourcesProduced())));
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
        else if(gameHandler.actionPerformedOfActivePlayer()==3 || gameHandler.turnPhaseGivenNick(gameHandler.activePlayer().getNickname()) == 3){
            gameHandler.sendMessageToActivePlayer(new YouMustDeleteADepotFirst());
        }
        else if(gameHandler.actionPerformedOfActivePlayer()==4 || gameHandler.turnPhaseGivenNick(gameHandler.activePlayer().getNickname()) == 4){
            gameHandler.sendMessageToActivePlayer(new YouMustDiscardResourcesFirst());
        }
        else if(gameHandler.actionPerformedOfActivePlayer()==5 || gameHandler.turnPhaseGivenNick(gameHandler.activePlayer().getNickname()) == 5){
            gameHandler.sendMessageToActivePlayer(new YouMustSelectWhiteToColorsFirst());
        }
        else gameHandler.sendMessageToActivePlayer(new MainActionAlreadyDoneMessage());
    }
}
