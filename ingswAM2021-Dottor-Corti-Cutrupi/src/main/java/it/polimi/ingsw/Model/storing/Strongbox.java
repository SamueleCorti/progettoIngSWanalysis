package it.polimi.ingsw.Model.storing;

import it.polimi.ingsw.Model.resource.Resource;
import it.polimi.ingsw.Model.resource.ResourceType;

import java.util.*;

public class Strongbox {
    Map<ResourceType,ArrayList<Resource>> strongbox;

    public Strongbox() {
        this.strongbox = new HashMap<>();
    }

    /**
     *It returns the number of different types of resources contained in the strongbox
     * (if strongbox contains servans, coins and stones, method returns 3)
     */
    public int sizeOfStrongbox(){
        if(strongbox!=null) return strongbox.size();
        return 0;
    }

    /**
     *It returns the number of resourced contained in the strongbox that we are looking for
     * (if we are looking for coins, and strongbox contains 2 of them, method returns 2)
     */
    public int amountOfResource(Resource resourceToLookFor){
        if(strongbox!=null && strongbox.get(resourceToLookFor.getResourceType())!=null){
            return strongbox.get(resourceToLookFor.getResourceType()).size();
        }
        return 0;
    }

    /**
     *Method adds a resource in the strongbox: if resources of the same type are already contained in strongbox
     * it simply adds the resource in the tail of the list containing that type of resource.
     * If there is not a resource of that type yet, it creates a new list with the resource in it
     */
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

    /**
     *Used when you have to remove multiple resources of the same type from the strongbox:
     * if the searched resource isn't in the strongbox, method removes nothing;
     * else the method as much resources of that type as amountToRemove
     */
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

    /**
     *It removes the first element of the list containing that type of resource.
     * If then the list of that type is empty, it deletes the list
     */
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
