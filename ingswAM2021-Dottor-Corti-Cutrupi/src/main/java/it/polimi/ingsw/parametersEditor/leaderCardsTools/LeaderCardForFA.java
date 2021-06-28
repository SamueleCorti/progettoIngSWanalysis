package it.polimi.ingsw.parametersEditor.leaderCardsTools;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a modified version of the LeaderCardForJson class, with similar attributes
 *  plus the Properties necessary to be shown in GUI's Table Views
 */

public class LeaderCardForFA {

    private String typeOfRequirement;
    private List <Integer> amountOfForDevelopmentRequirement;
    private List <Integer> levelsRequired;
    private List <String> colorsRequired;
    private List <Integer> amountOfForResourcesRequirement;
    private List <String> resourcesRequired;
    private Integer victoryPoints;
    private String specialPower;
    private ArrayList <String> specialPowerResources;
    private boolean wasCardModified;

    //index used to find the card from table
    private transient int cardIndex;

    //properties of the card to be shown in tables
    private transient StringProperty typeOfRequirementProperty = new SimpleStringProperty();
    private transient StringProperty resourcesOrDevelopmentRequired = new SimpleStringProperty();
    private transient StringProperty typeOfSpecialPowerProperty = new SimpleStringProperty();
    private transient StringProperty specialPowerResourcesProperty = new SimpleStringProperty();


    public LeaderCardForFA(String typeOfRequirement, List<Integer> amountOfForDevelopmentRequirement, List<Integer> levelsRequired, List<String> colorsRequired, List<Integer> amountOfForResourcesRequirement, List<String> resourcesRequired, int victoryPoints, String specialPower, ArrayList <String> specialPowerResources, boolean wasCardModified) {
        this.typeOfRequirement = typeOfRequirement;
        this.amountOfForDevelopmentRequirement = amountOfForDevelopmentRequirement;
        this.levelsRequired = levelsRequired;
        this.colorsRequired = colorsRequired;
        this.amountOfForResourcesRequirement = amountOfForResourcesRequirement;
        this.resourcesRequired = resourcesRequired;
        this.victoryPoints = victoryPoints;
        this.specialPower = specialPower;
        this.specialPowerResources = new ArrayList<String>();
        for(String string: specialPowerResources) {
            this.specialPowerResources.add(string);
        }
        this.wasCardModified = wasCardModified;
    }

    /**
     * This method is to set the properties for table views
     * @param index
     */
    public void initializePropertiesForTableView(Integer index){
        this.typeOfRequirementProperty = new SimpleStringProperty();
        this.resourcesOrDevelopmentRequired = new SimpleStringProperty();
        this.typeOfSpecialPowerProperty = new SimpleStringProperty();
        this.specialPowerResourcesProperty = new SimpleStringProperty();

        //part where we set the card values as properties to be shown in tables
        this.typeOfRequirementProperty.setValue(typeOfRequirement);
        String developmentOrResourcesRequired = new String("");
        int i = 0;
        if(typeOfRequirement.equals("resources")){
            for (Integer numRequired :amountOfForResourcesRequirement){
                developmentOrResourcesRequired+=numRequired+" "+resourcesRequired.get(i)+"\t";
                i++;
            }
        }else{
            for(Integer numRequired: amountOfForDevelopmentRequirement){
                if(i!=0){
                    developmentOrResourcesRequired+=", ";
                }
                developmentOrResourcesRequired+=numRequired+" "+colorsRequired.get(i)+" cards level " + levelsRequired.get(i);
                i++;
            }
        }
        this.resourcesOrDevelopmentRequired.set(developmentOrResourcesRequired);
        this.typeOfSpecialPowerProperty.set(specialPower);
        String specialPowerResourcesString = new String();
        for(String tempString: specialPowerResources) {
            specialPowerResourcesString += tempString;
        }
        this.specialPowerResourcesProperty.set(specialPowerResourcesString);

        this.cardIndex = index;
    }


    @Override
    public String toString() {
        return "LeaderCardForJson{" +
                "typeOfRequirement='" + typeOfRequirement + '\'' +
                ", amountOfForDevelopmentRequirement=" + amountOfForDevelopmentRequirement +
                ", colorsRequired=" + colorsRequired +
                ", amountOfForResourcesRequirement=" + amountOfForResourcesRequirement +
                ", resourcesRequired=" + resourcesRequired +
                ", VictoryPoints=" + victoryPoints +
                ", specialPower='" + specialPower + '\'' +
                ", specialPowerResources='" + specialPowerResources + '\'' +
                ", wasCardModified= " + wasCardModified + '\'' +
                '}';
    }

    public void setTypeOfRequirement(String typeOfRequirement) {
        this.typeOfRequirement = typeOfRequirement;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public void setSpecialPower(String specialPower) {
        this.specialPower = specialPower;
    }

    public void setSpecialPowerResource(ArrayList <String> specialPowerResources) {
        this.specialPowerResources = specialPowerResources;
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
        return victoryPoints;
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

    public StringProperty getTypeOfRequirementProperty(){
        return typeOfRequirementProperty;
    }

    public StringProperty getResourcesOrDevelopmentRequired(){
        return resourcesOrDevelopmentRequired;
    }

    public StringProperty getTypeOfSpecialPowerProperty(){
        return typeOfSpecialPowerProperty;
    }

    public StringProperty getSpecialPowerResourcesProperty(){
        return specialPowerResourcesProperty;
    }

    public int getCardIndex() {
        return cardIndex;
    }

}
