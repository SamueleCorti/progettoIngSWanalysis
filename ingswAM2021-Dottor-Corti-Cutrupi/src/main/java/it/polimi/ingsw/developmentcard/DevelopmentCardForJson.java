package it.polimi.ingsw.developmentcard;

import java.util.List;

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

    public DevelopmentCardForJson(List<Integer> amountOfForPrice, List<String> typeOfResourceForPrice, Integer level, String color, List<Integer> amountOfForProdRequirements, List<String> typeOfResourceForProdRequirements, List<Integer> amountOfForProdResults, List<String> typeOfResourceForProdResults, Integer victoryPoints) {
        this.amountOfForPrice = amountOfForPrice;
        this.typeOfResourceForPrice = typeOfResourceForPrice;
        this.level = level;
        this.color = color;
        this.amountOfForProdRequirements = amountOfForProdRequirements;
        this.typeOfResourceForProdRequirements = typeOfResourceForProdRequirements;
        this.amountOfForProdResults = amountOfForProdResults;
        this.typeOfResourceForProdResults = typeOfResourceForProdResults;
        this.victoryPoints = victoryPoints;
    }

    @Override
    public String toString() {
        return "DevelopmentCardForJson{" +
                "amountOfForPrice=" + amountOfForPrice +
                ", typeOfResourceForPrice=" + typeOfResourceForPrice +
                ", level=" + level +
                ", color='" + color + '\'' +
                ", amountOfForProdRequirements=" + amountOfForProdRequirements +
                ", typeOfResourceForProdRequirements=" + typeOfResourceForProdRequirements +
                ", amountOfForProdResults=" + amountOfForProdResults +
                ", typeOfResourceForProdResults=" + typeOfResourceForProdResults +
                ", victoryPoints=" + victoryPoints +
                '}';
    }
}
