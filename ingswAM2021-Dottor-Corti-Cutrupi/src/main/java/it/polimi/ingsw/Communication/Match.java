package it.polimi.ingsw.Communication;

import it.polimi.ingsw.Player;

import java.io.FileNotFoundException;
import java.net.Socket;
import java.util.ArrayList;

public class Match {
    private Socket matchSocket;
    boolean matchIsPrivate;
    ArrayList<Player> players;
    int numOfPlayers;

    public Match(Socket matchSocket, boolean matchIsPrivate, String nickname, int numOfPlayers) throws FileNotFoundException {
        this.matchSocket = matchSocket;
        this.matchIsPrivate = matchIsPrivate;
        this.players.add(new Player(nickname,players.size()+1));
        this.numOfPlayers=numOfPlayers;
    }
}
