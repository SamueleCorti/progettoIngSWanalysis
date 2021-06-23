package it.polimi.ingsw.server.messages.jsonMessages;

import it.polimi.ingsw.adapters.Parser;
import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.model.papalpath.CardCondition;
import it.polimi.ingsw.model.papalpath.PapalPath;
import it.polimi.ingsw.server.messages.Message;

public class PapalPathMessage implements Message {
    int[] tiles= new int[25];
    int[] victoryPoints= new int[25];
    int[] cardsInfo=new int[3];
    int playerFaithPos=0;
    int lorenzoFaithPos=0;

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
}
