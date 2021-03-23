package it.polimi.ingsw.storing;

import it.polimi.ingsw.resource.Resource;

import java.util.ArrayList;
import java.util.List;

public class Strongbox {
    List<List<Resource>> strongbox = new ArrayList<List<Resource>>();

    public int lengthOfStrongbox(){
        return strongbox.size();
    }

    public int depotOfStrongboxLength(Resource resourceToLookFor){
        int i=0;
        boolean found = false;
        while(i<strongbox.size() && found==false){
            if(strongbox.get(i).get(0).getResourceType()==resourceToLookFor.getResourceType()){
                found=true;
                return strongbox.get(i).size();
            }
            i++;
        }
        return 0;
    }

    public void addResource(Resource newResource){
        int i=0;
        int foundIndex=5;
        boolean found = false;
        while(i<strongbox.size() && found==false){
            if(strongbox.get(i).get(0).getResourceType()==newResource.getResourceType()){
                found=true;
                foundIndex=i;
            }
            i++;
        }
        if(found==false){
            List<Resource> temp = new ArrayList<Resource>();
            temp.add(newResource);
            strongbox.add(temp);
        }
        else{
            strongbox.get(foundIndex).add(newResource);
        }
    }

    public void removeResource(Resource newResource, int amountToRemove){
        boolean found = false;
        int i=0;
        while(i<strongbox.size() && found==false){
            if(strongbox.get(i).get(0).getResourceType()==newResource.getResourceType()){
                for(int j=0;j<amountToRemove;j++){
                    strongbox.get(i).remove(0);
                }
                found=true;
            }
            i++;
        }
    }
}
