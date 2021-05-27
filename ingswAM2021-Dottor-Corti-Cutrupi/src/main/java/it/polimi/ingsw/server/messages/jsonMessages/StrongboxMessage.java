package it.polimi.ingsw.server.messages.jsonMessages;

import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.storing.Strongbox;
import it.polimi.ingsw.server.messages.Message;

import java.util.ArrayList;

public class StrongboxMessage implements Message {
    private int[] resourcesContained;

    public StrongboxMessage(Strongbox strongbox, ArrayList<Resource> resourcesProduced){
        SerializationConverter converter = new SerializationConverter();
        int tempArray[]= new int[5];
        for(Resource resource: strongbox.getAllResources()){
            tempArray[converter.parseResourceToInt(resource)]+=1;
        }
        if(resourcesProduced!=null) {
            for (Resource resource : resourcesProduced) {
                tempArray[converter.parseResourceToInt(resource)] += 1;
            }
        }

        resourcesContained = tempArray;
    }



    public int[] getResourcesContained() {
        return resourcesContained;
    }
}
