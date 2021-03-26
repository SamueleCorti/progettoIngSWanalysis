package it.polimi.ingsw;

import it.polimi.ingsw.developmentcard.DevelopmentCardZone;
import it.polimi.ingsw.leadercard.LeaderCardZone;
import it.polimi.ingsw.papalpath.PapalPath;
import it.polimi.ingsw.resource.Resource;
import it.polimi.ingsw.storing.Strongbox;

import java.util.ArrayList;

public class Dashboard {

    Warehouse warehouse;
    Strongbox strongbox;
    LeaderCardZone leaderCardZone;
    ArrayList <DevelopmentCardZone> developmentCardZones;
    PapalPath papalPath;

    public ArrayList<DevelopmentCardZone> getDevelopmentCardZones() {
        return developmentCardZones;
    }

    public int totalAmountOfResources(Resource resourceToLookFor){
        return warehouse.amountOfResource(resourceToLookFor)+strongbox.amountOfResource(resourceToLookFor);
    }
}
