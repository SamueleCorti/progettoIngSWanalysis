package it.polimi.ingsw.model.developmentcard;

import java.util.List;

/**
 * since the development card class was too complicate to import directly from json, we created this
 *  class that has all the attributes needed to create a development card, but is easier to deserialize
 */

public class DevelopmentCardForJson {
    private List<Integer> amountOfForPrice;
    private List <String> typeOfResourceForPrice;

    private Integer level;
    private String color;

    private List <Integer> amountOfForProdRequirements;
    private List <String> typeOfResourceForProdRequirements;

    private List <Integer> amountOfForProdResults;
    private List <String> typeOfResourceForProdResults;

    private Integer victoryPoints;

    private boolean wasCardModified;

    public DevelopmentCardForJson(List<Integer> amountOfForPrice, List<String> typeOfResourceForPrice, Integer level, String color, List<Integer> amountOfForProdRequirements, List<String> typeOfResourceForProdRequirements, List<Integer> amountOfForProdResults, List<String> typeOfResourceForProdResults, Integer victoryPoints,boolean wasCardModified) {
        this.amountOfForPrice = amountOfForPrice;
        this.typeOfResourceForPrice = typeOfResourceForPrice;
        this.level = level;
        this.color = color;
        this.amountOfForProdRequirements = amountOfForProdRequirements;
        this.typeOfResourceForProdRequirements = typeOfResourceForProdRequirements;
        this.amountOfForProdResults = amountOfForProdResults;
        this.typeOfResourceForProdResults = typeOfResourceForProdResults;
        this.victoryPoints = victoryPoints;
        this.wasCardModified = wasCardModified;
    }



    public List<Integer> getAmountOfForPrice() {
        return amountOfForPrice;
    }

    public List<String> getTypeOfResourceForPrice() {
        return typeOfResourceForPrice;
    }

    public Integer getLevel() {
        return level;
    }

    public String getColor() {
        return color;
    }

    public List<Integer> getAmountOfForProdRequirements() {
        return amountOfForProdRequirements;
    }

    public List<String> getTypeOfResourceForProdRequirements() {
        return typeOfResourceForProdRequirements;
    }

    public List<Integer> getAmountOfForProdResults() {
        return amountOfForProdResults;
    }

    public List<String> getTypeOfResourceForProdResults() {
        return typeOfResourceForProdResults;
    }

    public Integer getVictoryPoints() {
        return victoryPoints;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setVictoryPoints(Integer victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public boolean isWasCardModified() {
        return wasCardModified;
    }
}
