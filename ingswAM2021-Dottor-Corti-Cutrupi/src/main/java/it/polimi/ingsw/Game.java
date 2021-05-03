package it.polimi.ingsw;

import java.util.ArrayList;

public class Game {
    ArrayList<Player> players;
    int gameID=-1;

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
