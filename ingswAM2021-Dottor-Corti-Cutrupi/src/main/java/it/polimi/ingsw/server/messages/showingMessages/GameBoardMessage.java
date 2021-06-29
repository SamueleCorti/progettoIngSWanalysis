package it.polimi.ingsw.server.messages.showingMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.model.boardsAndPlayer.GameBoard;
import it.polimi.ingsw.model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.requirements.ResourcesRequirements;
import it.polimi.ingsw.model.requirements.ResourcesRequirementsForAcquisition;
import it.polimi.ingsw.model.resource.Resource;
/**
 * Self explanatory name
 */
public class GameBoardMessage implements Message {
    private String message="Here's the gameboard:\n\n";
    private GameBoard gameBoard;

    public String getJsonGameboard() {
        return message;
    }

    public GameBoardMessage(GameBoard gameboard){
        this.gameBoard=gameboard;
        for(int row=0; row<3;row++){
            for(int column=0; column<4;column++){
                //message+=gameboard.getDevelopmentCardDeck(row,column).getLast
                if(gameboard.getDevelopmentCardDeck(row,column).deckSize()>0) message+=printDevCards(gameboard.getDevelopmentCardDeck(row,column).getFirstCard());
            }
        }
    }

    private String printDevCards(DevelopmentCard card) {
        String string = new String();
        string+="Color: "+ card.getCardStats().getValue1()+"\t\tlevel: "+card.getCardStats().getValue0()+" \t\tvictory points: "+card.getVictoryPoints()+"\n";
        string+="Card cost: \t";
        for(ResourcesRequirementsForAcquisition requirements: card.getCardPrice())
            string+=requirements.getResourcesRequired().getValue0() +" "+ requirements.getResourcesRequired().getValue1().getResourceType()+"s\t";
        string+="\nProduction cost: \n";
        for(ResourcesRequirements resourcesRequirements: card.getProdRequirements()){
            string+= resourcesRequirements.getResourcesRequired().getValue0()+" "+ resourcesRequirements.getResourcesRequired().getValue1().getResourceType()+"s\t";
        }
        string+="\n";
        string+="Resources produced: \n";
        for(Resource resource: card.getProdResults())
            string+= resource.getResourceType()+"\t";
        string+="\n\n\n";
        return string;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui){
            System.out.println("it is a gameboard message!");
            System.out.println(getJsonGameboard());
        }
    }
}
