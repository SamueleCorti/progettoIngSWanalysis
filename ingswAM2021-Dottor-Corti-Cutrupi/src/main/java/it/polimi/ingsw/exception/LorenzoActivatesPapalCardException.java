package it.polimi.ingsw.exception;

/**
 * Lorenzo activated a card, but the player didn't (so has to discard it)
 */
public class LorenzoActivatesPapalCardException extends Exception {
    private int cardIndex;
    public LorenzoActivatesPapalCardException (int numOfReportSection) {
        cardIndex=numOfReportSection;
    }

    public int getCardIndex() {
        return cardIndex;
    }
}
