package it.polimi.ingsw.server.messages.showingMessages;

import it.polimi.ingsw.adapters.Parser;
import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.storing.Strongbox;
import it.polimi.ingsw.server.messages.Message;
import javafx.application.Platform;

import java.util.ArrayList;

/**
 * Self explanatory name
 */
public class StrongboxMessage implements Message {
    private int[] resourcesContained;
    private int size;

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
        size=strongbox.sizeOfStrongbox();
        resourcesContained = tempArray;
    }

    public StrongboxMessage(int[] resources){
        this.resourcesContained = resources;
    }



    public int[] getResourcesContained() {
        return resourcesContained;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    socket.refreshStrongbox(new StrongboxMessage(resourcesContained));
                }
            });
        }
        else {
            printStrongbox(this);
        }
    }

    public void printStrongbox(StrongboxMessage message){
        SerializationConverter parser = new SerializationConverter();
        if(size>0){
            System.out.println("Resources in the strongbox:");
            System.out.println(parser.parseIntArrayToStringOfResourcesPretty(message.getResourcesContained()));
        }
    }
}
