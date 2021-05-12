package it.polimi.ingsw.Communication.client.messages.actions.mainActions;

import it.polimi.ingsw.resource.Resource;

import java.util.ArrayList;

public class MarketDoubleWhiteToColorAction extends MarketAction {
    private int index;
    private boolean isRow;
    private ArrayList<Resource> resources;

    public MarketDoubleWhiteToColorAction(int index, boolean isRow, ArrayList<Resource> resources) {
        super(index,isRow);
        this.resources=resources;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public boolean isRow() {
        return isRow;
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }
}
