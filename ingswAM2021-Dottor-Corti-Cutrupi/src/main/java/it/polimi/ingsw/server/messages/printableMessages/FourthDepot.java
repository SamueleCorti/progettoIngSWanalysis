package it.polimi.ingsw.server.messages.printableMessages;

public class FourthDepot implements PrintableMessage{
    String string = "There's a fourth depot in the warehouse, you must delete one\n"+"To do so, you have to perform a delete depot action [e.g. deletedepot 4]";

    public String getString() {
        return string;
    }

}
