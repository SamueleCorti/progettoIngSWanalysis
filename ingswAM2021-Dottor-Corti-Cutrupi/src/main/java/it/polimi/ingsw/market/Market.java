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
        for(int row=0; row<3; row++){
            for(int column=0;column<4;column++){
                System.out.print(market[row][column].getResourceType()+"\t");
            }
            System.out.println();
        }
        System.out.println("\t\t\t\t\t\t\t\t"+floatingMarble.getResourceType());
        System.out.println();
    }

    //this method returns the resources from a column/row of the market one by one, then call the method to push that same column/row
    public void getResourcesFromMarket(boolean isRow, int index, Warehouse warehouse, PapalPath papalPath) throws OutOfBoundException,RegularityError {
        boolean faultyIndex=false;
        try{
            if((isRow && index>2) || (!isRow && index>3)) {
                faultyIndex=true;
                throw new OutOfBoundException();
            }
        }
        catch (OutOfBoundException error){
            System.out.println(error.toString());
        }
        //if the user requires a line that doesn't exists the system notifies the error, but the market itself doesn't neither change nor returns anything
        if(!faultyIndex) {
            if (isRow) {
                for (int column = 0; column < 4; column++) {
                    market[index][column].effectFromMarket(warehouse, papalPath);
                }
            } else if (!isRow) {
                for (int row = 0; row < 3; row++) {
                    market[row][index].effectFromMarket(warehouse, papalPath);
                }
            }
            warehouse.swapResources();
            pushLine(isRow, index);
        }
    }

    //after a line is selected and has given the player its resources, this method pushes it by one
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