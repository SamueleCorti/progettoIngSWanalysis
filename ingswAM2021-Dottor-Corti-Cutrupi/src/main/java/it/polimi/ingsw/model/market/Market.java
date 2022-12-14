package it.polimi.ingsw.model.market;

import it.polimi.ingsw.exception.OutOfBoundException;
import it.polimi.ingsw.exception.PapalCardActivatedException;
import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.model.resource.*;
import it.polimi.ingsw.exception.warehouseErrors.WarehouseDepotsRegularityError;

import java.util.ArrayList;
import java.util.Collections;

public class Market {

    Resource[][] market= new Resource[3][4];
    Resource floatingMarble;

    /**
     * randomly generates the market, to make sure each game starts with a different set of resources available
     */
    public Market() {
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
        floatingMarble = resources.get(i);
    }

    /**
     * used for testing, formats the matrix in a readable way
     */
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

    public String getStringMarket(){
        String string=new String();
        for(int row=0; row<3; row++){
            for(int column=0;column<4;column++){
                string+= market[row][column].getResourceType()+"\t";
            }
            string+= "\n";
        }
        string+="\t\t\t\t\t\t\t\t"+floatingMarble.getResourceType();
        return string;
    }

    /**
     *this method returns the resources from a column/row of the market one by one, then call the method to push that same column/row.
     * If a papal favor card gets activated during the process, it call{@link #finishMarket(boolean, int, Dashboard, int)}
     * @param isRow: true if the player wants a row, flase if he wants a column
     * @param index: index of the row/column: o for the 1st, 1 for the 2nd, and so on. Up to 2 for rows, 3 for columns
     * @param dashboard: needed to place the resources in the intended place
     * @throws PapalCardActivatedException : warns the model to check the papal faith position for all players
     * @throws WarehouseDepotsRegularityError : the warehouse contains too many resources, and some need to be discarded
     */
    public void acquireResourcesFromMarket(boolean isRow, int index, Dashboard dashboard) throws WarehouseDepotsRegularityError, PapalCardActivatedException {
        //if the user requires a line that doesn't exists the system notifies the error, but the market itself doesn't neither change nor returns anything
        if (isRow) {
                for (int column = 0; column < 4; column++) {
                    try {
                        market[index][column].effectFromMarket(dashboard);
                    } catch (PapalCardActivatedException e) {
                        finishMarket(isRow, index, dashboard, column+1);
                        throw new PapalCardActivatedException(e.getIndex());
                    }
                }
            } else {
                for (int row = 0; row < 3; row++) {
                    try {
                        market[row][index].effectFromMarket(dashboard);
                    } catch (PapalCardActivatedException e) {
                        finishMarket(isRow, index, dashboard, row+1);
                        throw new PapalCardActivatedException(e.getIndex());
                    }
                }
            }
            pushLine(isRow, index);
            dashboard.getWarehouse().swapResources();
    }

    /**
     * Gets the remaining resources from the line, then throws the exception
     * @throws PapalCardActivatedException : warns the model to check the papal faith position for all players
     */
    public void finishMarket(boolean isRow, int index, Dashboard dashboard, int lineReached) throws PapalCardActivatedException {
        if (isRow){
            for (int column=lineReached; column < 4; column++) {
                market[index][column].effectFromMarket(dashboard);
            }
        }
        else {
            for (int row=lineReached; row<3; row++) {
                market[row][index].effectFromMarket(dashboard);
            }
        }
    }


    /**
     * @return the number of blanks in the set row/column. Used to understand how to act when a player has two
     * different whiteToColor leader cards active
     */
    public int checkNumOfBlank(boolean isRow, int index) throws OutOfBoundException {
        boolean faultyIndex=false;
        int numOfBlank=0;
        if((isRow && index>2) || (!isRow && index>3)) {
            throw new OutOfBoundException();
        }

        //if the user requires a line that doesn't exists the system notifies the error, but the market itself doesn't neither change nor returns anything
        if(!faultyIndex) {
            if (isRow) {
                for (int column = 0; column < 4; column++) {
                    if(market[index][column].getResourceType().equals(ResourceType.Blank)) numOfBlank++;
                }
            } else{
                for (int row = 0; row < 3; row++) {
                    if(market[row][index].getResourceType().equals(ResourceType.Blank)) numOfBlank++;
                }
            }
        }
        return numOfBlank;
    }

    /**
     * after a line is selected and has given the player its resources, this method pushes it by one
     */
    public void pushLine (boolean isRow, int index){
        Resource temp1,temp2,temp3;
        if(isRow){
            temp1= market[index][1];
            temp2= market[index][2];
            temp3= market[index][3];
            market[index][3]=floatingMarble;
            market[index][2]=temp3;
            market[index][1]=temp2;
            floatingMarble= market[index][0];
            market[index][0]=temp1;
        }
        if(!isRow){
            temp1= market[1][index];
            temp2= market[2][index];
            market[2][index]=floatingMarble;
            floatingMarble=market[0][index];
            market[0][index]=temp1;
            market[1][index]=temp2;
        }
    }

    /**
     * constructor used for testing
     */
    public Market(Resource resource1,Resource resource2,Resource resource3,Resource resource4,Resource resource5,Resource resource6,Resource resource7,Resource resource8,Resource resource9,Resource resource10,Resource resource11,Resource resource12,Resource resource13) {
        market[0][0]= resource1;
        market[0][1]= resource2;
        market[0][2]= resource3;
        market[0][3]= resource4;
        market[1][0]= resource5;
        market[1][1]= resource6;
        market[1][2]= resource7;
        market[1][3]= resource8;
        market[2][0]= resource9;
        market[2][1]= resource10;
        market[2][2]= resource11;
        market[2][3]= resource12;
        floatingMarble= resource13;
    }

    //used for testing
    public Resource getSingleResource(int row, int column){
        return market[row][column];
    }

    public Resource getFloatingMarble(){
        return floatingMarble;
    }

    /**
     * @return the resource type for a given position in the market
     */
    public ResourceType reresourceTypeInMarket(int row, int column) {
        return market[row][column].getResourceType();
    }
}