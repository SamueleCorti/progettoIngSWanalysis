package it.polimi.ingsw.client.gui.utility;

import it.polimi.ingsw.server.messages.jsonMessages.SerializationConverter;

import java.net.URL;

public class ImageSearcher {
    public String getImageFromColorVictoryPoints(int intColor, int victoryPoints){
        SerializationConverter converter= new SerializationConverter();
        String color= converter.parseIntToColorString(intColor);
        return "/ images /cardsFrontJPG/"+color+victoryPoints+".jpg";
    }

    public String getImageFromPowerTypeResource(int powerType, int resource){
        SerializationConverter converter= new SerializationConverter();
        String power= converter.parseIntToSpecialPower(powerType);
        String res= converter.intToResource(resource).getResourceType().toString();
        return "/images/cardsFrontJPG/"+power+res+".jpg";
    }
}
