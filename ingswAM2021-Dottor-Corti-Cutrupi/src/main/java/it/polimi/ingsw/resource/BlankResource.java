package it.polimi.ingsw.resource;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.Warehouse;
import it.polimi.ingsw.leadercard.leaderpowers.PowerType;
import it.polimi.ingsw.papalpath.CardCondition;
import it.polimi.ingsw.papalpath.PapalPath;

import java.util.ArrayList;

public class BlankResource implements Resource {
    int playerChoice = 0;

    public void setPlayerChoice(int playerChoice) {
        this.playerChoice = playerChoice;
    }

    @Override
    public String getResourceType() {
        return "blank";
    }

    @Override
    public void effectFromMarket(Dashboard dashboard) {
        ArrayList<Integer> indexWhiteToColor = new ArrayList<Integer>();
        for(int i=0;i<2;i++){
            if((dashboard.getLeaderCardZone().getLeaderCards().get(i).getCondition().equals(CardCondition.Active) &&
                    dashboard.getLeaderCardZone().getLeaderCards().get(i).getLeaderPower().returnPowerType().equals(PowerType.WhiteToColor))){
                indexWhiteToColor.add(i);
            }
        }

        if(indexWhiteToColor.size()==1){
            dashboard.getLeaderCardZone().getLeaderCards().get(indexWhiteToColor.get(0)).activateCardPower(dashboard);
        }
        else if(indexWhiteToColor.size()==2){
            //TODO: we have to create an interaction with the user where he selects the leader card to use, if he didn't choose
            // yet we have to wait his decision (and the rest of the program must not progress)
            if(dashboard.getIndexOfLCToUse()==0){

            }
        }
    }

    public void notNewAnymore(){}

    public boolean getIsNew(){
        return true;
    }
}