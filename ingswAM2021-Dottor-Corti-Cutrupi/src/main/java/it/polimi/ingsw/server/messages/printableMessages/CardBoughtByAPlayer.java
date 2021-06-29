package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.model.developmentcard.Color;

/**
 * Self explanatory name
 */
public class CardBoughtByAPlayer implements PrintableMessage {
    private String string;

    public CardBoughtByAPlayer(String nickname, Color color, int cardLevel) {
        string="\n"+nickname+" has just bought the "+color+" dev card level "+cardLevel;
    }

    public String getString() {
        return string;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui) System.out.println(string);
    }
}
