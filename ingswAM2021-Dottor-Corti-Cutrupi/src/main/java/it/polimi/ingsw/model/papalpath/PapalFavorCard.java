package it.polimi.ingsw.model.papalpath;

/**
 * Represents a papal favor cards, using its position, victory points value, and condition (active, inactive, discarded)
 */
public class PapalFavorCard {
    private int faithPosition;
    private CardCondition condition;
    private int victoryPoints;

    //These cards are initialized when the rest of the path is, we only need their number (1st, 2nd, 3rd) and how many VP they're worth; at the start of the game their condition is the same

    public PapalFavorCard(int faithPosition, int victoryPoints) {
        this.faithPosition = faithPosition;
        this.condition = CardCondition.Inactive;
        this.victoryPoints = victoryPoints;
    }

    public int getFaithPosition() {
        return faithPosition;
    }

    public CardCondition getCondition() {
        return this.condition;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setCondition(CardCondition condition) {
        this.condition = condition;
    }
}
