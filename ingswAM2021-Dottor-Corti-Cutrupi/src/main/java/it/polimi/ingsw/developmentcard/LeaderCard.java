package it.polimi.ingsw.developmentcard;

import it.polimi.ingsw.Dashboard;

import java.util.ArrayList;

public class LeaderCard {

    private ArrayList <Requirements> cardRequirements;
    private int victoryPoints;


    /*
    the part about the special powers is missing
    */
    /*
    the constructor is missing
     */

    public int getVictoryPoints() {
        return victoryPoints;
    }
    public boolean checkRequirements(Dashboard dashboard) {
        for(Requirements requirements: cardRequirements){
            if(requirements.checkRequirement(dashboard)==true){
            }
            else{
                return false;
            }
        }
        return true;
    }

}
