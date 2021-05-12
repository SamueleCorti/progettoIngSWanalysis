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

    public void changeTurn(){
        int index=0;
        for(int i=0; i<players.size(); i++){
            if (activePlayer==players.get(i)) index=i;
        }
        if(index<3) activePlayer=players.get(index+1);
        else activePlayer=players.get(0);
    }
}
