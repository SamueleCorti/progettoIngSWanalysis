package it.polimi.ingsw;

import it.polimi.ingsw.developmentcard.Color;
import it.polimi.ingsw.requirements.DevelopmentRequirements;
import it.polimi.ingsw.requirements.Requirements;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class WhiteToColorTest {
    DevelopmentRequirements requirement1 = new DevelopmentRequirements(1, Color.Blue);
    DevelopmentRequirements requirement2 = new DevelopmentRequirements(2,Color.Yellow);
    ArrayList<Requirements> requirements= new ArrayList<Requirements>();

    @Test
    public void requirementsTest(){
        requirements.add(requirement1);
        requirements.add(requirement2);

    }
}
