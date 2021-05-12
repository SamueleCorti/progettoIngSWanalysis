package it.polimi.ingsw.Communication.server.answers;

import it.polimi.ingsw.Game;

public class ResultsMessage {
    String results;

    public ResultsMessage(Game game) {
        int gameSize= game.getPlayers().size();
        String start= "The game ended! Here are the results: \n";
        String oneP=        " 1st: "+game.getPlayers().get(0).getNickname()+ "\n";
        String towP=        " 2nd: "+game.getPlayers().get(1).getNickname()+ "\n";
        String threeP=      " 3rd: "+game.getPlayers().get(2).getNickname()+ "\n";
        String fourP=      " 4th: "+game.getPlayers().get(3).getNickname()+ "\n";
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
