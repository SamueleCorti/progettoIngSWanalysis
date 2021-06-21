package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.model.boardsAndPlayer.Player;

/**
 * Sends the active player a string representing his depots
 */
public class PrintDepotsMessage implements PrintableMessage{
    StringBuilder string= new StringBuilder("Here are your depots: \n");

    /**
     * @param gameHandler used to access the player's depots
     */
    public PrintDepotsMessage(GameHandler gameHandler) {
        Player player = gameHandler.activePlayer();
        for(int i=1;i<=player.sizeOfWarehouse();i++){
            string.append(i).append(": ");
            for(int j=0; j<player.lengthOfDepotGivenItsIndex(i);j++){
                string.append("\t").append(player.typeOfDepotGivenItsIndex(i));
            }
            string.append("\n");
        }
        if(player.numberOfExtraDepots()!=0){
            string.append("You also have the following extra depots: \n");
            for(int i=0; i<player.numberOfExtraDepots(); i++){
                for(int j=0; j<player.resourcesContainedInAnExtraDepotGivenItsIndex(i);j++)
                    string.append("\t").append(player.typeOfExtraDepotGivenItsIndex(i));
                string.append("\n");
            }
        }
    }

    @Override
    public String getString() {
        return string.toString();
    }
}
