package it.polimi.ingsw.Communication.client.messages.actions.mainActions.productionActions;

import it.polimi.ingsw.Communication.client.messages.Message;
import it.polimi.ingsw.resource.Resource;

import java.util.ArrayList;

public class ResourcesCommunication implements Message {
    ArrayList<Resource> resourcesUsed;
    ArrayList<Resource> resourcesCreated;

    public ResourcesCommunication(ArrayList<Resource> resourcesUsed, ArrayList<Resource> resourcesCreated) {
        this.resourcesUsed = resourcesUsed;
        this.resourcesCreated = resourcesCreated;
    }

    public ArrayList<Resource> getResourcesUsed() {
        return resourcesUsed;
    }

    public ArrayList<Resource> getResourcesCreated() {
        return resourcesCreated;
    }
}
