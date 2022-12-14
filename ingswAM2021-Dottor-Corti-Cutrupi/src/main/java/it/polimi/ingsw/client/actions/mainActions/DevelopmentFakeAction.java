package it.polimi.ingsw.client.actions.mainActions;

import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.server.messages.notifications.DevelopmentNotification;
import it.polimi.ingsw.model.developmentcard.Color;

/**
 * Action created when the player decides to buy a development card
 */
public class DevelopmentFakeAction implements MainAction {
    private final Color color;
    private final int cardLevel;
    private final int index;

    /**
     * The player chooses what card he wants
     * @param color: either blue/green/yellow/purple
     * @param cardLevel: 1 to 3
     * @param index: development card zone to place the card into
     */
    public DevelopmentFakeAction(Color color, int cardLevel, int index) {
        this.color = color;
        this.cardLevel = cardLevel;
        this.index = index;
    }

    public Color getColor() {
        return color;
    }

    public int getCardLevel() {
        return cardLevel;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "DevelopmentAction{" +
                "color=" + color +
                ", cardLevel=" + cardLevel +
                ", index=" + index +
                '}';
    }

    @Override
    public void execute(GameHandler gameHandler) {
        if(gameHandler.developmentFakeAction(color,cardLevel,index)) {
            gameHandler.sendAllExceptActivePlayer(new DevelopmentNotification(index, cardLevel, color, gameHandler.activePlayer().getNickname()));
        }
    }
}
