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
        /*
        this part still has to be implemented
         */
    return true;
    };
}
