package it.polimi.ingsw.Communication.client.actions;

/**
 * Action used during the initialization phase; it contains two indexes, representing the two leader cards the player wishes to discard.
 */
public class DiscardTwoLeaderCardsAction implements Action {
    private final int index1;
    private final int index2;

    /**
     * Constructor of the {@link DiscardTwoLeaderCardsAction} class.
     * @param index1 Index of the first leader card to discard
     * @param index2 Index of the second leader card to discard
     */
    public DiscardTwoLeaderCardsAction(int index1, int index2) {
        this.index1 = index1;
        this.index2 = index2;
    }

    /**
     * @return The index representing the first card to discard
     */
    public int getIndex1() {
        return index1;
    }

    /**
     * @return The index representing the second card to discard
     */
    public int getIndex2() {
        return index2;
    }
}
