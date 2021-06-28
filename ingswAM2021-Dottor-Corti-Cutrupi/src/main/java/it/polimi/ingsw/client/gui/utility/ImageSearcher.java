package it.polimi.ingsw.client.gui.utility;

import it.polimi.ingsw.server.messages.showingMessages.SerializationConverter;

public class ImageSearcher {
    public String getImageFromColorVictoryPoints(int intColor, int victoryPoints){
        SerializationConverter converter= new SerializationConverter();
        String color= converter.parseIntToColorString(intColor);
        return "/images/cardsFrontJPG/"+color+victoryPoints+".jpg";
    }

    public String getImageFromPowerTypeResource(int powerType, int resource){
        SerializationConverter converter= new SerializationConverter();

        String power= converter.parseIntToSpecialPower(powerType);
        String res;
        if(converter.intToResource(resource)!=null) {
            res = converter.intToResource(resource).getResourceType().toString();
        }else{
            res = "error";
        }

        if(power.equals("error")||res.equals("error")){
            return "error";
        }else{
            return "/images/cardsFrontJPG/"+power+res+".jpg";
        }
    }
}
