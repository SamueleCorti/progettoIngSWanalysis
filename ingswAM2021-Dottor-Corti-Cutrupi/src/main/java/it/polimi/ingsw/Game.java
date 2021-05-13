package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents the game, contains an arrayList of players, a gameboard, the game ID, and the active player.
 */
public class Game {
    private ArrayList<Player> players;
    private int gameID=-1;
    private Player activePlayer;
    private GameBoard gameBoard;

    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Randomize the players' order, so that it isn't actually determined by the joining order
     */
    public void randomizePlayersOrder(){
        Collections.shuffle(players);
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

    /**
     * Called when a player finishes his turn, gives control to the next one
     */
    public void changeTurn(){
        int index=0;
        for(int i=0; i<players.size(); i++){
            if (activePlayer==players.get(i)) index=i;
        }
        if(index<3) activePlayer=players.get(index+1);
        else activePlayer=players.get(0);
    }

    /**
     * Used to order the player based on their victory points
     * @return an array of players, from best to worst
     */
    public Player[] leaderboard() {
        Player[] temp= new Player[players.size()];
        for(int i=0; i<players.size();i++)  temp[i]=players.get(i);
        for(int i = 0; i < players.size(); i++) {
            boolean flag = false;
            for(int j = 0; j < players.size()-1; j++) {
                if(temp[j].getVictoryPoints()>temp[j+1].getVictoryPoints()) {
                    Player k = temp[j];
                    temp[j] = temp[j+1];
                    temp[j+1] = k;
                    flag=true;
                }
            }
            if(!flag) break;
        }
        Player[] leaderBoard= new Player[players.size()];
        for(int i=0;i< players.size();i++){
            leaderBoard[i]=temp[players.size()-i-1];
        }
        return leaderBoard;
    }
}
