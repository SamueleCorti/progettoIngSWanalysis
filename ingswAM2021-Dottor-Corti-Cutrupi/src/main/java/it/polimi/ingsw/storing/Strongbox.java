package it.polimi.ingsw.storing;

import it.polimi.ingsw.resource.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Strongbox {
    Map<String,ArrayList<Resource>> strongbox;

    public int lengthOfStrongbox(){
        return strongbox.size();
    }

    public int amountOfResource(Resource resourceToLookFor){
        if(strongbox.get(resourceToLookFor.getResourceType())!=null){
            return strongbox.get(resourceToLookFor.getResourceType()).size();
        }
        return 0;
    }

    public void addResource(Resource newResource){
        if(strongbox.get(newResource.getResourceType())!=null){
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
        }
    }
}
