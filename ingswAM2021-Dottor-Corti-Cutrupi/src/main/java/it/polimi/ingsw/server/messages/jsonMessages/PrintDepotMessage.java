package it.polimi.ingsw.server.messages.jsonMessages;

import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.model.storing.Warehouse;
import it.polimi.ingsw.server.messages.Message;

public class PrintDepotMessage implements Message {
    int[][] depots;


    public PrintDepotMessage(Dashboard dashboard) {
        ResourceToIntConverter resourceToIntConverter= new ResourceToIntConverter();
        depots= new int[dashboard.getWarehouse().sizeOfWarehouse()+dashboard.getExtraDepots().size()][2];
        for(int i=1; i<4; i++){
            depots[i-1][0]= resourceToIntConverter.converter(dashboard.getWarehouse().returnTypeofDepot(i));
            depots[i-1][1]= dashboard.getWarehouse().returnLengthOfDepot(i);
        }
        for(int i=0; i<dashboard.getExtraDepots().size();i++){
            depots[i+3][0]= resourceToIntConverter.converter(dashboard.getExtraDepots().get(i).getDepotType());
            depots[i+3][1]= dashboard.getExtraDepots().get(i).getSize();
        }
    }
}
