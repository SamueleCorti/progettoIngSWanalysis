package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;

/**
 * Self explanatory name
 */
public class ResourcesUsableForProd implements PrintableMessage {
    private String string;

    public ResourcesUsableForProd(String s) {
        string = "Resources available for more productions: "+s;
    }

    public String getString() {
        return string;
    }


    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui) System.out.println(string);
    }
}
