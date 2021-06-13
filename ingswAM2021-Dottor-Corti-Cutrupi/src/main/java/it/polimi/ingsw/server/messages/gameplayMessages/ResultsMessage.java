package it.polimi.ingsw.server.messages.gameplayMessages;

import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.controller.Game;
import it.polimi.ingsw.adapters.NicknameVictoryPoints;

import java.util.ArrayList;

/**
 * Message used when the game is finished. Informs the players of the result, giving them the leaderboard.
 */

public class ResultsMessage implements Message {
    private ArrayList<String> playersInOrder;
    private ArrayList<Integer> playersPoints;


    public ResultsMessage(Game game) {
        playersInOrder = new ArrayList <String>();
        playersPoints = new ArrayList<Integer>();
        int gameSize= game.playersInGame().size();
        NicknameVictoryPoints[] leaderboard= game.leaderboard();

        for(int i=0;i<gameSize;i++) {
            switch (i) {
                case 0: {
                    playersInOrder.add(leaderboard[0].getNickname());
                    playersPoints.add(leaderboard[0].getVictoryPoints());
                    break;
                }
                case 1: {
                    playersInOrder.add(leaderboard[1].getNickname());
                    playersPoints.add(leaderboard[1].getVictoryPoints());
                    break;
                }
                case 2: {
                    playersInOrder.add(leaderboard[2].getNickname());
                    playersPoints.add(leaderboard[2].getVictoryPoints());
                    break;
                }
                case 3: {
                    playersInOrder.add(leaderboard[3].getNickname());
                    playersPoints.add(leaderboard[3].getVictoryPoints());
                    break;
                }
            }
        }
    }

    public ArrayList<String> getPlayersInOrder() {
        return playersInOrder;
    }

    public ArrayList<Integer> getPlayersPoints() {
        return playersPoints;
    }
}
