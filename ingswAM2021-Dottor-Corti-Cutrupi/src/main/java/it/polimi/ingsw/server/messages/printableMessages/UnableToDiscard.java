package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.model.resource.ResourceType;

public class UnableToDiscard implements PrintableMessage {
    String string;

    public UnableToDiscard(ResourceType resourceType) {
        string= "It was impossible to remove a "+ resourceType+ " resource. It's likely that" +
                "it wasn't a resource just taken from market.";
    }

    public String getString() {
        return string;
    }
}
