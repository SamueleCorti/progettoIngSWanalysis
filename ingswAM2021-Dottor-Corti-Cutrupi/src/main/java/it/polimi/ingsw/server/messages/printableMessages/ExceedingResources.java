package it.polimi.ingsw.server.messages.printableMessages;

public class ExceedingResources implements PrintableMessage{
    String string = "There's an exceeding amount of resources in one depot of the warehouse,\n" +"To do so, you have to perform a discard resource action [e.g. discardresources coin stone]";

    public String getString() {
        return string;
    }

}
