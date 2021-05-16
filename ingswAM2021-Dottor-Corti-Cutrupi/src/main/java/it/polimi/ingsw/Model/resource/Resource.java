package it.polimi.ingsw.Model.resource;

import it.polimi.ingsw.Model.boardsAndPlayer.Dashboard;

public interface Resource {

    public ResourceType getResourceType();
    public void effectFromMarket(Dashboard dashboard);
    public void effectFromProduction(Dashboard dashboard);
    public void notNewAnymore();
    public boolean getIsNew();
}