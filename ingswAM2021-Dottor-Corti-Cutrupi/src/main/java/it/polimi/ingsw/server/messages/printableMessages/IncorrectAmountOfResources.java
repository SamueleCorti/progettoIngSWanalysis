package it.polimi.ingsw.server.messages.printableMessages;

public class IncorrectAmountOfResources implements PrintableMessage {
    String string;

    public IncorrectAmountOfResources(int numOfStandardProdRequirements, int numOfStandardProdResults) {
        String string = "You insert an incorrect amount of resources, you must select "+
                numOfStandardProdRequirements+
                " resources to use and "+numOfStandardProdResults+" resources to produce!";
    }

    public String getString() {
        return string;
    }
}
