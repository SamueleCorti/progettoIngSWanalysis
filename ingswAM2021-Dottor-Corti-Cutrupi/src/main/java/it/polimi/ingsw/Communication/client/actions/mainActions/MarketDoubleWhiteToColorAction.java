package it.polimi.ingsw.Communication.client.actions.mainActions;

import it.polimi.ingsw.resource.Resource;
import it.polimi.ingsw.resource.ResourceType;

import java.util.ArrayList;

public class MarketDoubleWhiteToColorAction extends MarketAction {
    private int index;
    private boolean isRow;
    private ArrayList<ResourceType> resources;

    public MarketDoubleWhiteToColorAction(int index, boolean isRow, ArrayList<ResourceType> resources) {
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

    public ArrayList<ResourceType> getResources() {
        return resources;
    }

    @Override
    public String toString() {
        return "MarketDoubleWhiteToColorAction{" +
                "index=" + index +
                ", isRow=" + isRow +
                ", resources=" + resources +
                '}';
    }
}
