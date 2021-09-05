package it.polimi.ingsw.parametersEditor.controllers;

import it.polimi.ingsw.parametersEditor.GUIFA;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import it.polimi.ingsw.parametersEditor.leaderCardsTools.LeaderCardForFA;
import it.polimi.ingsw.parametersEditor.leaderCardsTools.LeaderCardModifier;

public class LeaderCardsPageController implements GUIControllerFA {
    private LeaderCardModifier leaderCardModifier;
    private GUIFA gui;
    private LeaderCardForFA selectedCard;

    @FXML private Button plus1;
    @FXML private Button minus1;
    @FXML private Button changeVictoryPointsButton;
    @FXML private Button changeCardSpecialPower;
    @FXML private Button changeSpecialPowerResourcesButton;
    @FXML private ChoiceBox<String> specialPowerChoiceBox;
    @FXML private Label victoryPointsLabel;
    @FXML private Label victoryPointsText;
    @FXML private Label specialPowerText;
    @FXML private Button confirmChoiceButton;

    @FXML private Button goBackButton;
    @FXML private Button changeCardRequirementType;
    @FXML private Button changeCardRequirementsButton;
    @FXML private TableView tableView;
    @FXML private TableColumn<LeaderCardForFA,String> typeOfRequirement;
    @FXML private TableColumn<LeaderCardForFA,String> requirementsColumn;
    @FXML private TableColumn<LeaderCardForFA,String> typeOfSPecialPower;
    @FXML private TableColumn<LeaderCardForFA,String> specialPowerResources;
    @FXML private TableColumn<LeaderCardForFA,Number> victoryPoints;

    public void goBackAndSaveChanges(MouseEvent mouseEvent) {
        leaderCardModifier.writeCardsInJson();
        gui.changeStage("mainMenuPage.fxml");
    }

    @Override
    public void setGui(GUIFA gui) {
        this.gui=gui;
    }

    /**
     * this method initializes the leaderCardsPage elements, and imports the cards into its leaderCardModifier class
     */
    public void initialize(){
        changeCardRequirementType.setDisable(true);changeCardSpecialPower.setDisable(true);changeVictoryPointsButton.setDisable(true);
        changeCardRequirementsButton.setDisable(true);changeSpecialPowerResourcesButton.setDisable(true);

        specialPowerChoiceBox.getItems().add("extradepot");
        specialPowerChoiceBox.getItems().add("discount");
        specialPowerChoiceBox.getItems().add("extraprod");
        specialPowerChoiceBox.getItems().add("whitetocolor");

        specialPowerChoiceBox.setOpacity(0);
        victoryPointsLabel.setOpacity(0);
        victoryPointsText.setOpacity(0);
        specialPowerText.setOpacity(0);
        confirmChoiceButton.setOpacity(0);
        plus1.setOpacity(0);
        minus1.setOpacity(0);
        plus1.setDisable(true);
        minus1.setDisable(true);
        confirmChoiceButton.setDisable(true);

        typeOfRequirement.setCellValueFactory(data -> data.getValue().getTypeOfRequirementProperty());
        requirementsColumn.setCellValueFactory(data -> data.getValue().getResourcesOrDevelopmentRequired());
        typeOfSPecialPower.setCellValueFactory(data -> data.getValue().getTypeOfSpecialPowerProperty());
        specialPowerResources.setCellValueFactory(data -> data.getValue().getSpecialPowerResourcesProperty());
        victoryPoints.setCellValueFactory(new PropertyValueFactory<>("victoryPoints"));

        leaderCardModifier = new LeaderCardModifier();
        leaderCardModifier.importCards();
        for (LeaderCardForFA cardToAdd: leaderCardModifier.getListOfCards()){
            tableView.getItems().add(cardToAdd);
        }
    }

    /**
     * this method updates the values of the cards in the TableView
     */
    public void refreshCards(){
        this.tableView.getItems().clear();
        leaderCardModifier.updateProperties();
        for (LeaderCardForFA cardToAdd: leaderCardModifier.getListOfCards()){
            tableView.getItems().add(cardToAdd);
        }
    }

