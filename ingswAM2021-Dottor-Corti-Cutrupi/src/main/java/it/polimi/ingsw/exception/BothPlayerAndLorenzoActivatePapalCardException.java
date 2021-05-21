package it.polimi.ingsw.exception;

public class BothPlayerAndLorenzoActivatePapalCardException extends Exception{
    int cardIndex;
    public BothPlayerAndLorenzoActivatePapalCardException(int numOfReportSection) {
        cardIndex=numOfReportSection;
    }

    public int getCardIndex() {
        return cardIndex;
    }
}
