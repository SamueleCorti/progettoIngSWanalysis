package it.polimi.ingsw;

import java.util.*;

//successfully creates a random ordinated list with no repetitions of elements
public class RNG {
    List<Integer> list;
    //define ArrayList to hold Integer objects
        public RNG(int length){
            Random randNum = new Random();

            Set<Integer> rng= new LinkedHashSet<Integer>();
            while (rng.size() < length) {
                rng.add(randNum.nextInt(length)+1);
            }
            System.out.println("Distinct random numbers = "+rng);
            list = new ArrayList<>(rng);
            System.out.println(Arrays.toString(list.toArray()));

        }
    public List<Integer> returnRNG(){
        return list;
        }
}