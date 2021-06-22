package it.polimi.ingsw;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.model.leadercard.LeaderCardForJson;
import it.polimi.ingsw.model.leadercard.LeaderCardModifier;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeaderCardModifierTest {

    /**
     * this test checks if the cards were imported correctly
     * @throws FileNotFoundException
     */
    @Test
    public void test1() throws FileNotFoundException {
        LeaderCardModifier func1 = new LeaderCardModifier();
        func1.importCards();
        func1.printCards();
    }

    /**
     * this test checks if the methods to change the card values work properly
     */
    @Test
    public void test2() throws FileNotFoundException {
        LeaderCardModifier func1 = new LeaderCardModifier();
        func1.importCards();
        System.out.println("card before operations:");
        func1.changeRequirementType(0);
        assertEquals("resources",func1.getListOfCards().get(0).getTypeOfRequirement());
        func1.changeRequirementType(0);
        assertEquals("development",func1.getListOfCards().get(0).getTypeOfRequirement());
        func1.addResourcesRequirementOfCard(0,150,"stone");
        assertEquals(150,func1.getListOfCards().get(0).getAmountOfForResourcesRequirement().get(0));
        func1.changeResourcesRequirementOfCard(0,0,12,"coin");
        assertEquals("coin",func1.getListOfCards().get(0).getResourcesRequired().get(0));
        func1.removeResourcesRequirementFromCard(0,0);
        func1.addResourcesRequirementOfCard(0,50,"stone");
        assertEquals(50,func1.getListOfCards().get(0).getAmountOfForResourcesRequirement().get(0));
        func1.addDevelopmentRequirementOfCard(0,15,15,"Blue");
        assertEquals(15,func1.getListOfCards().get(0).getAmountOfForDevelopmentRequirement().get(2));
        func1.changeDevelopmentRequirementOfCard(0,2,3,3,"Yellow");
        assertEquals(3,func1.getListOfCards().get(0).getLevelsRequired().get(2));
        func1.removeDevelopmentRequirementOfCard(0,1);
        assertEquals(3,func1.getListOfCards().get(0).getLevelsRequired().get(1));
        func1.changeCardVictoryPoints(0,17);
        assertEquals(17,func1.getListOfCards().get(0).getVictoryPoints());
        func1.changeCardSpecialPowerType(10,"discount");
        assertEquals("discount",func1.getListOfCards().get(10).getSpecialPower());
        //func1.changeCardSpecialPowerResource(10,"coin");
        //assertEquals("coin",func1.getListOfCards().get(10).getSpecialPowerResource());
    }

    /**
     * in this test we check if the method writeCardsInJson works properly
     */
    @Test
    public void test3() throws FileNotFoundException {
        LeaderCardModifier func1 = new LeaderCardModifier();
        func1.importCards();
        func1.changeCardVictoryPoints(0,15);
        //func1.writeCardsInJson();
        func1.importCards();
        func1.printCards();
        func1.changeCardVictoryPoints(0,2);
        //func1.writeCardsInJson();
    }

    @Test
    public void test4() throws FileNotFoundException {
        List <Integer> intList = new ArrayList<Integer>();
        intList.add(3);
        intList.add(3);
        intList.add(3);
        List <String> stringList = new ArrayList<String>();
        stringList.add("coin");
        stringList.add("coin");
        stringList.add("coin");
        ArrayList <String> stringArrayList = new ArrayList<String>();
        stringArrayList.add("coin");
        stringArrayList.add("coin");
        stringArrayList.add("coin");
        LeaderCardForJson leaderCardForJson = new LeaderCardForJson("Resources",null,null,null,intList,stringList,4,"extraProd",stringArrayList,false);
        leaderCardForJson.toString();
        Gson listOfCardsGson = new GsonBuilder().setPrettyPrinting().create();
        String listJson = listOfCardsGson.toJson(leaderCardForJson);
        System.out.println(listJson);
    }

}
