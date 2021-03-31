package it.polimi.ingsw;

import it.polimi.ingsw.developmentcard.DevelopmentCardZone;
import it.polimi.ingsw.leadercard.LeaderCardZone;
import it.polimi.ingsw.papalpath.PapalPath;
import it.polimi.ingsw.resource.Resource;
import it.polimi.ingsw.storing.ExtraDepot;
import it.polimi.ingsw.storing.RegularityError;
import it.polimi.ingsw.storing.Strongbox;

import java.util.ArrayList;

public class Dashboard {
    private Warehouse warehouse;
    private Strongbox strongbox;
    private LeaderCardZone leaderCardZone;
    private ArrayList <DevelopmentCardZone> developmentCardZones;
    private PapalPath papalPath;
    private ArrayList<ExtraDepot> extraDepots;
    private ArrayList<Resource> whiteToColorResources;
    private ArrayList<Resource> discountedResources;
    private ArrayList<Resource> resourcesForExtraProd;

    public void activatedDiscountCard(Resource discountedResource){
        discountedResources.add(discountedResource);
    }

    public ArrayList<Resource> getWhiteToColorResources() {
        return whiteToColorResources;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Strongbox getStrongbox() {
        return strongbox;
    }

    public LeaderCardZone getLeaderCardZone() {
        return leaderCardZone;
    }

    public PapalPath getPapalPath() {
        return papalPath;
    }

    public ArrayList<DevelopmentCardZone> getDevelopmentCardZones() {
        return developmentCardZones;
    }

    public int totalAmountOfResources(Resource resourceToLookFor){
        return warehouse.amountOfResource(resourceToLookFor)+strongbox.amountOfResource(resourceToLookFor);
    }

    public Dashboard(int playerOrder) {
        this.warehouse = new Warehouse();
        this.strongbox = new Strongbox();
        this.leaderCardZone = new LeaderCardZone();
        this.developmentCardZones = new ArrayList<DevelopmentCardZone>();
        for(int i=0; i<3; i++)        this.developmentCardZones.add(new DevelopmentCardZone());
        this.papalPath = new PapalPath(playerOrder);
        this.extraDepots= new ArrayList<ExtraDepot>();
        this.whiteToColorResources = new ArrayList<Resource>();
        this.discountedResources = new ArrayList<Resource>();
        this.resourcesForExtraProd = new ArrayList<Resource>();
    }

    public void activateProd(DevelopmentCardZone zoneToActivate) throws RegularityError {
        zoneToActivate.getOnTopCard().produce(this);
    }

    public ArrayList<Resource> getResourcesForExtraProd() {
        return resourcesForExtraProd;
    }

    public ArrayList<ExtraDepot> getExtraDepots() {
        if(extraDepots!=null)   return extraDepots;
        else return null;
    }
}
