package it.polimi.ingsw;

import it.polimi.ingsw.developmentcard.DevelopmentCardZone;
import it.polimi.ingsw.leadercard.LeaderCardZone;
import it.polimi.ingsw.leadercard.leaderpowers.LeaderPower;
import it.polimi.ingsw.leadercard.leaderpowers.PowerType;
import it.polimi.ingsw.papalpath.CardCondition;
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
    private ArrayList <ExtraDepot> extraDepots;
    private ArrayList<Resource> discountedResources;

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
        if (resourceToLookFor.equals(discountedResources.get(0)) || resourceToLookFor.equals(discountedResources.get(1))){
                    return warehouse.amountOfResource(resourceToLookFor)+strongbox.amountOfResource(resourceToLookFor)+1;
        }
        else return warehouse.amountOfResource(resourceToLookFor)+strongbox.amountOfResource(resourceToLookFor);
    }

    public Dashboard(int playerOrder) {
        this.warehouse = new Warehouse();
        this.strongbox = new Strongbox();
        this.leaderCardZone = new LeaderCardZone();
        for(int i=0; i<3; i++)        this.developmentCardZones.add(new DevelopmentCardZone());
        this.papalPath = new PapalPath(playerOrder);
        this.extraDepots= new ArrayList<ExtraDepot>();
    }

    public ArrayList<ExtraDepot> getExtraDepots() {
        if(extraDepots!=null)   return extraDepots;
        else return null;
    }

    public void activatedDiscountCard(Resource discountedResource){
        discountedResources.add(discountedResource);
    }
}
