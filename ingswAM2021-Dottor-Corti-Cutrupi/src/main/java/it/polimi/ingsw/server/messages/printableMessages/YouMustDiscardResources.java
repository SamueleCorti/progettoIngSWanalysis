package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.server.messages.showingMessages.SerializationConverter;
import javafx.application.Platform;

/**
 * Self explanatory name
 */
public class YouMustDiscardResources implements PrintableMessage {
    private String string = "There's an exceeding amount of resources in one depot of the warehouse," +
            " you must delete resources to fix this problem"+"To do so, you have to perform a discard resource action " +
            "[e.g. discardresources coin stone]";
    private int[][] depots;
    private int sizeOfWarehouse;
    private int sizeOfExtraDepots;

    public String getString() {
        return string;
    }

    public YouMustDiscardResources(Dashboard dashboard) {
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

    public void execute(ClientSideSocket socket, boolean isGui){
        if (isGui)
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                socket.initializeExceedingResources(depots,sizeOfWarehouse);
                socket.changeStage("exceedingresources.fxml");
            }
        });
        else System.out.println(string);
    }
}
