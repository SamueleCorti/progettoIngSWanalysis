package it.polimi.ingsw.model.resource;

import it.polimi.ingsw.exception.PapalCardActivatedException;
import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;

public interface Resource {

    public ResourceType getResourceType();
    public void effectFromMarket(Dashboard dashboard) throws PapalCardActivatedException;
    public void effectFromProduction(Dashboard dashboard) throws PapalCardActivatedException;
    public void notNewAnymore();
    public boolean getIsNew();
}