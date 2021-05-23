package it.polimi.ingsw.model;

import it.polimi.ingsw.communication.server.ServerSideSocket;
import it.polimi.ingsw.communication.server.messages.gameCreationPhaseMessages.MultiPlayerGameCreated;
import it.polimi.ingsw.communication.server.messages.gameCreationPhaseMessages.SinglePlayerGameCreated;
import it.polimi.ingsw.communication.server.messages.gameplayMessages.ResultsMessage;
import it.polimi.ingsw.communication.server.messages.printableMessages.*;
import it.polimi.ingsw.communication.server.messages.jsonMessages.DevelopmentCardMessage;
import it.polimi.ingsw.communication.server.messages.LorenzoWonMessage;
import it.polimi.ingsw.communication.server.messages.PlayerWonSinglePlayerMatch;
import it.polimi.ingsw.exception.BothPlayerAndLorenzoActivatePapalCardException;
import it.polimi.ingsw.exception.LorenzoActivatesPapalCardException;
import it.polimi.ingsw.exception.LorenzoWonTheMatch;
import it.polimi.ingsw.model.adapters.NicknameVictoryPoints;
import it.polimi.ingsw.model.lorenzoIlMagnifico.BlackCrossToken;
import it.polimi.ingsw.model.lorenzoIlMagnifico.DiscardToken;
import it.polimi.ingsw.model.lorenzoIlMagnifico.DoubleBlackCrossToken;
import it.polimi.ingsw.model.lorenzoIlMagnifico.Token;
import it.polimi.ingsw.model.boardsAndPlayer.GameBoard;

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
    private Map<Integer,String> originalOrderToNickname;
    private Map<String,Integer> nicknameToOriginalOrder;

    //TODO DECOMMENT A LOT OF MESSAGES
    /**
     * order of the player who triggered the endgame phase
     * (by buying 7 DevelopmentCards or reaching the end of PapalPath)
     */
    private int orderOfEndingPLayer;

    public int getOrderOfEndingPLayer() {
        return orderOfEndingPLayer;
    }

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
                connection.sendSocketMessage(new MultiPlayerGameCreated());
            }
        }

        //Single-player game creation
        else {
            playersSockets.get(0).sendSocketMessage(new SinglePlayerGameCreated());
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

    public Player playerActive(){
        return gameBoard.getPlayerFromNickname(activePlayer.getNickname());
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setOrderOfEndingPLayer(int orderOfEndingPLayer) {
        this.orderOfEndingPLayer = orderOfEndingPLayer;
    }

    public void nextTurn(){
        gameBoard.endTurn(activePlayer.getNickname());

        if(gameBoard.isSinglePlayer()){
            //CASE SINGLE PLAYER GAME: LORENZO HAS TO DO HIS MOVE

            if(orderOfEndingPLayer!=0){
                activePlayer.sendSocketMessage(new PlayerWonSinglePlayerMatch(gameBoard.getPlayers().get(0).getVictoryPoints()));
            }
            else {
                try {
                    Token tokenUsed = gameBoard.playLorenzo();
                    if (tokenUsed instanceof BlackCrossToken) {
                        players.get(0).sendSocketMessage(new BlackCrossTokenMessage(gameBoard.getLorenzoIlMagnifico().getFaithPosition()));
                    } else if (tokenUsed instanceof DoubleBlackCrossToken) {
                        players.get(0).sendSocketMessage(new DoubleBlackCrossTokenMessage(gameBoard.getLorenzoIlMagnifico().getFaithPosition()));
                    } else if (tokenUsed instanceof DiscardToken) {
                        players.get(0).sendSocketMessage(new DiscardTokenMessage(tokenUsed.toString()));
                        if (gameBoard.getDeckOfChoice(((DiscardToken) tokenUsed).getColor(), ((DiscardToken) tokenUsed).getLevelOfSecondDiscard()).deckSize() > 0) {
                            players.get(0).sendSocketMessage(new DevelopmentCardMessage(this.getGameBoard().getDeckOfChoice(((DiscardToken) tokenUsed).getColor(), ((DiscardToken) tokenUsed).getLevelOfSecondDiscard()).getFirstCard()));
                        } else players.get(0).sendSocketMessage(new DevelopmentCardMessage(null));
                    }
                    players.get(0).sendSocketMessage(new NextTurnMessage(players.get(0).getNickname()));
                } catch (LorenzoWonTheMatch e) {
                    players.get(0).sendSocketMessage(new LorenzoWonMessage());
                } catch (LorenzoActivatesPapalCardException e) {
                    activePlayer.sendSocketMessage(new LorenzoActivatedPapalCardAndYouDidnt(e.getCardIndex()));
                } catch (BothPlayerAndLorenzoActivatePapalCardException e) {
                    getActivePlayer().sendSocketMessage(new LorenzoActivatedpapalCardAndYouToo(e.getCardIndex()));
                }
            }
        }

        else {
            //CASE MULTI-PLAYER GAME

            //CASE THE PLAYER IS THE LAST IN ORDER AND GAME IS IN ENDING PHASE
            if(orderOfEndingPLayer!=0 && activePlayer.equals(players.get(players.size()-1))){
                for (ServerSideSocket serverSideSocket : players) {
                    serverSideSocket.sendSocketMessage(new ResultsMessage(this));
                }
            }
            else {
                for (int i = 1; i <= players.size(); i++) {
                    if (activePlayer.equals(players.get(i - 1))) {
                        //I have to set the new active player
                        if (i == (players.size())) {
                            //case the player is the last, I have to start back from n.1
                            activePlayer = players.get(0);
                        } else activePlayer = players.get(i);

                        for (ServerSideSocket socket : players) {
                           socket.sendSocketMessage(new NextTurnMessage(activePlayer.getNickname()));
                        }
                        return;
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
    public NicknameVictoryPoints[] leaderboard() {
        NicknameVictoryPoints[] temp = new NicknameVictoryPoints[gameBoard.playersSize()];
        for(int i=0; i<gameBoard.playersSize();i++)  temp[i]=new NicknameVictoryPoints(gameBoard.playerName(i),gameBoard.playerVictoryPoints(i));

        for(int i = 0; i < gameBoard.playersSize(); i++) {
            boolean flag = false;
            for(int j = 0; j < gameBoard.playersSize()-1; j++) {
                if(temp[j].getVictoryPoints()>temp[j+1].getVictoryPoints()) {
                    NicknameVictoryPoints k = temp[j];
                    temp[j] = temp[j+1];
                    temp[j+1] = k;
                    flag=true;
                }
            }
            if(!flag) break;
        }

        NicknameVictoryPoints[] leaderBoard= new NicknameVictoryPoints[gameBoard.playersSize()];
        for(int i=0;i<gameBoard.playersSize();i++){
            leaderBoard[i]=temp[gameBoard.playersSize()-i-1];
        }
        return leaderBoard;
    }

    public void reconnectAPlayerThatWasInGamePhase() {

    }
}
