package it.polimi.ingsw.gui;

import it.polimi.ingsw.server.messages.jsonMessages.SerializationConverter;

import java.net.URL;

public class Parser {
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
