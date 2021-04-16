package it.polimi.ingsw;


import it.polimi.ingsw.resource.Resource;
import it.polimi.ingsw.resource.ResourceType;
import it.polimi.ingsw.storing.RegularityError;

import java.util.*;

public class Warehouse {
    private Map<Integer,List<Resource>> depot;

    public Warehouse() {
        this.depot = new HashMap<Integer,List<Resource>>();
        depot.put(1,null);
        depot.put(2,null);
        depot.put(3,null);
    }

    public ResourceType returnTypeofDepot(int key){
        if(depot.get(key)!=null && depot.get(key).size()>0){
            return depot.get(key).get(0).getResourceType();
        }
        return null;
    }

    public int returnLengthOfDepot(int key){
        if(depot.get(key)!=null){
            return depot.get(key).size();
        }
        return 0;
    }

    public int sizeOfWarehouse(){ return depot.size();}

    public List<Resource> getListWithIndex(int a){
        return depot.get(a);
    }

    public void checkRegularity() throws RegularityError {
        //I'm checking that each size of the List is correct, all the resources of a List are equals (type is the same)
        // and different deposit have resources of different types

        boolean errorFound = false;

        try{
            if(depot.size()>3) {
                throw new RegularityError();
            }
            else{
                int i=1;
                while(i<4 && errorFound==false){
                    try{
                        if(depot.get(i)!=null && depot.get(i).size()>i) {
                            errorFound = true;
                            throw new RegularityError();}
                    } catch (RegularityError e1) {
                        System.out.println(e1.toString());
                    }
                    i++;
                }
                if(errorFound==false){
                    for(int s=1;s<4;s++){
                        if(depot.get(s)!=null){
                            for(int j=0;j<depot.get(s).size();j++){
                                depot.get(s).get(j).notNewAnymore();
                            }
                        }
                    }
                }
            }
        }catch (RegularityError e1) {
            System.out.println(e1.toString());
        }

    }



    public void swapResources() throws RegularityError {
        //I want to move the depot containing a list with the biggest size on the list mapped with key=3
        // the second biggest on the one with key=2
        // and the smallest on the one with key=2
        if(depot.size()!=4){
            int keyMax=0;
            int[] depotlengths = new int[3];
            for(int i=1;i<4;i++){
                if(depot.get(i)==null) depotlengths[i-1]=0;
                else depotlengths[i-1]=depot.get(i).size();
            }

            int max=Math.max(depotlengths[0],Math.max(depotlengths[1],depotlengths[2]));
            int i=3;
            boolean found=false;
            while(i>0 && found==false){
                if(depot.get(i)!=null && depot.get(i).size()==max){
                    keyMax=i;
                    found=true;
                }
                i--;
            }

            if(keyMax!=3 && keyMax!=0){
                List<Resource> temp = new ArrayList<Resource>();
                if(depot.get(3)!=null) {
                    temp.addAll(depot.get(keyMax));
                    depot.get(keyMax).clear();
                    depot.get(keyMax).addAll(depot.get(3));
                    depot.get(3).clear();
                    depot.get(3).addAll(temp);
                }
            }

            if(depot.get(1)!=null && depot.get(1).size()>depot.get(2).size()){
                List<Resource> temp = new ArrayList<Resource>(depot.get(2));
                depot.get(2).clear();
                depot.get(2).addAll(depot.get(1));
                depot.get(1).clear();
                depot.get(1).addAll(temp);
            }
        }
        checkRegularity();
    }

    public int amountOfResource(Resource resourceToLookFor){
        for(int i=1;i<4;i++){
            if(depot.get(i)!=null && depot.get(i).size()!=0 && depot.get(i).get(0).getResourceType()==resourceToLookFor.getResourceType()){
                return depot.get(i).size();
            }
        }
        return 0;
    }

    public void addResource(Resource newResource) {
        //We add the resource in the depot containing the same type of resource: if there is not and one depot is free
        // we add it into the free depot; if no depot is free, we add it into a fourth depot (CheckRegularity will resolve
        // the problem)
        boolean found = false;
        int i=1;
        while(i<5 && found==false){
            if(depot.get(i)!=null && depot.get(i).size()>0 && depot.get(i).get(0).getResourceType()==newResource.getResourceType()){
                depot.get(i).add(newResource);
                found=true;
            }
            i++;
        }

        if(found==false){
            i=1;
            int lastAvailable=0;
            while (i<4){
                if(depot.get(i)==null || depot.get(i).size()==0){
                    lastAvailable=i;
                }
                i++;
            }
            if(depot.get(lastAvailable)==null && lastAvailable!=0){
                List<Resource> list = new ArrayList<>();
                list.add(newResource);
                depot.put(lastAvailable,list);
            }
            else if(lastAvailable!=0){
                depot.get(lastAvailable).add(newResource);
            }
            else{
                List<Resource> list = new ArrayList<>();
                list.add(newResource);
                depot.put(4,list);
            }
        }
    }

    public int removeResource(Resource resourceToRemove, int amountToRemove)  {
        boolean found = false;
        int i=0;
        int indexFound=0;
        while(found==false && i<5){
            if(depot.get(i)!=null && depot.get(i).size()>0 && depot.get(i).get(0).getResourceType()==resourceToRemove.getResourceType()){
                found=true;
                indexFound=i;
            }
            i++;
        }
        if(found==true){
            if(depot.get(indexFound).size()>=amountToRemove){
                for(i=0;i<amountToRemove;i++){
                    depot.get(indexFound).remove(0);
                }
                return amountToRemove;
            }
            else{
                int removedSize = depot.get(indexFound).size();
                depot.get(indexFound).clear();
                return removedSize;
            }
        }
        return 0;
    }

    public void removeResource(int a)  {
        // We remove the last element of the depot with index a: if it is empty, nothing changes

        try {
            if(!depot.get(a).get(depot.get(a).size() - 1).getIsNew()) throw new RegularityError();
        }catch (RegularityError e1){
            System.out.println(e1.toString());
        }
        depot.get(a).remove(depot.get(a).size() - 1);
    }

    public int removeExceedingDepot(int a) throws RegularityError {
        //If the depot to remove is the fourth, we simply delete it. Instead if it's not we have to replace all the
        // element in the list with index "a" with the element from the fourth depot (we have to check if all the elements
        // in the list with index "a" are new)
        boolean errorFound = false;
        try {
            if(!depot.get(a).get(0).getIsNew()){
                errorFound = true;
                throw new RegularityError();
            }
        }catch (RegularityError e1){
            System.out.println(e1.toString());
        }

        int removedSize = 0;
        if(errorFound==false){
            if(a==4){
                removedSize=depot.get(a).size();
                depot.remove(4);
            }
            else if(a<4){
                removedSize=depot.get(a).size();
                depot.get(a).clear();
                depot.get(a).addAll(depot.get(4));
                depot.remove(4);
            }
        }
        return removedSize;
    }
}
