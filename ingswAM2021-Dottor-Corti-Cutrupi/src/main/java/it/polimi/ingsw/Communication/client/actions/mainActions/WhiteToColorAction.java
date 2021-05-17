package it.polimi.ingsw.Communication.client.actions.mainActions;

import it.polimi.ingsw.Communication.client.actions.Action;
import it.polimi.ingsw.Communication.client.actions.secondaryActions.SecondaryAction;
import it.polimi.ingsw.Model.resource.Resource;
import it.polimi.ingsw.Model.resource.ResourceType;

import java.util.ArrayList;

public class WhiteToColorAction implements SecondaryAction {
    ArrayList<ResourceType> resourceTypes= new ArrayList<>();

    public ArrayList<ResourceType> getResourceTypes() {
        return resourceTypes;
    }

    public WhiteToColorAction(ArrayList<ResourceType> resourceTypes) {
        this.resourceTypes = resourceTypes;
    }
}
