package it.polimi.ingsw.developmentcard;

import it.polimi.ingsw.Dashboard;
import org.javatuples.Pair;

import java.util.ArrayList;

public class DevelopmentRequirements implements Requirements{

    private Pair<Integer,Color> developmentRequired;

    public DevelopmentRequirements(Pair<Integer, Color> developmentRequired) {
        this.developmentRequired = developmentRequired;
    }

    public boolean checkRequirement(Dashboard dashboard){
        for(DevelopmentCardZone developmentCardZone: dashboard.getDevelopmentCardZones()){
            for(Pair<Integer,Color> cardStats: developmentCardZone.getCardsStats()){
                //not 100% sure if "==" is allowed for pairs. This has to be tested
                if(developmentRequired==cardStats){
                    return true;
                }
            }
        }
    return false;
    };
}
