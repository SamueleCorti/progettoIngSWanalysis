package it.polimi.ingsw.communication.server.messages.gameplayMessages;

import it.polimi.ingsw.communication.server.messages.Message;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.boardsAndPlayer.Player;

/**
 * Message used when the game is finished. Informs the players of the result, giving them the leaderboard.
 */

public class ResultsMessage implements Message {
    private String results="";

    public ResultsMessage(Game game) {
        int gameSize= game.getGameBoard().getPlayers().size();
        Player[] leaderboard= game.leaderboard();
        String start= "The game ended! Here are the results: \n";
        String temp="";
        for(int i=0;i<gameSize;i++) {
            switch (i) {
                case 0: {
                    temp += " 1st: " + leaderboard[0].getNickname() + " with " + leaderboard[0].getVictoryPoints() + " points. \n";
                    break;
                }
                case 1: {
                    temp += " 2nd: " + leaderboard[1].getNickname() + " with " + leaderboard[1].getVictoryPoints() + " points. \n";
                    break;
                }
                case 2: {
                    temp += " 3rd: " + leaderboard[2].getNickname() + " with " + leaderboard[2].getVictoryPoints() + " points. \n";
                    break;
                }
                case 3: {
                    temp += " 4th: " + leaderboard[3].getNickname() + " with " + leaderboard[3].getVictoryPoints() + " points. \n";
                    break;
                }
            }
        }
        results=start+temp;
    }

    public String getResult() {
        return results;
    }
}
