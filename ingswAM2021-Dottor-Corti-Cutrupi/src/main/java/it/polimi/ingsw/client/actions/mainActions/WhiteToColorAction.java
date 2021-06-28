package it.polimi.ingsw.client.actions.mainActions;

import it.polimi.ingsw.client.actions.tertiaryActions.TertiaryAction;
import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.server.messages.printableMessages.IncorrectPhaseMessage;

import java.util.ArrayList;

/**
 * Created when the player has two or more {@link it.polimi.ingsw.model.leadercard.leaderpowers.WhiteToColor} leader cards active and gets one or more blank marbles from
 * the market
 */
public class WhiteToColorAction implements TertiaryAction {
    private ArrayList<Integer> indexes;
    private boolean createdInGUI;

    /**
     * @param resourceTypes list of the card (e.g. 1,0,1 means that the player got 3 blanks, and substituted two of them with the second leader card resources, one with
     *                      the first
     * @param createdInGUI
     */
    public WhiteToColorAction(ArrayList<Integer> resourceTypes, boolean createdInGUI) {
        this.indexes = resourceTypes;
        this.createdInGUI=createdInGUI;
    }

    @Override
    public void execute(GameHandler gameHandler) {
        if(gameHandler.actionPerformedOfActivePlayer()==5){
            if(createdInGUI)    gameHandler.whiteToColorAction(indexes,-1);
            else    gameHandler.marketSpecialAction(indexes);
        }
        else gameHandler.sendMessageToActivePlayer(new IncorrectPhaseMessage());
    }

    @Override
    public void execute(GameHandler gameHandler, int clientID) {
        if(gameHandler.turnPhaseGivenNickname(clientID)==5){
            if(createdInGUI)    gameHandler.whiteToColorAction(indexes,clientID);
            else    gameHandler.marketSpecialAction(indexes);
        }
        else gameHandler.sendMessageToActivePlayer(new IncorrectPhaseMessage());
    }
}
