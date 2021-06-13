package it.polimi.ingsw.model.leadercard;

import java.util.ArrayList;
import java.util.List;

/**
 * since the leader card class was too complicate to import directly from json, we created this
 * class that has all the attributes needed to create a development card, but is easier to deserialize
 */

public class LeaderCardForJson {

    private String typeOfRequirement;
    private List <Integer> amountOfForDevelopmentRequirement;
    private List <Integer> levelsRequired;
    private List <String> colorsRequired;
    private List <Integer> amountOfForResourcesRequirement;
    private List <String> resourcesRequired;
    private int VictoryPoints;
    private String specialPower;
    private ArrayList <String> specialPowerResources;
    private boolean wasCardModified;

    public LeaderCardForJson(String typeOfRequirement, List<Integer> amountOfForDevelopmentRequirement, List<Integer> levelsRequired, List<String> colorsRequired, List<Integer> amountOfForResourcesRequirement, List<String> resourcesRequired, int victoryPoints, String specialPower,ArrayList <String> specialPowerResources,boolean wasCardModified) {
        this.typeOfRequirement = typeOfRequirement;
        this.amountOfForDevelopmentRequirement = amountOfForDevelopmentRequirement;
        this.levelsRequired = levelsRequired;
        this.colorsRequired = colorsRequired;
        this.amountOfForResourcesRequirement = amountOfForResourcesRequirement;
        this.resourcesRequired = resourcesRequired;
        VictoryPoints = victoryPoints;
        this.specialPower = specialPower;
        this.specialPowerResources = new ArrayList<String>();
        for(String string: specialPowerResources) {
            this.specialPowerResources.add(string);
        }
        this.wasCardModified = wasCardModified;
    }

    @Override
    public String toString() {
        return "LeaderCardForJson{" +
                "typeOfRequirement='" + typeOfRequirement + '\'' +
                ", amountOfForDevelopmentRequirement=" + amountOfForDevelopmentRequirement +
                ", colorsRequired=" + colorsRequired +
                ", amountOfForResourcesRequirement=" + amountOfForResourcesRequirement +
                ", resourcesRequired=" + resourcesRequired +
                ", VictoryPoints=" + VictoryPoints +
                ", specialPower='" + specialPower + '\'' +
                ", specialPowerResources='" + specialPowerResources + '\'' +
                ", wasCardModified= " + wasCardModified + '\'' +
                '}';
    }

    public void setTypeOfRequirement(String typeOfRequirement) {
        this.typeOfRequirement = typeOfRequirement;
    }

    public void setVictoryPoints(int victoryPoints) {
        VictoryPoints = victoryPoints;
    }

    public void setSpecialPower(String specialPower) {
        this.specialPower = specialPower;
    }

    public String getTypeOfRequirement() {
        return typeOfRequirement;
    }

    public List<Integer> getAmountOfForDevelopmentRequirement() {
        return amountOfForDevelopmentRequirement;
    }

    public List<Integer> getLevelsRequired() {
        return levelsRequired;
    }

    public List<String> getColorsRequired() {
        return colorsRequired;
    }

    public List<Integer> getAmountOfForResourcesRequirement() {
        return amountOfForResourcesRequirement;
    }

    public List<String> getResourcesRequired() {
        return resourcesRequired;
    }

    public int getVictoryPoints() {
        return VictoryPoints;
    }

    public String getSpecialPower() {
        return specialPower;
    }

    public List <String> getSpecialPowerResources() {
        return specialPowerResources;
    }

    public boolean isWasCardModified() {
        return wasCardModified;
    }

    public void setWasCardModified(boolean wasCardModified) {
        this.wasCardModified = wasCardModified;
    }
}