    /**
     * method launched when the user selects a card
     * @param mouseEvent
     */
    public void userClickedOnTable(MouseEvent mouseEvent) {
        if(tableView.getItems().size()>0) {
           changeCardRequirementType.setDisable(false);changeSpecialPowerResourcesButton.setDisable(false);
           changeCardRequirementsButton.setDisable(false);changeVictoryPointsButton.setDisable(false);
           changeCardSpecialPower.setDisable(false);
           this.selectedCard = (LeaderCardForFA) tableView.getSelectionModel().getSelectedItem();
           specialPowerChoiceBox.setValue(selectedCard.getSpecialPower());
        }
    }

    public void changeCardRequirementType(MouseEvent mouseEvent) {
        leaderCardModifier.changeRequirementType(selectedCard.getCardIndex());
        refreshCards();
    }

    /**
     * Method used to change the card requirements; if the card requires development cards, we go to the development requirements page;
     * if the cards has resources requirements,  we go to the resources page with mode = 1, passing the amount of resources
     * required to activate the card
     */
    public void goToChangeRequirements(MouseEvent mouseEvent) {
        if(selectedCard.getTypeOfRequirement().equals("development")) {
            DevelopmentRequirementPageController developmentRequirementPageController = (DevelopmentRequirementPageController) gui.getDevelopmentRequirementPageController();
            developmentRequirementPageController.setModifierAndCard(this.leaderCardModifier,this.selectedCard);
            gui.changeStage("developmentRequirementsPage.fxml");
        }else{
            int i = 0;
            int numCoins=0,numStones=0,numShields=0,numServants=0;
            for (Integer numRequired : selectedCard.getAmountOfForResourcesRequirement()){
                if (selectedCard.getResourcesRequired().get(i).equals("coin")){
                    numCoins += numRequired;
                }else if (selectedCard.getResourcesRequired().get(i).equals("stone")){
                    numStones += numRequired;
                }else if (selectedCard.getResourcesRequired().get(i).equals("servant")){
                    numShields += numRequired;
                }else if (selectedCard.getResourcesRequired().get(i).equals("shield")){
                    numServants += numRequired;
                }
                i++;
            }
            ResourcesPageController resourcesPageController = (ResourcesPageController) gui.getResourcesPageController();
            resourcesPageController.setAmountsAndMode(numCoins,numStones,numShields,numServants,1);
            gui.changeStage("resourcesPage.fxml");
        }
        disableAllButtons();
    }

    public void changeCardVictoryPoints(MouseEvent mouseEvent) {
        plus1.setOpacity(1);
        minus1.setOpacity(1);
        plus1.setDisable(false);
        minus1.setDisable(false);
        confirmChoiceButton.setDisable(false);
        victoryPointsText.setOpacity(1);
        victoryPointsLabel.setOpacity(1);
        confirmChoiceButton.setOpacity(1);
        victoryPointsLabel.setText(""+selectedCard.getVictoryPoints());
    }


    public void minusVictoryPoints(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(victoryPointsLabel.getText());
        temp -= 1;
        if(temp>=0) {
            victoryPointsLabel.setText("" + temp);
        }
    }

    public void plusVictoryPoints(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(victoryPointsLabel.getText());
        temp += 1;
        victoryPointsLabel.setText(""+temp);
    }

    /**
     * this button confirms the choice of victory points/special power, and sets the new inserted value
     * @param mouseEvent
     */
    public void confirmChoice(MouseEvent mouseEvent) {
        leaderCardModifier.changeCardVictoryPoints(selectedCard.getCardIndex(),Integer.parseInt(victoryPointsLabel.getText()));
        leaderCardModifier.changeCardSpecialPowerType(selectedCard.getCardIndex(), specialPowerChoiceBox.getValue());
        disableAllButtons();
        refreshCards();
    }

