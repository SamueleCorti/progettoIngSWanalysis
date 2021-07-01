package it.polimi.ingsw.server.messages.initializationMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.controller.Game;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Map;

/**
 * Self explanatory name
 */
public class OrderMessage implements Message {
    private ArrayList<String> playersNicknamesInOrder;

    /**
     * Used to give players the order that everyone will play according to
     */
    public OrderMessage(Game game, Map<Integer,String> originalOrderToNickname) {
        this.playersNicknamesInOrder = new ArrayList<String>();
        for (Integer originalOrder: originalOrderToNickname.keySet()) {
            if(game.isNameInGame(originalOrderToNickname.get(originalOrder))){
                playersNicknamesInOrder.add(originalOrderToNickname.get(originalOrder));
            }
        }
    }

    public ArrayList<String> getPlayersNicknamesInOrder() {
        return playersNicknamesInOrder;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.addPlayersNicknamesAndOrder(playersNicknamesInOrder);
                    socket.setupChoiceBoxAndNickname();
                }
            });
        }
        else {
            printPlayerOrder(playersNicknamesInOrder);
        }
    }

    private void printPlayerOrder(ArrayList<String> playersNicknamesInOrder) {
        System.out.println("The order has been randomized! Here's the list of players:");
        int gameSize = playersNicknamesInOrder.size();
        for (int i = 0; i < gameSize; i++) {
            switch (i) {
                case 0: {
                    System.out.println("First to play: " + playersNicknamesInOrder.get(0));
                    break;
                }
                case 1: {
                    System.out.println("Second to play: " + playersNicknamesInOrder.get(1));
                    break;
                }
                case 2: {
                    System.out.println("Third to play: " + playersNicknamesInOrder.get(2));
                    break;
                }
                case 3: {
                    System.out.println("Fourth to play: " + playersNicknamesInOrder.get(3));
                    break;
                }
            }
        }
        System.out.println("");
    }
}
