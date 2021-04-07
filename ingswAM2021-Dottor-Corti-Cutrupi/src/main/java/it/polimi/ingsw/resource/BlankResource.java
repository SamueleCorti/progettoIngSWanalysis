package it.polimi.ingsw.resource;

import it.polimi.ingsw.Dashboard;

public class BlankResource implements Resource {
    int playerChoice = 0;

    //added later; tests not updated for this
    private ResourceType resourceType = ResourceType.Blank;

    public void setPlayerChoice(int playerChoice) {
        this.playerChoice = playerChoice;
    }

    @Override
    public ResourceType getResourceType() {
        return resourceType;
    }

    @Override
    public void effectFromMarket(Dashboard dashboard) {
        if(dashboard.getWhiteToColorResources()!=null && dashboard.getWhiteToColorResources().size()>0){
            if(dashboard.getWhiteToColorResources().size()==1){
                dashboard.getWhiteToColorResources().get(0).effectFromMarket(dashboard);
            }
            else if(dashboard.getWhiteToColorResources().size()==2){
                //TODO: understand the working of the interactions with the user for this case
            }
        }

    }

    public void notNewAnymore(){}

    public boolean getIsNew(){
        return true;
    }
}