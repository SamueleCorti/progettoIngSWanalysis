package it.polimi.ingsw.client.actions.secondaryActions;

import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.model.boardsAndPlayer.Player;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.server.messages.printableMessages.PrintAString;

/**
 * Sends the player a string containg the sum of resources in warehouse, strongbox, and extra depos
 */
public class PrintResourcesAction implements SecondaryAction{
    @Override
    public void execute(GameHandler gameHandler) {
        Player player = gameHandler.activePlayer();
        String string="Here are all your resources: \n";
        string+="You have "+player.availableResourceOfType(ResourceType.Coin)+" coin; of those, "+
                player.producedThisTurn(ResourceType.Coin)+ " have just been produced this turn\n";
        string+="You have "+player.availableResourceOfType(ResourceType.Stone)+" stone; of those, "+
                player.producedThisTurn(ResourceType.Stone)+ " have just been produced this turn\n";
        string+="You have "+player.availableResourceOfType(ResourceType.Servant)+" servant; of those, "+
                player.producedThisTurn(ResourceType.Servant)+ " have just been produced this turn\n";
        string+="You have "+player.availableResourceOfType(ResourceType.Shield)+" shield; of those, "+
                player.producedThisTurn(ResourceType.Shield)+ " have just been produced this turn";
        gameHandler.sendMessageToActivePlayer(new PrintAString(string));
    }
}
