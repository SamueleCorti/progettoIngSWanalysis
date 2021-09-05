package it.polimi.ingsw.parametersEditor.controllers;

import it.polimi.ingsw.parametersEditor.GUIFA;
import it.polimi.ingsw.parametersEditor.devCardsTools.DevelopmentCardForFA;
import it.polimi.ingsw.parametersEditor.devCardsTools.DevelopmentCardModifier;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;

public class DevCardsPageController implements GUIControllerFA {

    private DevelopmentCardModifier devCardModifier;
    private GUIFA gui;
    private DevelopmentCardForFA selectedCard;

    @FXML private Button plus1;
    @FXML private Button changeResourcesProduced;
    @FXML private Button minus1;
    @FXML private ChoiceBox colorChoiceBox;
    @FXML private Label victoryPointsLabel;
    @FXML private Label victoryPointsText;
    @FXML private Label specialPowerText;
    @FXML private Label levelText;
    @FXML private Button confirmChoiceButton;
    @FXML private Button changeVictoryPointsButton;
    @FXML private Button changeCardPrice;
    @FXML private Button changeRequirementsToProduce;
    @FXML private Button minusLevel;
    @FXML private Label levelLabel;
    @FXML private Button plusLevel;

    @FXML private Button goBackButton;
    @FXML private Button changeCardColor;
    @FXML private Button changeCardLevel;
    @FXML private TableView tableView;
    @FXML private TableColumn<DevelopmentCardForFA,String> color;
    @FXML private TableColumn<DevelopmentCardForFA,Number> level;
    @FXML private TableColumn<DevelopmentCardForFA,String> requirementsToBuyColumn;
    @FXML private TableColumn<DevelopmentCardForFA,String> requirementsToProduce;
    @FXML private TableColumn<DevelopmentCardForFA,String> producedResources;
    @FXML private TableColumn<DevelopmentCardForFA,Number> victoryPoints;

    public void goBackAndSaveChanges(MouseEvent mouseEvent) {
        devCardModifier.writeCardsInJson();
        gui.changeStage("mainMenuPage.fxml");
    }

    @Override
    public void setGui(GUIFA gui) {
        this.gui=gui;
    }

    /**
     * this method initializes the devCardsPage elements, and imports the cards into its devCardModifier class
     */
    public void initialize(){
        changeCardColor.setDisable(true);
        changeCardLevel.setDisable(true);

        colorChoiceBox.setOpacity(0);colorChoiceBox.setDisable(true);

        colorChoiceBox.getItems().add("blue");
        colorChoiceBox.getItems().add("green");
        colorChoiceBox.getItems().add("yellow");
        colorChoiceBox.getItems().add("purple");

        victoryPointsLabel.setOpacity(0);victoryPointsText.setOpacity(0);specialPowerText.setOpacity(0);
        confirmChoiceButton.setOpacity(0);confirmChoiceButton.setDisable(true);levelLabel.setOpacity(0);levelText.setOpacity(0);
        plus1.setOpacity(0);plus1.setDisable(true);plusLevel.setOpacity(0);plusLevel.setDisable(true);levelLabel.setOpacity(0);
        minus1.setOpacity(0);minus1.setDisable(true);minusLevel.setOpacity(0);minusLevel.setDisable(true);
        changeVictoryPointsButton.setDisable(true);changeResourcesProduced.setDisable(true);
        changeCardPrice.setDisable(true);
        changeRequirementsToProduce.setDisable(true);

        color.setCellValueFactory(data -> data.getValue().colorPropertyProperty());
        requirementsToBuyColumn.setCellValueFactory(data -> data.getValue().priceStringPropertyProperty());
        requirementsToProduce.setCellValueFactory(data -> data.getValue().requirementsToProducePropertyProperty());
        producedResources.setCellValueFactory(data -> data.getValue().producedResourcesPropertyProperty());
        level.setCellValueFactory(new PropertyValueFactory<>("level"));
        victoryPoints.setCellValueFactory(new PropertyValueFactory<>("victoryPoints"));

        devCardModifier = new DevelopmentCardModifier();

        devCardModifier.importCards();

        for (DevelopmentCardForFA cardToAdd: devCardModifier.getListOfCards()){
            tableView.getItems().add(cardToAdd);
        }
    }

    /**
     * this method updates the values of the cards in the TableView
     */
    public void refreshCards(){
        this.tableView.getItems().clear();
        devCardModifier.updateProperties();
        for (DevelopmentCardForFA cardToAdd: devCardModifier.getListOfCards()){
            tableView.getItems().add(cardToAdd);
        }
    }

