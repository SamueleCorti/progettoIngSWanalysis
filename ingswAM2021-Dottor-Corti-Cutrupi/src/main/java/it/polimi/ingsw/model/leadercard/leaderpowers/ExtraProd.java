package it.polimi.ingsw.model.leadercard.leaderpowers;

import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.adapters.ResourceDuplicator;

import java.util.ArrayList;

public class ExtraProd implements LeaderPower {
    private PowerType type= PowerType.ExtraProd;
    private ArrayList<Resource> resourcesRequired;

    public ExtraProd(ArrayList<Resource> resourcesRequired) {
        this.resourcesRequired = resourcesRequired;
    }

    /**
     *Method adds the related resource of this card to the dashboard, so it possible to use the corresponding
     * special production.
     */
    @Override
    public void activateLeaderPower(Dashboard dashboard) {
        /*for(int i=0; i<2; i++) {
            if(dashboard.getLeaderCardZone().getLeaderCards().get(i).getLeaderPower().equals(this));
            dashboard.getLeaderCardZone().getLeaderCards().get(i).setCondition(CardCondition.Active,dashboard);
        }*/
        /*for(Resource resource: resourcesRequired) {
            dashboard.getResourcesForExtraProd().add(resource);
        }*/
        dashboard.addToExtraProd(resourcesRequired);
    }

    @Override
    public ArrayList<Resource> returnRelatedResourcesCopy() {
        ResourceDuplicator duplicator= new ResourceDuplicator();
        ArrayList<Resource> resourcesCopy= new ArrayList<>();
        for(Resource resource:resourcesRequired)    resourcesCopy.add(duplicator.copyResource(resource));
        return resourcesCopy;
    }

    @Override
    public PowerType returnPowerType() {
        return type;
    }

    @Override
    public String toString() {
        String string= new String();
        for(Resource resource:resourcesRequired)    string+=resource.getResourceType()+"\t";
        return "allows you to produce "+ resourcesRequired.size()+ " resources of your choice after discarding these resources:\n" +string+
                " \nAlso allows you to move forward by "+ resourcesRequired.size()+" in the papal path";
    }
}
