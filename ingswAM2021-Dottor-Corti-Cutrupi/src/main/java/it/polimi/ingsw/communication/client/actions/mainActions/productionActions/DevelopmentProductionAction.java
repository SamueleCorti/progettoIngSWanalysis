package it.polimi.ingsw.communication.client.actions.mainActions.productionActions;

import it.polimi.ingsw.communication.client.actions.mainActions.ProductionAction;
import it.polimi.ingsw.communication.server.GameHandler;
import it.polimi.ingsw.communication.server.messages.printableMessages.*;

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

    @Override
    public void execute(GameHandler gameHandler) {
        if(gameHandler.actionPerformedOfActivePlayer()==0 || gameHandler.actionPerformedOfActivePlayer()==2) {
            boolean[] productions = gameHandler.productionsActivatedInThisTurn();

            //CORRECT PATH: USER ASKED FOR A PRODUCTION HE DIDN'T ACTIVATE IN THIS TURN YET
            if (!productions[developmentCardZone+1] && !gameHandler.activePlayer().isLastCardOfTheSelectedDevZoneNull(developmentCardZone)){

                //CORRECT PATH: USER HAS GOT ENOUGH RESOURCES TO ACTIVATE THE PRODUCTION
                if(gameHandler.devCardProduction(developmentCardZone)) {
                    gameHandler.sendMessageToActivePlayer(new ProductionAck());
                    gameHandler.sendMessageToActivePlayer(new ResourcesUsableForProd(gameHandler.parseListOfResources(gameHandler.activePlayer().resourcesUsableForProd())));
                    gameHandler.sendMessageToActivePlayer(new ResourcesProduced(gameHandler.parseListOfResources(gameHandler.activePlayer().resourcesProduced())));
                    gameHandler.updateValueOfActionPerformed(2);
                }

                //WRONG PATH: USER HASN'T GOT ENOUGH RESOURCES TO ACTIVATE THE PRODUCTION
                gameHandler.sendMessageToActivePlayer(new NotEnoughResourcesToProduce());
            }

            //WRONG PATH: USER ALREADY ACTIVATED THIS DEVELOPMENT CARD PRODUCTION IN THIS TURN
            else if(productions[developmentCardZone+1]){
                gameHandler.sendMessageToActivePlayer(new ProductionAlreadyActivatedInThisTurn());
            }
            else
                gameHandler.sendMessageToActivePlayer(new WrongZoneInProduce());
        }
    }
}
