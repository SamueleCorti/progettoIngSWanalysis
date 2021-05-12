package it.polimi.ingsw.Communication.server.answers;

import it.polimi.ingsw.Game;

public class OrderMessage implements Message{
    String playerOrderMessage;

    /**
     * Used to give the players info about the order everyone will play according to
     * @param game
     */
    public OrderMessage(Game game) {
        int gameSize= game.getPlayers().size();
        String start= "The turn order of the players has been randomized; here are the results: \n";
        String oneP=        " 1st: "+game.getPlayers().get(0).getNickname()+ "\n";
        String towP=        " 2nd: "+game.getPlayers().get(1).getNickname()+ "\n";
        String threeP=      " 3rd: "+game.getPlayers().get(2).getNickname()+ "\n";
        String fourP=      " 4th: "+game.getPlayers().get(3).getNickname()+ "\n";
        switch (gameSize){
            case 2: {
                playerOrderMessage= start+oneP+ towP;
                break;
            }
            case 3: {
                playerOrderMessage= start+oneP+
                        towP+threeP;
                break;
            }
            case 4: {
                playerOrderMessage= start+oneP+ towP+threeP+fourP;
                break;
            }
            default: break;
        }

    }

    public String getPlayerOrder() {
        return playerOrderMessage;
    }
}
