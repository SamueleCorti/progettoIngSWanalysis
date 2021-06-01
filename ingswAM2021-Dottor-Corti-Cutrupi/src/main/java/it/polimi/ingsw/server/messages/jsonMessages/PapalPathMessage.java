package it.polimi.ingsw.server.messages.jsonMessages;

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
                    tiles[i]+=30;
                    cardsInfo[card]=2;
                    card++;
                }
                else if(papalPath.getCards(papalPath.getPapalTiles().get(i).getNumOfReportSection()-1).getCondition()== CardCondition.Discarded)   {
                    tiles[i]+=10;
                    cardsInfo[card]=1;
                    card++;
                }
                else if(papalPath.getCards(papalPath.getPapalTiles().get(i).getNumOfReportSection()-1).getCondition()== CardCondition.Inactive)   {
                    tiles[i]+=20;
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
}
