package it.polimi.ingsw.market;

import it.polimi.ingsw.Warehouse;
import it.polimi.ingsw.market.RNG;
import it.polimi.ingsw.papalpath.PapalPath;
import it.polimi.ingsw.resource.*;
import it.polimi.ingsw.storing.RegularityError;

import java.util.List;

public class Market {

    Resource[][] market= new Resource[3][4];
    Resource floatingMarble;

    public Market() {
        RNG rng = new RNG(13);
        List<Integer> list = rng.returnRNG();
        Resource resource;
        int i = 0;

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 4; column++) {
                int a = list.get(i);
                if (a == 1 || a == 2)   market[row][column] = new CoinResource();
                if (a == 3 || a == 4)   market[row][column] = new StoneResource();
                if (a == 5 || a == 6)   market[row][column] = new ServantResource();
                if (a == 7 || a == 8)   market[row][column] = new ShieldResource();
                if (a == 9)             market[row][column] = new FaithResource();
                else if(a>9)            market[row][column] = new BlankResource();
                i++;
            }
        }
        int a = list.get(i);
        if (a == 1 || a == 2) floatingMarble = new CoinResource();
        if (a == 3 || a == 4) floatingMarble = new StoneResource();
        if (a == 5 || a == 6) floatingMarble = new ServantResource();
        if (a == 7 || a == 8) floatingMarble = new ShieldResource();
        if (a == 9)           floatingMarble = new FaithResource();
        else                  floatingMarble = new BlankResource();


    }

    public void printMarket(){
        for(int column=0; column<4; column++){
            for(int row=0;row<3;row++){
                System.out.print(market[row][column].getResourceType()+"\t");
            }
            System.out.println();
        }
    }

    public void getResourcesFromMarket(boolean isRow, int index, Warehouse warehouse, PapalPath papalPath) throws OutOfBoundException,RegularityError {
        boolean faultyIndex=false;
        try{
            if((isRow && index>3) || (!isRow && index>2)) {
                faultyIndex=true;
                throw new OutOfBoundException();
            }
        }
        catch (OutOfBoundException error){
            System.out.println(error.toString());
        }
        if (isRow && !faultyIndex){
            for(int column=0;column<4;column++){
                market[index][column].effectFromMarket(warehouse,papalPath);
            }
        }
        else if(!isRow && !faultyIndex){
            for(int row=0; row<3; row++){
                market[row][index].effectFromMarket(warehouse,papalPath);
            }
        }
        warehouse.swapResources();
        pushLine(isRow, index);
    }

    public void pushLine (boolean isRow, int index){
        Resource temp1,temp2,temp3;
        if(isRow){
            temp1= market[index][0];
            temp2= market[index][1];
            temp3= market[index][2];
            market[index][0]=floatingMarble;
            market[index][1]=temp1;
            market[index][2]=temp2;
            floatingMarble= market[index][3];
            market[index][3]=temp3;

        }
        if(!isRow){
            temp1= market[0][index];
            temp2= market[1][index];
            market[0][index]=floatingMarble;
            floatingMarble=market[2][index];
            market[1][index]=temp1;
            market[2][index]=temp2;
        }
    }



}