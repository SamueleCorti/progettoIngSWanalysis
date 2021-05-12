package it.polimi.ingsw.Communication.server.answers;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.Player;

public class DisconnectionMessage implements Message {
    Game game;
    Player disconnectedPlayer;
    String message;

    public DisconnectionMessage(Game game, Player disconnectedPlayer) {
        this.game = game;
        this.disconnectedPlayer = disconnectedPlayer;
        message= disconnectedPlayer.getNickname()+ " has just disconnected. The game will proceed normally, and all "+disconnectedPlayer.getNickname()+"'s turns" +
                "will be skipped until he reconnects. ";
    }

}
