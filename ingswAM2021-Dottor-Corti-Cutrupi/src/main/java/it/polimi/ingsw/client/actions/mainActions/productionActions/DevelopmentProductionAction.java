package it.polimi.ingsw.client.actions.mainActions.productionActions;

import it.polimi.ingsw.client.actions.mainActions.ProductionAction;
import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.exception.warehouseErrors.WarehouseDepotsRegularityError;
import it.polimi.ingsw.server.messages.printableMessages.*;

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

    @Override
    public void execute(GameHandler gameHandler) {
        if(gameHandler.actionPerformedOfActivePlayer()==0 || gameHandler.actionPerformedOfActivePlayer()==2) {
            boolean[] productions = gameHandler.productionsActivatedInThisTurn();

            //CORRECT PATH: USER ASKED FOR A PRODUCTION HE DIDN'T ACTIVATE IN THIS TURN YET
            if (!productions[developmentCardZone] && !gameHandler.activePlayer().isLastCardOfTheSelectedDevZoneNull(developmentCardZone)){

                //CORRECT PATH: USER HAS GOT ENOUGH RESOURCES TO ACTIVATE THE PRODUCTION
                if(gameHandler.devCardProduction(developmentCardZone)) {
                    gameHandler.sendMessageToActivePlayer(new ProductionAck());

                    try {
                        gameHandler.activePlayer().swapResources();
                    } catch (WarehouseDepotsRegularityError warehouseDepotsRegularityError) {
                        warehouseDepotsRegularityError.printStackTrace();
                    }
                    gameHandler.sendMessageToActivePlayer(new ResourcesUsableForProd(gameHandler.parseListOfResources(gameHandler.activePlayer().resourcesUsableForProd())));
                    gameHandler.sendMessageToActivePlayer(new ResourcesProduced(gameHandler.parseListOfResources(gameHandler.activePlayer().resourcesProduced())));
                    //gameHandler.sendMessageToActivePlayer(new QuantityOfFaithProducedMessage(gameHandler.activePlayer().getFaithProduced(developmentCardZone)));
                    gameHandler.updateValueOfActionPerformed(2);
                }

                //WRONG PATH: USER HASN'T GOT ENOUGH RESOURCES TO ACTIVATE THE PRODUCTION
                else gameHandler.sendMessageToActivePlayer(new NotEnoughResourcesToProduce());
            }

            //WRONG PATH: USER ALREADY ACTIVATED THIS DEVELOPMENT CARD PRODUCTION IN THIS TURN
            else if(productions[developmentCardZone]){
                gameHandler.sendMessageToActivePlayer(new ProductionAlreadyActivatedInThisTurn());
            }
            else
                gameHandler.sendMessageToActivePlayer(new WrongZoneInProduce());
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
