package it.polimi.ingsw.server.messages;

public class PlayerWonSinglePlayerMatch implements Message {
    int victoryPoints;

    public PlayerWonSinglePlayerMatch(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }
}
