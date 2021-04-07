package it.polimi.ingsw.storing;

import it.polimi.ingsw.resource.Resource;
import it.polimi.ingsw.resource.ResourceType;

import java.util.*;

public class Strongbox {
    Map<ResourceType,ArrayList<Resource>> strongbox;

    public Strongbox() {
        this.strongbox = new HashMap<>();
    }

    public int lengthOfStrongbox(){
        if(strongbox!=null) return strongbox.size();
        return 0;
    }

    public int amountOfResource(Resource resourceToLookFor){
        if(strongbox!=null && strongbox.get(resourceToLookFor.getResourceType())!=null){
            return strongbox.get(resourceToLookFor.getResourceType()).size();
        }
        return 0;
    }

    public void addResource(Resource newResource){
        if(strongbox!=null && strongbox.get(newResource.getResourceType())!=null){
            strongbox.get(newResource.getResourceType()).add(newResource);
        }
        else{
            ArrayList<Resource> list = new ArrayList<Resource>();
            list.add(newResource);
            strongbox.put(newResource.getResourceType(),list);
        }
    }

    public void removeResourceWithAmount(Resource resourceToRemove, int amountToRemove){
        boolean errorFound = false;
        try{
            if(strongbox.get(resourceToRemove.getResourceType())==null ||
                    strongbox.get(resourceToRemove.getResourceType()).size()<amountToRemove) {
                errorFound = true;
                throw new RegularityError();
            }
        }catch (RegularityError e1) {
            System.out.println(e1.toString());
        }
        if(errorFound==false){
            for(int i=0;i<amountToRemove;i++){
                strongbox.get(resourceToRemove.getResourceType()).remove(0);
            }
            if(strongbox.get(resourceToRemove.getResourceType()).size()==0){
                strongbox.remove(resourceToRemove.getResourceType());
            }
        }
    }

    public void removeResource(Resource resourceToRemove){
        boolean errorFound = false;
        try{
            if(strongbox.get(resourceToRemove.getResourceType())==null ) {
                errorFound = true;
                throw new RegularityError();
            }
        }catch (RegularityError e1) {
            System.out.println(e1.toString());
        }
        if(errorFound==false){
            strongbox.get(resourceToRemove.getResourceType()).remove(0);
            if(strongbox.get(resourceToRemove.getResourceType()).size()==0){
                strongbox.remove(resourceToRemove.getResourceType());
            }
        }
    }
}
