package it.polimi.ingsw;

import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players;
    private int gameID=-1;
    private Player activePlayer;
    private GameBoard gameBoard;

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}
