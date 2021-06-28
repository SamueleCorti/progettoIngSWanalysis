package it.polimi.ingsw.client.actions.mainActions;

import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.server.messages.jsonMessages.PapalPathMessage;

/**
 * Action created when the player decides to get resources from market. It has a boolean isRow to represents if the client wants a row or a column,
 * and an int index to represent what row/index specifically he wants.
 */
public class MarketAction implements MainAction {
    private final int index;
    private final boolean isRow;

    /**
     * @param index: number of the row/column (starting form 0)
     * @param isRow : true if player wants a row, false for column
     */
    public MarketAction(int index, boolean isRow) {
        this.index = index;
        this.isRow = isRow;
    }

    public int getIndex() {
        return index;
    }

    public boolean isRow() {
        return isRow;
    }

    @Override
    public void execute(GameHandler gameHandler) {
            gameHandler.marketPreMove(index,isRow);
    }

}
