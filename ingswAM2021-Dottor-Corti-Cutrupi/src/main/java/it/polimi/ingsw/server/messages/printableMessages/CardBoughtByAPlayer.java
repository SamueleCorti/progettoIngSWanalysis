package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.model.developmentcard.Color;

public class CardBoughtByAPlayer implements PrintableMessage {
    String string;

    public CardBoughtByAPlayer(String nickname, Color color, int cardLevel) {
        string="\n"+nickname+" has just bought the "+color+" dev card level "+cardLevel;
    }

    public String getString() {
        return string;
    }
}
