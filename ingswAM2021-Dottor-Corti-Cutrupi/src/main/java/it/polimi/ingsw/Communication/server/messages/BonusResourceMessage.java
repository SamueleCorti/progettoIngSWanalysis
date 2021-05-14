package it.polimi.ingsw.Communication.server.messages;

/**
 * Message sent from the server to the players, informing them about the number of extra resources they're allowed to start with
 */
public class BonusResourceMessage implements Message{
    private String message;

    public BonusResourceMessage(int order) {
        message="As you're number "+ order+ " to play, you're going to choose ";
        if (order==2 || order==3) message+="one bonus resource to start with";
        else message+="two bonus resources to start with";
    }

    /**
     * @return the string, tailor made for each client (depending on its play order), composing the {@link BonusResourceMessage}
     */
    public String getMessage() {
        return message;
    }
}