    /**
     * method launched when the user selects a card
     * @param mouseEvent
     */
    public void userClickedOnTable(MouseEvent mouseEvent) {
        if(tableView.getItems().size()>0) {
            changeCardColor.setDisable(false);changeResourcesProduced.setDisable(false);
            changeCardPrice.setDisable(false);changeVictoryPointsButton.setDisable(false);
            changeCardLevel.setDisable(false);
            changeRequirementsToProduce.setDisable(false);
            this.selectedCard = (DevelopmentCardForFA) tableView.getSelectionModel().getSelectedItem();
            colorChoiceBox.setValue(selectedCard.getColor());levelLabel.setText(selectedCard.getLevel().toString());
        }
    }

    public void changeCardColor(MouseEvent mouseEvent) {
        colorChoiceBox.setOpacity(1);colorChoiceBox.setDisable(false);
        confirmChoiceButton.setOpacity(1);confirmChoiceButton.setDisable(false);
        refreshCards();
    }

    public void changeCardLevel(MouseEvent mouseEvent) {
        levelLabel.setText(selectedCard.getLevel().toString());levelLabel.setOpacity(1);levelText.setOpacity(1);
        plusLevel.setDisable(false);plusLevel.setOpacity(1);minusLevel.setDisable(false);minusLevel.setOpacity(1);
        confirmChoiceButton.setOpacity(1);confirmChoiceButton.setDisable(false);
    }

    public void minusLevelAction(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(levelLabel.getText());
        temp -= 1;
        if(temp>=1) {
            levelLabel.setText("" + temp);
        }
    }

    public void plusLevelAction(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(levelLabel.getText());
        temp += 1;
        if(temp<=3) {
            levelLabel.setText("" + temp);
        }
    }

