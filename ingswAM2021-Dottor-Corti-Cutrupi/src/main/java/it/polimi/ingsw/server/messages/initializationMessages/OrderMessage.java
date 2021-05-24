package it.polimi.ingsw.server.messages.initializationMessages;

import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.model.Game;

public class OrderMessage implements Message {
    private String playerOrderMessage;

    /**
     * Used to give players the order that everyone will play according to
     */
    public OrderMessage(Game game) {
        playerOrderMessage= "The order has been randomized; here are the results: \n";
        int gameSize= game.getPlayers().size();
        String temp="";
        for(int i=0;i<gameSize;i++) {
            switch (i) {
                case 0: {
                    temp += " 1st to play: " + game.getPlayers().get(0).getNickname()+"\n";
                    break;
                }
                case 1: {
                    temp += " 2nd: " + game.getPlayers().get(1).getNickname()+"\n";
                    break;
                }
                case 2: {
                    temp += " 3nd: " + game.getPlayers().get(2).getNickname()+"\n";
                    break;
                }
                case 3: {
                    temp += " Last to play: " + game.getPlayers().get(3).getNickname()+"\n";
                    break;
                }
            }
        }
        playerOrderMessage+=temp;
    }

    public String getPlayerOrder() {
        return playerOrderMessage;
    }
}
