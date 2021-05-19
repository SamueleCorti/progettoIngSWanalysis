package it.polimi.ingsw.Model;

import it.polimi.ingsw.Communication.server.Server;
import it.polimi.ingsw.Communication.server.ServerSideSocket;
import it.polimi.ingsw.Communication.server.SocketServer;
import it.polimi.ingsw.Communication.server.messages.GameplayMessages.ResultsMessage;
import it.polimi.ingsw.Communication.server.messages.GenericMessage;
import it.polimi.ingsw.Model.boardsAndPlayer.GameBoard;
import it.polimi.ingsw.Model.boardsAndPlayer.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the game, contains an arrayList of players, a gameboard, the game ID, and the active player.
 */
public class Game {
    private ArrayList<ServerSideSocket> players = new ArrayList<>();
    private int gameID;
    private ServerSideSocket activePlayer;
    private GameBoard gameBoard;
    private Map<Integer,String > originalOrderToNickname;
    private Map<String,Integer> nicknameToOriginalOrder;

    /**
     * order of the player who triggered the endgame phase
     * (by buying 7 DevelopmentCards or reaching the end of PapalPath)
     */
    private int orderOfEndingPLayer;

    public ArrayList<ServerSideSocket> getPlayers() {
        return players;
    }

    public Game(ArrayList <ServerSideSocket> playersSockets, int gameID){
        this.gameID=gameID;
        this.players = playersSockets;
        originalOrderToNickname = new HashMap<>();
        nicknameToOriginalOrder = new HashMap<>();
        randomizePlayersOrder();

        //Multi-player game creation
        if(playersSockets.size()>1) {
            this.gameBoard = new GameBoard(players);
            for (ServerSideSocket connection:playersSockets) {
                connection.sendSocketMessage(new GenericMessage("Multi-player game created"));
            }
        }

        //Single-player game creation
        else {
            playersSockets.get(0).sendSocketMessage(new GenericMessage("Single-player game created"));
            this.gameBoard = new GameBoard(playersSockets.get(0).getNickname());
        }

        this.activePlayer=players.get(0);
        this.orderOfEndingPLayer = 0;
    }



    /**
     * Randomize the players' order, so that it isn't actually determined by the joining order
     */
    private void randomizePlayersOrder(){
        Collections.shuffle(players);
        for(int i=0;i< players.size();i++){
            players.get(i).setOrder(i+1);
            originalOrderToNickname.put(i+1,players.get(i).getNickname());
            nicknameToOriginalOrder.put(players.get(i).getNickname(),i+1);
        }
    }

    public void reorderPlayersTurns(){
        ArrayList<ServerSideSocket> newOrder = new ArrayList<>();
        for (int i=1;i<=originalOrderToNickname.size();i++) {
            for (ServerSideSocket connectedSocket:players) {
                if(originalOrderToNickname.get(i).equals(connectedSocket.getNickname())){
                    newOrder.add(connectedSocket);
                }
            }
        }
        //players.clear();
        //players.addAll(newOrder);
        players = newOrder;
    }

    public void addPlayer(ServerSideSocket serverSideSocket){
        players.add(serverSideSocket);
    }

    public void setPlayers(ArrayList<ServerSideSocket> players) {
        this.players = players;
    }

    public ServerSideSocket getActivePlayer() {
        return activePlayer;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setOrderOfEndingPLayer(int orderOfEndingPLayer) {
        this.orderOfEndingPLayer = orderOfEndingPLayer;
    }

    public void nextTurn(){
        gameBoard.getPlayerFromNickname(activePlayer.getNickname()).endTurn();
        //previous was <: still to be tested
        for(int i=1;i<=players.size();i++){
            if(activePlayer.equals(players.get(i-1))){
                //I have to set the new active player
                if(this.orderOfEndingPLayer==0) {
                    //case it's not endgame phase
                    if (i == (players.size())) {
                        //case the player is the last, I have to start back from n.1
                        activePlayer = players.get(0);
                    } else activePlayer = players.get(i);

                    for (ServerSideSocket socket : players) {
                        socket.sendSocketMessage(new GenericMessage("It's " + activePlayer.getNickname() + "'s turn"));
                    }
                    return;
                }else {
                    if (i >= this.orderOfEndingPLayer) {
                        if (i == (players.size())) {
                                for (ServerSideSocket serverSideSocket : players) {
                                    serverSideSocket.sendSocketMessage(new ResultsMessage(this));
                                }
                        } else {
                            activePlayer = players.get(i);
                            for (ServerSideSocket socket : players) {
                                socket.sendSocketMessage(new GenericMessage("It's " + activePlayer.getNickname() + "'s turn"));
                            }
                        }
                        return;
                    } else {
                        System.out.println("something went wrong: we should never be in this situation");
                        }
                    }
            }
        }
    }

    public void nextTurnWhenActiveDisconnects(){
        for(int i=0;i< players.size();i++){
            if(activePlayer.equals(players.get(i))){
                //I have to set the new active player
                //case the player is the last, I have to start back from n.1
                if(i==(players.size()-1)){
                    activePlayer=players.get(0);
                }
                else activePlayer=players.get(i+1);
                return;
            }
        }
    }

    public void removeConnection(ServerSideSocket connectionToRemove){
        for(int i=0;i< players.size();i++){
            if(connectionToRemove.equals(players.get(i))) players.remove(i);
            return;
        }
    }

    /**
     * Used to order the player based on their victory points
     * @return an array of players, from best to worst
     */
    public Player[] leaderboard() {
        Player[] temp = new Player[players.size()];
        for(int i=0; i<players.size();i++)  temp[i]=getGameBoard().getPlayers().get(i);

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
        for(int i=0;i<players.size();i++){
            leaderBoard[i]=temp[players.size()-i-1];
        }
        return leaderBoard;
    }

    public void reconnectAPlayerThatWasInGamePhase() {

    }
}
