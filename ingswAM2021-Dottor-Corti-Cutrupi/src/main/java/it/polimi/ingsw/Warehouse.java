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

    public String returnTypeofDepot(int key){
        if(depot.get(key)!=null){
            return depot.get(key).get(0).getResourceType();
        }
        return "null";
    }

    public int returnLenghtofDepot(int key){
        return depot.get(key).size();
    }

    public List<Resource> getListWithIndex(int a){
        return depot.get(a);
    }

    public void checkRegularity() throws RegularityError {
        //I'm checking that each size of the List is correct, all the resources of a List are equals (type is the same)
        // and different deposit have resources of different types

        boolean errorFound1 = false;
        boolean errorFound2 = false;

        try{
            if(depot.size()>3) {
                errorFound1 = true;
                throw new RegularityError();
            }
        }catch (RegularityError e1) {
            System.out.println(e1.toString());
        }

        if(errorFound1==false){
            for(int i=1;i<4;i++){
                try{
                    if(depot.get(i).size()>i) {
                        errorFound2 = true;
                        throw new RegularityError();}
                } catch (RegularityError e1) {
                    System.out.println(e1.toString());
                }
            }
        }

        if(errorFound1==false && errorFound2==false){
            for(int i=1;i<4;i++){
                for(int j=0;j<depot.get(i).size();j++){
                    depot.get(i).get(j).notNewAnymore();
                }
            }
        }

    }



    public void swapResources() throws RegularityError {
        //I want to move the depot containing a list with the biggest size on the list mapped with key=3
        // the second biggest on the one with key=2
        // and the smallest on the one with key=2
        int keymax=0;
        int lenghtmax=0;
        for(int i=0;i<4;i++){
            if(depot.get(i)!=null && depot.get(i).size()>lenghtmax) {
                lenghtmax=depot.get(i).size();
                keymax=i;
            }
        }
        if(keymax!=3){
            List<Resource> temp = new ArrayList<Resource>();
            if(depot.get(3)!=null) {
                temp.addAll(depot.get(keymax));
                depot.get(keymax).clear();
                depot.get(keymax).addAll(depot.get(3));
            }
            depot.get(3).clear();
            depot.get(3).addAll(temp);
        }

        if(depot.get(1)!=null && depot.get(1).size()>depot.get(2).size()){
            List<Resource> temp = new ArrayList<Resource>(depot.get(2));
            depot.get(2).clear();
            depot.get(2).addAll(depot.get(1));
            depot.get(1).clear();
            depot.get(1).addAll(temp);
        }
        checkRegularity();
    }


    public void addResource(Resource newResource){
        //We add the resource in the depot containing the same type of resource: if there is not and one depot is free
        // we add it into the free depot; if no depot is free, we add it into a fourth depot (CheckRegularity will resolve
        // the problem)
        boolean found = false;
        int i=1;
        while(i<4 && found==false){
            if(depot.get(i)!=null && depot.get(i).get(0).getResourceType()==newResource.getResourceType()){
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


    void removeResource(int a) throws RegularityError {
        // We remove the last element of the depot with index a: if it is empty, nothing changes

        if(!depot.get(a).get(depot.get(a).size() - 1).getIsNew()) System.out.println("Il depot contenente " + depot.get(a).get(0).getResourceType() + " e lungo " + depot.get(a).size() + " ha l'ultima biglia anziana");

        try {
            if(!depot.get(a).get(depot.get(a).size() - 1).getIsNew()) throw new RegularityError();
        }catch (RegularityError e1){
            System.out.println(e1.toString());
        }

        depot.get(a).remove(depot.get(a).size() - 1);
        swapResources();
    }

    int removeExceedingDepot(int a){
        //If the depot to remove is the fourth, we simply delete it. Instead if it's not we have to replace all the
        // element in the list with index "a" with the element from the fourth depot (we have to check if all the elements
        // in the list with index "a" are new)
        try {
            if(!depot.get(a).get(0).getIsNew()){
                throw new RegularityError();
            }
        }catch (RegularityError e1){
            System.out.println(e1.toString());
        }

        int removedSize = 0;
        if(a==4){
            removedSize=depot.get(a).size();
            depot.remove(4);
        }
        else if(a<4){
            removedSize=depot.get(a).size();
            depot.get(a).replaceAll((UnaryOperator<Resource>) depot.get(4));
            depot.remove(4);
        }

        return removedSize;
    }
}
