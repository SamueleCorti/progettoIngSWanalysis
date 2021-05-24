package it.polimi.ingsw.model.leadercard.leaderpowers;

import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

public class WhiteToColor implements LeaderPower {

    private PowerType type= PowerType.WhiteToColor;
    private ArrayList<Resource> resourcesToCreate;

    public WhiteToColor(ArrayList<Resource> resourcesToCreate) {
        this.resourcesToCreate = resourcesToCreate;
    }

    /**
     *Method adds the related resource of this card to the dashboard, so it possible to transform a blank resource
     * in the market to the one of the related color in the warehouse.
     */
    @Override
    public void activateLeaderPower(Dashboard dashboard) {
        /*for(int i=0; i<2; i++) {
            if(dashboard.getLeaderCardZone().getLeaderCards().get(i).getLeaderPower().equals(this));
            dashboard.getLeaderCardZone().getLeaderCards().get(i).setCondition(CardCondition.Active,dashboard);
        }*/
        dashboard.addNewWhiteToColorEffect(resourcesToCreate);
    }

    @Override
    public PowerType returnPowerType() {
        return type;
    }

    public ArrayList<Resource> returnRelatedResourcesCopy() {
        return resourcesToCreate;
    }

    @Override
    public String toString() {
        String string= "for every blank resource you take from market, you're gonna instead get ";
        if(resourcesToCreate.size()>1){
            string+= "the following resources: ";
            for(Resource resource: resourcesToCreate)   string+= resource.getResourceType()+"\t";
        }
        else string+= "the following resource: "+ resourcesToCreate.get(0).getResourceType();
        return string;
    }
}
