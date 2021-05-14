package it.polimi.ingsw.Communication.client.actions;

public class GenericAction implements Action{
    String message;

    public GenericAction(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
