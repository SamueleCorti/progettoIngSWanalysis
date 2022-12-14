package it.polimi.ingsw.client.actions.initializationActions;

import it.polimi.ingsw.client.actions.Action;

import java.util.ArrayList;

/**
 * Action used during the initialization phase; it contains two indexes, representing the two leader cards the player wishes to discard.
 */
public class DiscardLeaderCardsAction implements Action {
    private ArrayList<Integer> indexes;

    /**
     * @param listOfIndexes: Indexes of the cards to discard
     */
    public DiscardLeaderCardsAction(ArrayList<Integer> listOfIndexes) {
       this.indexes = listOfIndexes;
    }


    public ArrayList<Integer> getIndexes() {
        return indexes;
    }
}
