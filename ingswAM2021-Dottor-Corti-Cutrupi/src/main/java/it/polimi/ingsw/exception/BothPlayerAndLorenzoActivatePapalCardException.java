package it.polimi.ingsw.exception;

/**
 * Self explanatory name
 */
public class BothPlayerAndLorenzoActivatePapalCardException extends Exception{
    private int cardIndex;

    public BothPlayerAndLorenzoActivatePapalCardException(int numOfReportSection) {
        cardIndex=numOfReportSection;
    }

    public int getCardIndex() {
        return cardIndex;
    }
}
