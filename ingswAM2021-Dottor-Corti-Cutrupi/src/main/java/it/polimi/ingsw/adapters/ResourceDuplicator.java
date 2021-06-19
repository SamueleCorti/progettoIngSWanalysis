package it.polimi.ingsw.adapters;

import it.polimi.ingsw.model.resource.*;

/**
 * Used to convert a resource to its resource type or a resource type to a new resource
 */
public class ResourceDuplicator {

    /**
     * @return the resource type of the given resource
     */
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

    /**
     * @return a new resource having the resource type given
     */
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
