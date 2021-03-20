package it.polimi.ingsw;


import it.polimi.ingsw.resource.Resource;

import java.util.*;
import java.util.function.UnaryOperator;

public class Warehouse {
    private Map<Integer,List<Resource>> depot;

    public Warehouse() {
        this.depot = new HashMap<Integer,List<Resource>>();
        depot.put(1,null);
        depot.put(2,null);
        depot.put(3,null);
    }

    public void checkRegularity() throws RegularityError {
        //I'm checking that each size of the List is correct, all the resources of a List are equals (type is the same)
        // and different deposit have resources of different types

        if(depot.size()>3){
            //I have to delete one depot
            //           removeResources();
        }
        try {
            if (
                    (depot.get(1).size() <= 1 && depot.get(2).size() <= 2 && depot.get(3).size() <= 3) &&
                            (depot.get(2).get(0).getResourceType().equals(depot.get(2).get(1).getResourceType()) || null == depot.get(2).get(1)) &&
                            ((depot.get(3).get(0).getResourceType().equals(depot.get(3).get(1).getResourceType()) || null == depot.get(3).get(1)) &&
                                    (depot.get(3).get(1).getResourceType().equals(depot.get(3).get(2).getResourceType()) || null == depot.get(3).get(2))) &&
                            ((!depot.get(1).get(0).getResourceType().equals(depot.get(2).get(0).getResourceType()) || depot.get(1).get(0) == null || depot.get(2).get(0) == null)) &&
                            ((!depot.get(1).get(0).getResourceType().equals(depot.get(3).get(0).getResourceType()) || depot.get(1).get(0) == null || depot.get(3).get(0) == null)) &&
                            ((!depot.get(3).get(0).getResourceType().equals(depot.get(2).get(0).getResourceType()) || depot.get(3).get(0) == null || depot.get(2).get(0) == null))
            ) {
            } else {
                throw new RegularityError();
            }
        } catch (RegularityError e1) {
            System.out.println(e1.toString());
        }
        ;
    }



    public void swapResources() {
        //I want to move the depot containing a list with the biggest size on the list mapped with key=3
        // the second biggest on the one with key=2
        // and the smallest on the one with key=2
        int keymax=0;
        int lenghtmax=0;
        for(int i=0;i<4;i++){
            if(depot.get(i).size()>lenghtmax) {
                lenghtmax=depot.get(i).size();
                keymax=i;
            }
        }
        if(depot.get(3)!=null) {
            List<Resource> temp = new ArrayList<Resource>(depot.get(3));
            depot.get(3).replaceAll((UnaryOperator<Resource>) depot.get(keymax));
            depot.get(keymax).replaceAll((UnaryOperator<Resource>) temp);
        }
        if(depot.get(1).size()>depot.get(2).size()){
            List<Resource> temp = new ArrayList<Resource>(depot.get(2));
            depot.get(2).replaceAll((UnaryOperator<Resource>) depot.get(1));
            depot.get(1).replaceAll((UnaryOperator<Resource>) temp);
        }
    }


    public void addResource(Resource newResource)  {
        //We add the resource in the depot containing the same type of resource: if there is not and one depot is free
        // we add it into the free depot; if no depot is free, we add it into a fourth depot (CheckRegularity will resolve
        // the problem
        boolean found = false;
        int i=1;
        while(i<4 && found==false){
            if(depot.get(i).get(0).getResourceType()==newResource.getResourceType()){
                depot.get(i).add(newResource);
                found=true;
            }
            i++;
        }

        if(found==false){
            int lastAvailable=0;
            while (i<4){
                if(depot.get(i).size()>0){
                    lastAvailable=i;
                }
                i++;
            }
            if(lastAvailable!=0){
                depot.get(lastAvailable).add(newResource);
            }
        }
    }


    void removeResources(int a) {
        // We remove the last element of the depot with index a: if it is empty, nothing changes
    }
}
