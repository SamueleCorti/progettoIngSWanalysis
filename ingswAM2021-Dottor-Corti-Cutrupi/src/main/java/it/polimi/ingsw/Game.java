package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private ArrayList<Player> players;
    private int gameID=-1;
    private Player activePlayer;
    private GameBoard gameBoard;

    public ArrayList<Player> getPlayers() {
        return players;
    }

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

    public void changeTurn(){
        int index=0;
        for(int i=0; i<players.size(); i++){
            if (activePlayer==players.get(i)) index=i;
        }
        if(index<3) activePlayer=players.get(index+1);
        else activePlayer=players.get(0);
    }

    public Player[] leaderboard(int [] array) {
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
        return temp;
    }
}
