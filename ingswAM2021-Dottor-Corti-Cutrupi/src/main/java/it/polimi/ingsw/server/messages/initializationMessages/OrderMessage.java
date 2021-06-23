package it.polimi.ingsw.server.messages.initializationMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.controller.Game;

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

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui) {
            socket.addPlayersNicknamesAndOrder(playersNicknamesInOrder);
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
