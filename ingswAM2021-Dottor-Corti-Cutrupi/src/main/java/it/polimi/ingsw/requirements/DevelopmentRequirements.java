package it.polimi.ingsw.requirements;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.developmentcard.Color;
import it.polimi.ingsw.developmentcard.DevelopmentCardZone;
import it.polimi.ingsw.requirements.Requirements;
import org.javatuples.Pair;

/*
this class represents the requirements of leader cards that require active development cards in the dashboard
 */

public class DevelopmentRequirements implements Requirements {
    private Integer amountOfDevelopmentRequired;
    private Integer levelRequired;
    private Color colorRequired;

    @Override
    public String toString() {
        return "DevelopmentRequirements{" +
                "amountOfDevelopmentRequired=" + amountOfDevelopmentRequired +
                ", levelRequired=" + levelRequired +
                ", colorRequired=" + colorRequired +
                '}';
    }

    public DevelopmentRequirements(Integer amountOfDevelopmentRequired, Integer levelRequired, Color colorRequired) {
        this.amountOfDevelopmentRequired = amountOfDevelopmentRequired;
        this.levelRequired = levelRequired;
        this.colorRequired = colorRequired;
    }

    public Integer getLevelRequired() {
        return levelRequired;
    }

    public Color getColorRequired() {
        return colorRequired;
    }

    public Integer getAmountOfDevelopmentRequired() {
        return amountOfDevelopmentRequired;
    }

    public boolean checkRequirement(Dashboard dashboard){
        //IN THIS WAY IT DOESNT WORK, IT STILL HAS TO BE IMPLEMENTED
        /*
        for(DevelopmentCardZone developmentCardZone: dashboard.getDevelopmentCardZones()){
            for(Pair<Integer,Color> cardStats: developmentCardZone.getCardsStats()){
                //not 100% sure if "==" is allowed for pairs. This has to be tested
                if(developmentRequired==cardStats){
                    return true;
                }
            }
        }*/
        return false;
    }
}
