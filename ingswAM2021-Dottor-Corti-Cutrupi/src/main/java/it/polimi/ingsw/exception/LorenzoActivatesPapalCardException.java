package it.polimi.ingsw.exception;

public class LorenzoActivatesPapalCardException extends Exception {
    int cardIndex;
    public LorenzoActivatesPapalCardException (int numOfReportSection) {
        cardIndex=numOfReportSection;
    }

    public int getCardIndex() {
        return cardIndex;
    }
}
