package it.polimi.ingsw.server.messages.jsonMessages;

import it.polimi.ingsw.model.resource.*;

public class ResourceToIntConverter {
    public int converter(ResourceType resourceType){
        if(resourceType.equals(ResourceType.Coin)){
            return 0;
        }else if(resourceType.equals(ResourceType.Stone)){
            return 1;
        }else if(resourceType.equals(ResourceType.Servant)){
            return 2;
        }else if(resourceType.equals(ResourceType.Shield)){
            return 3;
        }else if(resourceType.equals(ResourceType.Faith)){
            return 4;
        }else if(resourceType.equals(ResourceType.Blank)){
            return 5;
        }
        else{
            System.out.println("There was an error in parsing the resource!");
            return 150;
        }
    }

    public int converter(Resource resource){
        return converter((resource.getResourceType()));
    }

    public String parseIntArrayToStringOfResources(int[] resources){
        String string = new String();
        if(resources[0]!=0) {
            string += resources[0];
            string += " coins, \t";
        }
        if(resources[1]!=0) {
            string += resources[1];
            string += " stones, \t";
        }
        if(resources[2]!=0) {
            string += resources[2];
            string += " servants, \t";
        }
        if(resources[3]!=0) {
            string += resources[3];
            string += " shields, \t";
        }
        if(resources[4]!=0) {
            string += resources[4];
            string += " faith, \t";
        }
        return string;
    }

    public Resource intToResource(int i){
        switch (i){
            case 0:
                return new CoinResource();
            case 1:
                return new StoneResource();
            case 2:
                return new ServantResource();
            case 3:
                return new ShieldResource();
            case 4:
                return new FaithResource();
            case 5:
                return new BlankResource();
            default:
                return null;
        }
    }

    public String parseIntToColorString(int i){
        if(i==0){
            return "blue";
        }else if(i==1){
            return "green";
        }else if(i==2){
            return "yellow";
        }else if(i==3){
            return "purple";
        }else {
            return "error";
        }
    }
}
