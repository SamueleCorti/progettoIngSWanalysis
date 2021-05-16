package it.polimi.ingsw.Model.papalpath;

public class PapalFavorCard {
    private int numberID;
    private CardCondition condition;
    private int victoryPoints;

    //These cards are initialized when the rest of the path is, we only need their number (1st, 2nd, 3rd) and how many VP they're worth; at the start of the game their condition is the same

    public PapalFavorCard(int faithPosition, int victoryPoints) {
        this.numberID = faithPosition;
        this.condition = CardCondition.Inactive;
        this.victoryPoints = victoryPoints;
    }

    public int getNumberID() {
        return numberID;
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
