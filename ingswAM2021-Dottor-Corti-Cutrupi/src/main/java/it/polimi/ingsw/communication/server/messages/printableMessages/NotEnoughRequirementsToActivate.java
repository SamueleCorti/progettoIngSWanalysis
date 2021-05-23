package it.polimi.ingsw.communication.server.messages.printableMessages;

public class NotEnoughRequirementsToActivate implements PrintableMessage {
    String string = "You dont have the requirements to activate this leader card";

    public String getString() {
        return string;
    }
}
