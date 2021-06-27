package it.polimi.ingsw.parametersEditor.devCardsTools;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

/**
 * since the development card class was too complicate to import directly from json, we created this
 *  class that has all the attributes needed to create a development card, but is easier to deserialize
 */

public class DevelopmentCardForFA {
    private List<Integer> amountOfForPrice;
    private List <String> typeOfResourceForPrice;

    private Integer level;
    private String color;

    private List <Integer> amountOfForProdRequirements;
    private List <String> typeOfResourceForProdRequirements;

    private List <Integer> amountOfForProdResults;
    private List <String> typeOfResourceForProdResults;


    private transient StringProperty priceStringProperty = new SimpleStringProperty();
    private transient StringProperty colorProperty = new SimpleStringProperty();
    private transient StringProperty requirementsToProduceProperty = new SimpleStringProperty();
    private transient StringProperty producedResourcesProperty = new SimpleStringProperty();

    private Integer victoryPoints;
    private transient Integer cardIndex;

    private boolean wasCardModified;

    public DevelopmentCardForFA(List<Integer> amountOfForPrice, List<String> typeOfResourceForPrice, Integer level, String color, List<Integer> amountOfForProdRequirements, List<String> typeOfResourceForProdRequirements, List<Integer> amountOfForProdResults, List<String> typeOfResourceForProdResults, Integer victoryPoints, boolean wasCardModified) {
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

    public void initializePropertiesForTableView(int index) {
        this.priceStringProperty = new SimpleStringProperty();
        this.colorProperty = new SimpleStringProperty();
        this.requirementsToProduceProperty = new SimpleStringProperty();
        this.producedResourcesProperty = new SimpleStringProperty();

        //part where we set the card values as properties to be shown in tables
        this.colorProperty.setValue(color);
        String price = new String("");
        int i = 0;
        for (Integer numRequired :amountOfForPrice){
            price+=numRequired+" "+typeOfResourceForPrice.get(i)+"\t";
            i++;
        }
        this.priceStringProperty.set(price);
        String requirements = new String("");
        i = 0;
        for (Integer numRequired :amountOfForProdRequirements){
            requirements+=numRequired+" "+typeOfResourceForProdRequirements.get(i)+"\t";
            i++;
        }
        this.requirementsToProduceProperty.set(requirements);
        String produced = new String("");
        i = 0;
        for (Integer numRequired :amountOfForProdResults){
            produced+=numRequired+" "+typeOfResourceForProdResults.get(i)+"\t";
            i++;
        }
        this.producedResourcesProperty.set(produced);
        this.cardIndex = index;
    }

    public List<Integer> getAmountOfForPrice() {
        return amountOfForPrice;
    }

    public List<String> getTypeOfResourceForPrice() {
        return typeOfResourceForPrice;
    }

    public Integer getCardIndex() {
        return cardIndex;
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

    public String getPriceStringProperty() {
        return priceStringProperty.get();
    }

    public StringProperty priceStringPropertyProperty() {
        return priceStringProperty;
    }

    public void setPriceStringProperty(String priceStringProperty) {
        this.priceStringProperty.set(priceStringProperty);
    }

    public String getColorProperty() {
        return colorProperty.get();
    }

    public StringProperty colorPropertyProperty() {
        return colorProperty;
    }

    public void setColorProperty(String colorProperty) {
        this.colorProperty.set(colorProperty);
    }

    public String getRequirementsToProduceProperty() {
        return requirementsToProduceProperty.get();
    }

    public StringProperty requirementsToProducePropertyProperty() {
        return requirementsToProduceProperty;
    }

    public void setRequirementsToProduceProperty(String requirementsToProduceProperty) {
        this.requirementsToProduceProperty.set(requirementsToProduceProperty);
    }

    public String getProducedResourcesProperty() {
        return producedResourcesProperty.get();
    }

    public StringProperty producedResourcesPropertyProperty() {
        return producedResourcesProperty;
    }

    public void setProducedResourcesProperty(String producedResourcesProperty) {
        this.producedResourcesProperty.set(producedResourcesProperty);
    }

    public void setVictoryPoints(Integer victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public boolean isWasCardModified() {
        return wasCardModified;
    }

}
