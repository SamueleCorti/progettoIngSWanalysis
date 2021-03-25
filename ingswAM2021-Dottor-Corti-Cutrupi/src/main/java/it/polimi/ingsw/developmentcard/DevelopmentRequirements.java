package it.polimi.ingsw.developmentcard;

import org.javatuples.Pair;

import java.util.ArrayList;

public class DevelopmentRequirements implements Requirements{

    private Pair<Integer,Color> developmentRequired;

    public DevelopmentRequirements(Pair<Integer, Color> developmentRequired) {
        this.developmentRequired = developmentRequired;
    }

    public boolean checkRequirements(){
        //da implementare
    return true;
    };
}
