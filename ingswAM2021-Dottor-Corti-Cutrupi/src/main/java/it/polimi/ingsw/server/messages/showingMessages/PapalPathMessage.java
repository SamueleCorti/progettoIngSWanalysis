package it.polimi.ingsw.server.messages.showingMessages;

import it.polimi.ingsw.adapters.Parser;
import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.model.papalpath.CardCondition;
import it.polimi.ingsw.model.papalpath.PapalPath;
import it.polimi.ingsw.server.messages.Message;

/**
 * Self explanatory name
 */
public class PapalPathMessage implements Message {
    private int[] tiles= new int[25];
    private int[] victoryPoints= new int[25];
    private int[] cardsInfo=new int[3];
    private int playerFaithPos=0;
    private int lorenzoFaithPos=0;
    private int[] popeSpaces= new int[3];

    public PapalPathMessage(PapalPath papalPath) {
        int card=0;
        for(int i=0;i<25;i++){
            tiles[i]=papalPath.getPapalTiles().get(i).getNumOfReportSection();
            if(papalPath.isPopeSpace(i)){
                if(papalPath.getCards(papalPath.getPapalTiles().get(i).getNumOfReportSection()-1).getCondition()== CardCondition.Active)   {
                    cardsInfo[card]=2;
                    card++;
                }
                else if(papalPath.getCards(papalPath.getPapalTiles().get(i).getNumOfReportSection()-1).getCondition()== CardCondition.Discarded)   {
                    cardsInfo[card]=1;
                    card++;
                }
                else if(papalPath.getCards(papalPath.getPapalTiles().get(i).getNumOfReportSection()-1).getCondition()== CardCondition.Inactive)   {
                    cardsInfo[card]=0;
                    card++;
                }
            }
            victoryPoints[i]=papalPath.getPapalTiles().get(i).getVictoryPoints();
            if(papalPath.isPopeSpace(i))    popeSpaces[papalPath.numOfReportSection(i)-1]=i;
        }
        playerFaithPos = papalPath.getFaithPosition();
        this.lorenzoFaithPos = papalPath.getFaithPositionLorenzo();
    }

    public int[] getTiles() {
        return tiles;
    }

    public int[] getVictoryPoints() {
        return victoryPoints;
    }

    public int getPlayerFaithPos() {
        return playerFaithPos;
    }

    public int getLorenzoFaithPos() {
        return lorenzoFaithPos;
    }

    public int[] getCardsInfo() {
        return cardsInfo;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui){
            if(!socket.checkShowingOtherPlayerDashboard())   socket.printPapalPath(this);
            else socket.refreshPapalPath(this);
        }
        else {
            Parser parser = new Parser();
            System.out.println(parser.decipherPapalPath(this));
        }
    }

    public int[] getPopeSpaces() {
        return popeSpaces;
    }
}
