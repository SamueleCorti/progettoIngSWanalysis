package it.polimi.ingsw.model.storing;


import it.polimi.ingsw.exception.warehouseErrors.*;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.*;

public class Warehouse {
    private final Map<Integer,List<Resource>> depot;

    public Warehouse() {
        this.depot = new HashMap<>();
        depot.put(1,null);
        depot.put(2,null);
        depot.put(3,null);
    }

    /**
     *when you insert the number of the depot you want to check, it returns the resourceType contained in the depot
     * (null if it contains nothing)
     */
    public ResourceType returnTypeofDepot(int key){
        if(depot.get(key)!=null && depot.get(key).size()>0){
            return depot.get(key).get(0).getResourceType();
        }
        return null;
    }

    /**
     *when you insert the number of the depot you want to check, it returns the amount of resources contained in it
     */
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

    public ArrayList<Resource> getAllResources(){
        ArrayList<Resource> list = new ArrayList<>();
        for(int i=1;i<4;i++){
            if(returnLengthOfDepot(i)!=0){
                list.addAll(getListWithIndex(i));
            }
        }
        return list;
    }

    /**
     *Checks that each size of the List(each list is a depot) is correct, all the resources of a List
     * are equals (type is the same) and different deposit have resources of different types
     */
    public void checkRegularity() throws WarehouseDepotsRegularityError {
        //There are 4 depots (one has to be removed)
        if(depot.size()>3) throw new FourthDepotWarehouseError();
        for(int i=1;i<4;i++){
            if(depot.get(i)!=null && depot.get(i).size()>i) {
                throw new TooManyResourcesInADepot();
            }
        }
        for(int s=1;s<4;s++){
            if(depot.get(s)!=null){
                for(int j=0;j<depot.get(s).size();j++){
                    depot.get(s).get(j).notNewAnymore();
                }
            }
        }
    }


    /**
     *Moves the depot containing a list with the biggest size on the list mapped with key=3
     * the second biggest on the one with key=2
     *  and the smallest on the one with key=2
     */
    public void swapResources() throws WarehouseDepotsRegularityError {
        //I want to move the depot containing a list with the biggest size on the list mapped with key=3
        // the second biggest on the one with key=2
        // and the smallest on the one with key=2
        if(depot.size()!=4){
            int keyMax=0;
            int[] depotLengths = new int[3];
            for(int i=1;i<4;i++){
                if(depot.get(i)!=null) depotLengths[i-1]=depot.get(i).size();
            }

            int max=Math.max(depotLengths[0],Math.max(depotLengths[1],depotLengths[2]));
            int i=3;
            boolean found=false;
            while(i>0 && !found){
                if(depot.get(i)!=null && depot.get(i).size()==max){
                    keyMax=i;
                    found=true;
                }
                i--;
            }

            if(keyMax!=3 && keyMax!=0){
                if(depot.get(3)!=null) {
                    List<Resource> temp = new ArrayList<>(depot.get(keyMax));
                    depot.get(keyMax).clear();
                    depot.get(keyMax).addAll(depot.get(3));
                    depot.get(3).clear();
                    depot.get(3).addAll(temp);
                }
            }

            if(depot.get(1)!=null && depot.get(1).size()>depot.get(2).size()){
                List<Resource> temp = new ArrayList<>(depot.get(2));
                depot.get(2).clear();
                depot.get(2).addAll(depot.get(1));
                depot.get(1).clear();
                depot.get(1).addAll(temp);
            }
        }
        checkRegularity();
    }

    /**
     *Returns the quantity of the resource searched
     */
    public int amountOfResource(Resource resourceToLookFor){
        for(int i=1;i<4;i++){
            if(depot.get(i)!=null && depot.get(i).size()!=0 && depot.get(i).get(0).getResourceType()==resourceToLookFor.getResourceType()){
                return depot.get(i).size();
            }
        }
        return 0;
    }

    /**
     *It adds the resource in the depot containing the same type of resource: if there is not and one depot is free
     *the method adds the resource into the free depot; if no depot is free, we add it into a fourth depot
     *(CheckRegularity will resolve the problem)
     */
    public void addResource(Resource newResource) {
        boolean found = false;
        int i=1;
        while(i<5 && !found){
            if(depot.get(i)!=null && depot.get(i).size()>0 && depot.get(i).get(0).getResourceType()==newResource.getResourceType()){
                depot.get(i).add(newResource);
                found=true;
            }
            i++;
        }

        if(!found){
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

    /**
     *Used when you have to remove multiple resources of the same type from the warehouse:
     * if the searched resource isn't in the warehouse, method returns 0 and removes nothing;
     * if the searched resource is in the warehouse but the amount in it is less than the amountToRemove,
     * method returns the size of the list containing that resource (so all the resource of that type contained in
     * the warehouse) and removes all the content of that list;
     * in the end if the amount of resources of the looked for type is >= amountToRemove, method returns
     * amountToRemove and removes as much resources of that type as amountToRemove
     */
    public int removeResource(Resource resourceToRemove, int amountToRemove)  {
        boolean found = false;
        int i=0;
        int indexFound=0;
        while(!found && i<5){
            if(depot.get(i)!=null && depot.get(i).size()>0 && depot.get(i).get(0).getResourceType()==resourceToRemove.getResourceType()){
                found=true;
                indexFound=i;
            }
            i++;
        }
        if(found){
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

    /**
     *It removes the last element of the depot with index a: if it is empty, nothing changes
     */
    public void removeResource(int a)  throws WarehouseDepotsRegularityError{
        if(!depot.get(a).get(depot.get(a).size() - 1).getIsNew()) throw new LastElementOfDepotNotNewError();
        depot.get(a).remove(depot.get(a).size() - 1);
    }


    /**
     *If the depot to remove is the fourth, it simply deletes it. Instead if it's not it has to replace all the
     *element in the list with index "a" with the element from the fourth depot (checking if all the elements
     *in the list with index "a" are new)
     */
    public int removeExceedingDepot(int a) throws WarehouseDepotsRegularityError {
        if(!depot.get(a).get(0).getIsNew()) throw new NotAllNewResourcesInDepotError();
        int removedSize = 0;
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
        return removedSize;
    }
}
