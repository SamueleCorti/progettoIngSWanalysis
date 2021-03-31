package it.polimi.ingsw.requirements;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.developmentcard.Color;
import it.polimi.ingsw.developmentcard.DevelopmentCardZone;
import it.polimi.ingsw.requirements.Requirements;
import org.javatuples.Pair;

public class DevelopmentRequirements implements Requirements {
    //to fix, case where two cards of the same color are needed
    private Pair<Integer, Color> developmentRequired;

    public DevelopmentRequirements(int num, Color color) {
        this.developmentRequired = new Pair<Integer,Color>(num,color);
    }

    public Pair<Integer, Color> getDevelopmentRequired() {
        return developmentRequired;
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
    }
}
