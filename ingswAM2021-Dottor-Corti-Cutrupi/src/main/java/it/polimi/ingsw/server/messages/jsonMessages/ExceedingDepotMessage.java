package it.polimi.ingsw.server.messages.jsonMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.server.messages.Message;
import javafx.application.Platform;

/**
 * Used to serialize the depots in warehouse. It creates a matrix containing two columns, the first containing the type of resource it contains, the second its quantity
 */
public class ExceedingDepotMessage implements Message {
    int[][] depots;
    int sizeOfWarehouse;
    int sizeOfExtraDepots;


    public ExceedingDepotMessage(Dashboard dashboard) {
        sizeOfWarehouse=dashboard.getWarehouse().realSizeOfWarehouse();
        sizeOfExtraDepots=dashboard.getExtraDepots().size();
        SerializationConverter serializationConverter = new SerializationConverter();
        depots= new int[dashboard.getWarehouse().realSizeOfWarehouse()+dashboard.getExtraDepots().size()][2];
        if(sizeOfWarehouse<4) {
            for (int i = 0; i < sizeOfWarehouse; i++) {
                depots[i][0] = serializationConverter.resourceTypeToInt(dashboard.getWarehouse().returnTypeofDepot(3 - i));
                depots[i][1] = dashboard.getWarehouse().returnLengthOfDepot(3 - i);
            }
        }
        else {
            for (int i = 0; i < sizeOfWarehouse; i++) {
                depots[i][0] = serializationConverter.resourceTypeToInt(dashboard.getWarehouse().returnTypeofDepot(4 - i));
                depots[i][1] = dashboard.getWarehouse().returnLengthOfDepot(4 - i);
            }
        }
        for(int i=dashboard.getWarehouse().realSizeOfWarehouse(); i<dashboard.getWarehouse().realSizeOfWarehouse()+dashboard.getExtraDepots().size();i++){
            depots[i][0]= serializationConverter.resourceTypeToInt(dashboard.getExtraDepots().get(i-dashboard.getWarehouse().realSizeOfWarehouse()).getDepotType());
            depots[i][1]= dashboard.getExtraDepots().get(i-dashboard.getWarehouse().realSizeOfWarehouse()).getSize();
        }
    }

    public void execute(ClientSideSocket socket){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                socket.initializeExceedingDepot(depots,sizeOfWarehouse);
                socket.initializeExceedingResources(depots,sizeOfWarehouse);
            }
        });
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
