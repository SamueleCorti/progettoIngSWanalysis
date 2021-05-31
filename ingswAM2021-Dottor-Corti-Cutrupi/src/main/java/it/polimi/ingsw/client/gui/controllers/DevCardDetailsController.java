package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.actions.mainActions.DevelopmentAction;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.utility.DevelopmentCardForGUI;
import it.polimi.ingsw.client.gui.utility.ImageSearcher;
import it.polimi.ingsw.model.developmentcard.Color;
import it.polimi.ingsw.server.messages.jsonMessages.SerializationConverter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class DevCardDetailsController implements GUIController{
    private GUI gui;
    private int level;
    private Color color;
    private DevelopmentCardForGUI card;
    @FXML private Label levelLabel;
    @FXML private Label colorLabel;
    @FXML private Label cardPrice;
    @FXML private Label prodCostLabel;
    @FXML private Label prodResultsLabel;
    @FXML private ImageView devCardImage;
    @FXML private Button buy1;
    @FXML private Button buy2;
    @FXML private Button buy3;




    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }


    public void buyDevCard(int index) {
        //gui.resetDashboard();
        gui.sendAction(new DevelopmentAction(color,level,index));
        hideButtons();
        gui.changeStage("dashboard.fxml");
    }

    public void goBack(MouseEvent mouseEvent) {
        gui.changeStage("gameboard.fxml");
    }

    public void init(DevelopmentCardForGUI developmentCardForGUI){
        buy1.setDisable(true);  buy2.setDisable(true);  buy3.setDisable(true);
        SerializationConverter converter= new SerializationConverter();     ImageSearcher imageSearcher= new ImageSearcher();
        String string;
        string=String.valueOf(developmentCardForGUI.getLevel());
        levelLabel.setText(string);
        colorLabel.setText(converter.parseIntToColorString(developmentCardForGUI.getColor()));
        string = converter.parseIntArrayToStringOfResourcesPretty(developmentCardForGUI.getCardPrice());
        cardPrice.setText(string);
        string= converter.parseIntArrayToStringOfResourcesPretty(developmentCardForGUI.getProdRequirements());
        prodCostLabel.setText(string);
        string= converter.parseIntArrayToStringOfResourcesPretty(developmentCardForGUI.getProdResults());
        prodResultsLabel.setText(string);
        string= imageSearcher.getImageFromColorVictoryPoints(developmentCardForGUI.getColor(), developmentCardForGUI.getVictoryPoints());
        Image image= new Image(Objects.requireNonNull(getClass().getResourceAsStream(string)));
        if(image.isError())     image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsBackJPG/leaderCardBack.jpg")));
        devCardImage.setImage(image);
        level=developmentCardForGUI.getLevel();
        color=converter.stringToColor(converter.parseIntToColorString(developmentCardForGUI.getColor()));
    }

    public void showButtons(MouseEvent mouseEvent) {
        buy1.setDisable(false);  buy2.setDisable(false);  buy3.setDisable(false);
        buy1.setOpacity(1);     buy2.setOpacity(1);     buy3.setOpacity(1);
    }

    public void buyDevCard1() {
        buyDevCard(0);
    }

    public void buyDevCard2(MouseEvent mouseEvent) {
        buyDevCard(1);
    }

    public void buyDevCard3(MouseEvent mouseEvent) {
        buyDevCard(2);
    }

    public void hideButtons(){
        buy1.setDisable(true);  buy2.setDisable(true);  buy3.setDisable(true);
        buy1.setOpacity(0);     buy2.setOpacity(0);     buy3.setOpacity(0);
    }
}
