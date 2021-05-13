package it.polimi.ingsw.Communication.client.actions.mainActions;

/**
 * Action created when the player decides to get resources from market
 */
public class MarketAction implements MainAction {
    private int index;
    private boolean isRow;

    public MarketAction(int index, boolean isRow) {
        this.index = index;
        this.isRow = isRow;
    }

    //JUST FOR TESTING


    @Override
    public String toString() {
        return "MarketAction{" +
                "index=" + index +
                ", isRow=" + isRow +
                '}';
    }

    public int getIndex() {
        return index;
    }

    public boolean isRow() {
        return isRow;
    }
}
