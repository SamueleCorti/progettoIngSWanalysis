package it.polimi.ingsw.server.messages.showingMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.server.messages.printableMessages.YouMustDeleteADepot;
import javafx.application.Platform;

/**
 * Used to serialize the depots in warehouse. It creates a matrix containing two columns, the first containing the type of resource it contains, the second its quantity
 */
public class ExceedingDepotMessage implements Message {
    private int[][] depots;
    private int sizeOfWarehouse;
    private int sizeOfExtraDepots;

    /**
     * Constructor, serializes the waehouse and shows it to the player
     */
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
            depots[i][0]= serializationConverter.resourceTypeToInt(dashboard.getExtraDepots().get(i-dashboard.getWarehouse().realSizeOfWarehouse()).getExtraDepotType());
            depots[i][1]= dashboard.getExtraDepots().get(i-dashboard.getWarehouse().realSizeOfWarehouse()).getAmountOfContainedResources();
        }
    }



    public int[][] getDepots() {
        return depots;
    }

    public int getSizeOfWarehouse() {
        return sizeOfWarehouse;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui)
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    new YouMustDeleteADepot().execute(socket,true);
                    socket.initializeExceedingDepot(depots,sizeOfWarehouse);
                    socket.initializeExceedingResources(depots,sizeOfWarehouse);
                }
            });
        else new YouMustDeleteADepot().execute(socket, false);
    }
}
