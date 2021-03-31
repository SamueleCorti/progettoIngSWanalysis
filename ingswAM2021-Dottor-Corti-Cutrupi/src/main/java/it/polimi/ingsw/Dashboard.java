package it.polimi.ingsw;

import it.polimi.ingsw.developmentcard.DevelopmentCardZone;
import it.polimi.ingsw.leadercard.LeaderCardZone;
import it.polimi.ingsw.papalpath.PapalPath;
import it.polimi.ingsw.resource.Resource;
import it.polimi.ingsw.storing.ExtraDepot;
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

    //returns the amount of resourceToLookFor when it is needed to buy a development card
    public int availableResourcesForDevelopment(Resource resourceToLookFor){
        int quantityInDepots=0;
        for(int i=0; i<extraDepots.size();i++){
            if(extraDepots.get(i).getExtraDepotType().equals(resourceToLookFor))    quantityInDepots+=extraDepots.get(i).getExtraDepotSize();
        }
        if(resourceToLookFor.equals(discountedResources.get(0)) || resourceToLookFor.equals(discountedResources.get(1)))
            return warehouse.amountOfResource(resourceToLookFor)+strongbox.amountOfResource(resourceToLookFor)+quantityInDepots+1;
        else return warehouse.amountOfResource(resourceToLookFor)+strongbox.amountOfResource(resourceToLookFor)+quantityInDepots;
    }


    public int availableResourcesForProduction(Resource resourceToLookFor){
        int quantityInDepots=0;
        for(int i=0; i<extraDepots.size();i++){
            if(extraDepots.get(i).getExtraDepotType().equals(resourceToLookFor))    quantityInDepots+=extraDepots.get(i).getExtraDepotSize();
        }
        return warehouse.amountOfResource(resourceToLookFor)+strongbox.amountOfResource(resourceToLookFor)+quantityInDepots;
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

    public ArrayList<Resource> getResourcesForExtraProd() {
        return resourcesForExtraProd;
    }

    public ArrayList<ExtraDepot> getExtraDepots() {
        if(extraDepots!=null)   return extraDepots;
        else return null;
    }
}