    public void changeCardVictoryPoints(MouseEvent mouseEvent) {
        plus1.setOpacity(1); minus1.setOpacity(1);victoryPointsText.setOpacity(1); victoryPointsLabel.setOpacity(1);
        confirmChoiceButton.setOpacity(1);victoryPointsLabel.setText(""+selectedCard.getVictoryPoints());
        plus1.setDisable(false);minus1.setDisable(false);confirmChoiceButton.setDisable(false);
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



    public void confirmChoice(MouseEvent mouseEvent) {
        devCardModifier.changeCardColor(selectedCard.getCardIndex(), (String) colorChoiceBox.getValue());
        devCardModifier.changeCardLevel(selectedCard.getCardIndex(),Integer.parseInt(levelLabel.getText()));
        devCardModifier.changeCardVictoryPoints(selectedCard.getCardIndex(), Integer.parseInt(victoryPointsLabel.getText()));
        disableAllButtons();
        gui.refreshDevCards();
    }

    public void disableAllButtons(){
        victoryPointsLabel.setOpacity(0);victoryPointsText.setOpacity(0);specialPowerText.setOpacity(0);
        confirmChoiceButton.setOpacity(0); minus1.setDisable(true); plus1.setDisable(true);
        plus1.setOpacity(0);minus1.setOpacity(0);colorChoiceBox.setDisable(true);colorChoiceBox.setOpacity(0);
        levelLabel.setOpacity(0); levelText.setOpacity(0);plusLevel.setDisable(true);minusLevel.setDisable(true);
        plusLevel.setOpacity(0);minusLevel.setOpacity(0);
        changeCardColor.setDisable(true);changeResourcesProduced.setDisable(true);
        changeCardPrice.setDisable(true);changeVictoryPointsButton.setDisable(true);
        changeCardLevel.setDisable(true);changeRequirementsToProduce.setDisable(true);
        tableView.getSelectionModel().clearSelection();
    }

    /**
     * used when someone uses the change card price button; we move to the resources page with mode = 3, passing
     * the number of resources required to buy the card (imported from dev card modfier's lsit of cards)
     * * @param mouseEvent
     */
    public void changeCardPriceAction(MouseEvent mouseEvent) {
        int i = 0;
        int numCoins=0,numStones=0,numShields=0,numServants=0;
        for (Integer numRequired : selectedCard.getAmountOfForPrice()){
            if (selectedCard.getTypeOfResourceForPrice().get(i).equals("coin")){
                numCoins += numRequired;
            }else if (selectedCard.getTypeOfResourceForPrice().get(i).equals("stone")){
                numStones += numRequired;
            }else if (selectedCard.getTypeOfResourceForPrice().get(i).equals("servant")){
                numShields += numRequired;
            }else if (selectedCard.getTypeOfResourceForPrice().get(i).equals("shield")){
                numServants += numRequired;
            }
            i++;
        }
        ResourcesPageController resourcesPageController = (ResourcesPageController) gui.getResourcesPageController();
        resourcesPageController.setAmountsAndMode(numCoins,numStones,numShields,numServants,3);
        gui.changeStage("resourcesPage.fxml");
        disableAllButtons();
    }

    /**
     * used when someone uses the change card requirements button; we move to the resources page with mode = 4, passing
     * the number of resources required to activate the production (imported from dev card modfier's lsit of cards)
     * * @param mouseEvent
     */
    public void changeRequirementsToProduceAction(MouseEvent mouseEvent) {
        int i = 0;
        int numCoins=0,numStones=0,numShields=0,numServants=0;
        for (Integer numRequired : selectedCard.getAmountOfForProdRequirements()){
            if (selectedCard.getTypeOfResourceForProdRequirements().get(i).equals("coin")){
                numCoins += numRequired;
            }else if (selectedCard.getTypeOfResourceForProdRequirements().get(i).equals("stone")){
                numStones += numRequired;
            }else if (selectedCard.getTypeOfResourceForProdRequirements().get(i).equals("servant")){
                numShields += numRequired;
            }else if (selectedCard.getTypeOfResourceForProdRequirements().get(i).equals("shield")){
                numServants += numRequired;
            }
            i++;
        }
        ResourcesPageController resourcesPageController = (ResourcesPageController) gui.getResourcesPageController();
        resourcesPageController.setAmountsAndMode(numCoins,numStones,numShields,numServants,4);
        gui.changeStage("resourcesPage.fxml");
        disableAllButtons();
    }

    /**
     * used when someone uses the change card requirements button; we move to the resources page with mode = 4, passing
     * the number of resources required to activate the production (imported from dev card modfier's lsit of cards)
     * * @param mouseEvent
     */
    public void changeResourcesProducedAction(MouseEvent mouseEvent) {
        int i = 0;
        int numCoins=0,numStones=0,numShields=0,numServants=0;
        for (Integer numRequired : selectedCard.getAmountOfForProdResults()){
            if (selectedCard.getTypeOfResourceForProdResults().get(i).equals("coin")){
                numCoins += numRequired;
            }else if (selectedCard.getTypeOfResourceForProdResults().get(i).equals("stone")){
                numStones += numRequired;
            }else if (selectedCard.getTypeOfResourceForProdResults().get(i).equals("servant")){
                numShields += numRequired;
            }else if (selectedCard.getTypeOfResourceForProdResults().get(i).equals("shield")){
                numServants += numRequired;
            }
            i++;
        }
        ResourcesPageController resourcesPageController = (ResourcesPageController) gui.getResourcesPageController();
        resourcesPageController.setAmountsAndMode(numCoins,numStones,numShields,numServants,5);
        gui.changeStage("resourcesPage.fxml");
        disableAllButtons();
    }

    /**
     * This method sets the card price to a certain number of resources
     * @param coins
     * @param stones
     * @param servants
     * @param shields
     */
    public void setResourcesForPrice(int coins, int stones, int servants, int shields) {
        devCardModifier.clearPrice(selectedCard.getCardIndex());
        if(coins!=0) {
            devCardModifier.addPriceToCard(selectedCard.getCardIndex(),coins,"coin");
        }
        if(stones!=0) {
            devCardModifier.addPriceToCard(selectedCard.getCardIndex(), stones, "stone");
        }
        if(servants!=0) {
            devCardModifier.addPriceToCard(selectedCard.getCardIndex(), servants, "servant");
        }
        if(shields!=0) {
            devCardModifier.addPriceToCard(selectedCard.getCardIndex(), shields, "shield");
        }
    }

    /**
     * This method sets the card prod requirements to a certain number of resources
     * @param coins
     * @param stones
     * @param servants
     * @param shields
     */
    public void setResourcesForProdRequirements(int coins, int stones, int servants, int shields) {
        devCardModifier.clearProdRequirements(selectedCard.getCardIndex());
        if(coins!=0) {
            devCardModifier.addProdRequirementOfCard(selectedCard.getCardIndex(),coins,"coin");
        }
        if(stones!=0) {
            devCardModifier.addProdRequirementOfCard(selectedCard.getCardIndex(), stones, "stone");
        }
        if(servants!=0) {
            devCardModifier.addProdRequirementOfCard(selectedCard.getCardIndex(), servants, "servant");
        }
        if(shields!=0) {
            devCardModifier.addProdRequirementOfCard(selectedCard.getCardIndex(), shields, "shield");
        }
    }

    /**
     * This method sets the card prod results to a certain number of resources
     * @param coins
     * @param stones
     * @param servants
     * @param shields
     */
    public void setResourcesForProdResults(int coins, int stones, int servants, int shields) {
        devCardModifier.clearProdResults(selectedCard.getCardIndex());
        if(coins!=0) {
            devCardModifier.addProdResultOfCard(selectedCard.getCardIndex(),coins,"coin");
        }
        if(stones!=0) {
            devCardModifier.addProdResultOfCard(selectedCard.getCardIndex(), stones, "stone");
        }
        if(servants!=0) {
            devCardModifier.addProdResultOfCard(selectedCard.getCardIndex(), servants, "servant");
        }
        if(shields!=0) {
            devCardModifier.addProdResultOfCard(selectedCard.getCardIndex(), shields, "shield");
        }
    }
}
