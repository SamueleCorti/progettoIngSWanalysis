package it.polimi.ingsw.server.messages.jsonMessages;

import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.model.storing.Warehouse;
import it.polimi.ingsw.server.messages.Message;

public class DepotMessage implements Message {
    int[][] depots;
    int sizeOfWarehouse;
    int sizeOfExtraDepots;


    public DepotMessage(Dashboard dashboard) {
        sizeOfWarehouse=dashboard.getWarehouse().sizeOfWarehouse();
        sizeOfExtraDepots=dashboard.getExtraDepots().size();
        ResourceToIntConverter resourceToIntConverter= new ResourceToIntConverter();
        depots= new int[dashboard.getWarehouse().sizeOfWarehouse()+dashboard.getExtraDepots().size()][2];
        for(int i=0; i<dashboard.getWarehouse().sizeOfWarehouse(); i++){
            depots[i][0]= resourceToIntConverter.converter(dashboard.getWarehouse().returnTypeofDepot(i+1));
            depots[i][1]= dashboard.getWarehouse().returnLengthOfDepot(i+1);
        }
        for(int i=dashboard.getWarehouse().sizeOfWarehouse(); i<dashboard.getWarehouse().sizeOfWarehouse()+dashboard.getExtraDepots().size();i++){
            depots[i][0]= resourceToIntConverter.converter(dashboard.getExtraDepots().get(i).getDepotType());
            depots[i][1]= dashboard.getExtraDepots().get(i).getSize();
        }
    }

    public int[][] getDepots() {
        return depots;
    }

    public int getSizeOfWarehouse() {
        return sizeOfWarehouse;
    }

    public int getSizeOfExtraDepots() {
        return sizeOfExtraDepots;
    }
}