package it.polimi.ingsw.server.messages.jsonMessages;

import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.server.messages.Message;

/**
 * Used to serialize the depots in warehouse. It creates a matrix containing two columns, the first containing the type of resource it contains, the second its quantity
 */
public class DepotMessage implements Message {
    int[][] depots;
    int sizeOfWarehouse;
    int sizeOfExtraDepots;


    public DepotMessage(Dashboard dashboard) {
        sizeOfWarehouse=dashboard.getWarehouse().realSizeOfWarehouse();
        sizeOfExtraDepots=dashboard.getExtraDepots().size();
        SerializationConverter serializationConverter = new SerializationConverter();
        depots= new int[dashboard.getWarehouse().realSizeOfWarehouse()+dashboard.getExtraDepots().size()][2];
        for(int i=0; i<dashboard.getWarehouse().realSizeOfWarehouse(); i++){
            depots[i][0]= serializationConverter.converter(dashboard.getWarehouse().returnTypeofDepot(3-i));
            depots[i][1]= dashboard.getWarehouse().returnLengthOfDepot(3-i);
        }
        for(int i=dashboard.getWarehouse().realSizeOfWarehouse(); i<dashboard.getWarehouse().realSizeOfWarehouse()+dashboard.getExtraDepots().size();i++){
            depots[i][0]= serializationConverter.converter(dashboard.getExtraDepots().get(i-dashboard.getWarehouse().realSizeOfWarehouse()).getDepotType());
            depots[i][1]= dashboard.getExtraDepots().get(i-dashboard.getWarehouse().realSizeOfWarehouse()).getDepot().size();
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
