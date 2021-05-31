package it.polimi.ingsw.server.messages.initializationMessages;

import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.model.Game;

import java.util.ArrayList;

public class OrderMessage implements Message {
    private ArrayList<String> playersNicknamesInOrder;

    /**
     * Used to give players the order that everyone will play according to
     */
    public OrderMessage(Game game) {
        this.playersNicknamesInOrder = new ArrayList<String>();
        int gameSize = game.getPlayers().size();
        for(int i=0;i<gameSize;i++) {
            switch (i) {
                case 0: {
                    playersNicknamesInOrder.add(game.getPlayers().get(0).getNickname());
                    break;
                }
                case 1: {
                    playersNicknamesInOrder.add(game.getPlayers().get(1).getNickname());
                    break;
                }
                case 2: {
                    playersNicknamesInOrder.add(game.getPlayers().get(2).getNickname());
                    break;
                }
                case 3: {
                    playersNicknamesInOrder.add(game.getPlayers().get(3).getNickname());
                    break;
                }
            }

        }
    }

    public ArrayList<String> getPlayersNicknamesInOrder() {
        return playersNicknamesInOrder;
    }
}
