package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;

/**
 * Self explanatory name
 */
public class FourthDepot implements PrintableMessage{
    private String string = "There's a fourth depot in the warehouse, you must delete one\n"+"To do so, you have to perform a delete depot action [e.g. deletedepot 4]";

    public String getString() {
        return string;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        System.out.println(string);
    }
}