    /**
     * method that disables all appearing/disapperaing buttons
     */
    public void disableAllButtons(){
        victoryPointsLabel.setOpacity(0);victoryPointsText.setOpacity(0);specialPowerText.setOpacity(0);
        specialPowerChoiceBox.setOpacity(0);confirmChoiceButton.setOpacity(0);
        plus1.setOpacity(0);minus1.setOpacity(0);plus1.setDisable(true); minus1.setDisable(true);
        confirmChoiceButton.setDisable(true); specialPowerChoiceBox.setDisable(true);changeCardSpecialPower.setDisable(true);
        changeCardRequirementsButton.setDisable(true);changeCardRequirementType.setDisable(true);changeSpecialPowerResourcesButton.setDisable(true);
        changeVictoryPointsButton.setDisable(true);
        tableView.getSelectionModel().clearSelection();
    }

    public void changeSpecialPower(MouseEvent mouseEvent) {
        specialPowerText.setOpacity(1);
        confirmChoiceButton.setOpacity(1);
        confirmChoiceButton.setDisable(false);specialPowerChoiceBox.setDisable(false);
        specialPowerChoiceBox.setOpacity(1);
        victoryPointsLabel.setText(""+selectedCard.getVictoryPoints());
    }

    /**
     * This method sets the card resources requirements to a certain number of resources
     * @param coins
     * @param stones
     * @param servants
     * @param shields
     */
    public void setResourcesForRequirement(int coins, int stones, int servants, int shields) {
        leaderCardModifier.clearResourceRequirements(selectedCard.getCardIndex());
        if(coins!=0) {
            leaderCardModifier.addResourcesRequirementOfCard(selectedCard.getCardIndex(), coins, "coin");
        }
        if(stones!=0) {
            leaderCardModifier.addResourcesRequirementOfCard(selectedCard.getCardIndex(), stones, "stone");
        }
        if(servants!=0) {
            leaderCardModifier.addResourcesRequirementOfCard(selectedCard.getCardIndex(), servants, "servant");
        }
        if(shields!=0) {
            leaderCardModifier.addResourcesRequirementOfCard(selectedCard.getCardIndex(), shields, "shield");
        }
    }

    /**
     * This method sets the card special power resources to a certain number of resources
     * @param coins
     * @param stones
     * @param servants
     * @param shields
     */
    public void setResourcesForSpecialPower(int coins, int stones, int servants, int shields){
        leaderCardModifier.clearSpecialPowerResources(selectedCard.getCardIndex());
        if(coins!=0) {
            leaderCardModifier.addSpecialPowerResources(selectedCard.getCardIndex(),coins,"coin");
        }else if(stones!=0) {
            leaderCardModifier.addSpecialPowerResources(selectedCard.getCardIndex(), stones, "stone");
        }else if(servants!=0) {
            leaderCardModifier.addSpecialPowerResources(selectedCard.getCardIndex(), servants, "servant");
        }else if(shields!=0) {
            leaderCardModifier.addSpecialPowerResources(selectedCard.getCardIndex(), shields, "shield");
        }
    }

    /**
     * this method moves you to the resources page with mode = 2, passing the amount of special power resources of the selected card
     * @param mouseEvent
     */
    public void changeSpecialPowerResources(MouseEvent mouseEvent) {
        int numCoins=0,numStones=0,numShields=0,numServants=0;
        for (String resource : selectedCard.getSpecialPowerResources()){
            if (resource.equals("coin")){
                numCoins += 1;
            }else if (resource.equals("stone")){
                numStones += 1;
            }else if (resource.equals("servant")){
                numShields += 1;
            }else if (resource.equals("shield")){
                numServants += 1;
            }
        }
        ResourcesPageController resourcesPageController = (ResourcesPageController) gui.getResourcesPageController();
        resourcesPageController.setAmountsAndMode(numCoins,numStones,numShields,numServants,2);
        gui.changeStage("resourcesPage.fxml");
        disableAllButtons();
    }
}
