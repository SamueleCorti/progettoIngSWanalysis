package it.polimi.ingsw.communication.client.actions.mainActions;

import it.polimi.ingsw.communication.client.actions.secondaryActions.SecondaryAction;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.ArrayList;

public class WhiteToColorAction implements SecondaryAction {
    ArrayList<ResourceType> resourceTypes= new ArrayList<>();

    public ArrayList<ResourceType> getResourceTypes() {
        return resourceTypes;
    }

    @Override
    public String toString() {
        return "WhiteToColorAction{" +
                "resourceTypes=" + resourcesList() +
                '}';
    }

    private String resourcesList(){
        String s = "";
        for (ResourceType type:resourceTypes) {
            if(type.equals(ResourceType.Coin)) s+="coin ";
            if(type.equals(ResourceType.Stone)) s+="stone ";
            if(type.equals(ResourceType.Shield)) s+="shield ";
            if(type.equals(ResourceType.Servant)) s+="servant ";
        }
        return s;
    }

    public WhiteToColorAction(ArrayList<ResourceType> resourceTypes) {
        this.resourceTypes = resourceTypes;
    }
}
