package it.polimi.ingsw.Exceptions;

public class LorenzoActivatesPapalCardException extends Exception {
    int cardIndex;
    public LorenzoActivatesPapalCardException (int numOfReportSection) {
        cardIndex=numOfReportSection;
    }

    public int getCardIndex() {
        return cardIndex;
    }
}
