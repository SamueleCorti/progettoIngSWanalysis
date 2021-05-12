package it.polimi.ingsw.Communication.server.answers;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.Player;

public class ResultsMessage {
    String results;

    public ResultsMessage(Game game) {
        int gameSize= game.getPlayers().size();
        Player[] leaderboard= game.leaderboard();
        String start= "The game ended! Here are the results: \n";
        String oneP=        " 1st: "+leaderboard[gameSize-1].getNickname()+ " with "+ leaderboard[gameSize-1].getVictoryPoints()+" points. \n";
        String towP=        " 2nd: "+leaderboard[gameSize-2].getNickname()+ " with "+ leaderboard[gameSize-2].getVictoryPoints()+" points. \n";
        String threeP=      " 3rd: "+leaderboard[gameSize-3].getNickname()+ " with "+ leaderboard[gameSize-3].getVictoryPoints()+" points. \n";
        String fourP=       " 4th: "+leaderboard[gameSize-4].getNickname()+ " with "+ leaderboard[gameSize-4].getVictoryPoints()+" points. \n";
        switch (gameSize){
            case 2: {
                results= start+oneP+ towP;
                break;
            }
            case 3: {
                results= start+oneP+
                        towP+threeP;
                break;
            }
            case 4: {
                results= start+oneP+ towP+threeP+fourP;
                break;
            }
            default: break;
        }

    }

    public String getPlayerOrder() {
        return results;
    }
}
