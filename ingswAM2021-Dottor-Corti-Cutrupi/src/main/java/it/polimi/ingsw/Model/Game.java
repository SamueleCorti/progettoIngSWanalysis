package it.polimi.ingsw.Model;

import it.polimi.ingsw.Communication.server.Server;
import it.polimi.ingsw.Communication.server.ServerSideSocket;
import it.polimi.ingsw.Communication.server.messages.GenericMessage;
import it.polimi.ingsw.Model.boardsAndPlayer.GameBoard;
import it.polimi.ingsw.Model.boardsAndPlayer.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * Represents the game, contains an arrayList of players, a gameboard, the game ID, and the active player.
 */
public class Game {
    private ArrayList<ServerSideSocket> players = new ArrayList<>();
    private int gameID=-1;
    private Player activePlayer;
    private GameBoard gameBoard;
   // private Map <String,ServerSideSocket> nicknameToServerSideSocket;

    public ArrayList<ServerSideSocket> getPlayers() {
        return players;
    }

    public Game(ArrayList <ServerSideSocket> playersSockets, int gameID){
        this.gameID=gameID;
        this.players = playersSockets;
        randomizePlayersOrder();

        //Multi-player game creation
        if(playersSockets.size()>1) {
            this.gameBoard = new GameBoard(players);
            for (ServerSideSocket connection:playersSockets) {
                connection.sendSocketMessage(new GenericMessage("Multi-player game created"));
                //nicknameToServerSideSocket.put(connection.getNickname(),connection);
            }
        }

        //Single-player game creation
        else {
            this.gameBoard = new GameBoard();
            playersSockets.get(0).sendSocketMessage(new GenericMessage("Single-player game created"));
        }
    }

    /**
     * Randomize the players' order, so that it isn't actually determined by the joining order
     */
    private void randomizePlayersOrder(){
        Collections.shuffle(players);
        for(int i=0;i< players.size();i++){
            players.get(i).setOrder(i+1);
        }
    }

    public void addPlayer(ServerSideSocket serverSideSocket){
        players.add(serverSideSocket);
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public void setPlayers(ArrayList<ServerSideSocket> players) {
        this.players = players;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Used to order the player based on their victory points
     * @return an array of players, from best to worst
     */
    /*public Player[] leaderboard() {
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
    }*/
}
