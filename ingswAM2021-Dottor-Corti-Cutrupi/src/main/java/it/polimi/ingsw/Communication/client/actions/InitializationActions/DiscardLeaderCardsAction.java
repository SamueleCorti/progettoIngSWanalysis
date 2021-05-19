package it.polimi.ingsw.Communication.client.actions.InitializationActions;

import it.polimi.ingsw.Communication.client.actions.Action;

import java.util.ArrayList;

/**
 * Action used during the initialization phase; it contains two indexes, representing the two leader cards the player wishes to discard.
 */
public class DiscardLeaderCardsAction implements Action {
    private ArrayList<Integer> indexes;


    public DiscardLeaderCardsAction(ArrayList<Integer> listOfIndexes) {
       this.indexes = listOfIndexes;
    }


    public ArrayList<Integer> getIndexes() {
        return indexes;
    }
}
