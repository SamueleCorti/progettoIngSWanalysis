package it.polimi.ingsw;

import it.polimi.ingsw.storing.RegularityError;
import jdk.internal.net.http.common.Pair;

import java.util.ArrayList;

public class CustomPair<T,U> {
    private ArrayList<T> list1;
    private ArrayList<U> list2;

    public CustomPair(ArrayList<T> list1, ArrayList<U> list2) {
        if(list1.size()==list2.size()){
            this.list1 = new ArrayList<T>();
            this.list2 = new ArrayList<U>();
        }
    }

    public int size(){
        return list2.size();
    }

    public Pair<Object,Object> getPair(int index){
        Object obj1 = list1.get(index);
        Object obj2 = list2.get(index);
        Pair<Object,Object> objectToReturn = new Pair<Object,Object>(obj1,obj2);
        return objectToReturn;
    }

    public void addNewPair(T object1, U object2){
        list1.add(object1);
        list2.add(object2);
    }
}
