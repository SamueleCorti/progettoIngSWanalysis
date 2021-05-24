package it.polimi.ingsw.client.actions.mainActions;

import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.server.messages.notifications.DevelopmentNotification;
import it.polimi.ingsw.server.messages.printableMessages.MainActionAlreadyDoneMessage;
import it.polimi.ingsw.model.developmentcard.Color;
import it.polimi.ingsw.server.messages.printableMessages.YouMustDeleteADepotFirst;
import it.polimi.ingsw.server.messages.printableMessages.YouMustDiscardResourcesFirst;
import it.polimi.ingsw.server.messages.printableMessages.YouMustSelectWhiteToColorsFirst;

/**
 * Action created when the player decides to buy a development card
 */
public class DevelopmentAction implements MainAction {
    private final Color color;
    private final int cardLevel;
    private final int index;

    /**
     * The player chooses what card he wants
     * @param color: either blue/green/yellow/purple
     * @param cardLevel: 1 to 3
     * @param index: development card zone to place the card into
     */
    public DevelopmentAction(Color color, int cardLevel, int index) {
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
        //CASE: USER DIDN'T DO A MAIN ACTION YET SO HE CAN USE THIS
        if(gameHandler.actionPerformedOfActivePlayer()==0){
            if(gameHandler.developmentAction(color,cardLevel,index))
            {
                gameHandler.sendAllExceptActivePlayer(new DevelopmentNotification(index, cardLevel, color
                        ,gameHandler.activePlayer().getNickname()));
            }
        }
        else if(gameHandler.actionPerformedOfActivePlayer()==3){
            gameHandler.sendMessageToActivePlayer(new YouMustDeleteADepotFirst());
        }
        else if(gameHandler.actionPerformedOfActivePlayer()==4){
            gameHandler.sendMessageToActivePlayer(new YouMustDiscardResourcesFirst());
        }
        else if(gameHandler.actionPerformedOfActivePlayer()==5){
            gameHandler.sendMessageToActivePlayer(new YouMustSelectWhiteToColorsFirst());
        }
        else gameHandler.sendMessageToActivePlayer(new MainActionAlreadyDoneMessage());
    }
}
