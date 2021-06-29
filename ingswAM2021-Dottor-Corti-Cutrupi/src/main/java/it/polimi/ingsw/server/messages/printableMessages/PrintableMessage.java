package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.server.messages.Message;

/**
 * Interface for all messages that notify the client of something
 */
public interface PrintableMessage extends Message {
    public String getString();

}
