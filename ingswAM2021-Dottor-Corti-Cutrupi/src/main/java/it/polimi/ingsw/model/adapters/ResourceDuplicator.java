package it.polimi.ingsw.model.adapters;

import it.polimi.ingsw.model.resource.*;

public class ResourceDuplicator {
    public Resource copyResource(Resource resource){
        switch (resource.getResourceType()){
            case Stone: return new StoneResource();
            case Shield: return new ShieldResource();
            case Coin: return new CoinResource();
            case Servant: return new ServantResource();
            case Faith: return new FaithResource();
            default: return new BlankResource();
        }
    }

    public Resource copyResource(ResourceType resource){
        switch (resource){
            case Stone: return new StoneResource();
            case Shield: return new ShieldResource();
            case Coin: return new CoinResource();
            case Servant: return new ServantResource();
            case Faith: return new FaithResource();
            default: return new BlankResource();
        }
    }
}
