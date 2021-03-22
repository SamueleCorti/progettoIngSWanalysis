package it.polimi.ingsw.storing;

import it.polimi.ingsw.resource.Resource;

import java.util.ArrayList;
import java.util.List;

public class Strongbox {
    List<List<Resource>> strongbox = new ArrayList<List<Resource>>();

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
            strongbox.get(strongbox.size()).add(newResource);
        }
        else{
            strongbox.get(foundIndex).add(newResource);
        }
    }
}
