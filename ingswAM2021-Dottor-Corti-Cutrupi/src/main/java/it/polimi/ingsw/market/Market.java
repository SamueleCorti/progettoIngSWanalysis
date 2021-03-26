package it.polimi.ingsw.market;

import it.polimi.ingsw.Warehouse;
import it.polimi.ingsw.market.RNG;
import it.polimi.ingsw.papalpath.PapalPath;
import it.polimi.ingsw.resource.*;
import it.polimi.ingsw.storing.RegularityError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Market {

    Resource[][] market= new Resource[3][4];
    Resource floatingMarble;

    public Market() {
        Resource resource;
        int i = 0;
        ArrayList<Resource> resources= new ArrayList<>();

        for(int j=0; j<2; j++) {
            resources.add(new CoinResource());
            resources.add(new StoneResource());
            resources.add(new ServantResource());
            resources.add(new ShieldResource());
        }
        resources.add(new FaithResource());
        for(int j=0;j<4;j++)  resources.add(new BlankResource());
        Collections.shuffle(resources);
        for(int row = 0; row < 3; row++){
            for (int column = 0; column < 4; column++){
                market[row][column]= resources.get(i);
                i++;
            }
        }
        System.out.println("AAA");
        floatingMarble = resources.get(i);
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