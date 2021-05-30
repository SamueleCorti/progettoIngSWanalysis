package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.actions.mainActions.DevelopmentAction;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.utility.DevelopmentCardForGUI;
import it.polimi.ingsw.client.gui.utility.ImageSearcher;
import it.polimi.ingsw.model.developmentcard.Color;
import it.polimi.ingsw.server.messages.jsonMessages.SerializationConverter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class DevCardDetailsController implements GUIController{
    private GUI gui;
    private DevelopmentCardForGUI card;
    @FXML private Label levelLabel;
    @FXML private Label colorLabel;
    @FXML private Label cardPrice;
    @FXML private Label prodCostLabel;
    @FXML private Label prodResultsLabel;
    @FXML private ImageView devCardImage;
    private int index;




    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }


    //TODO: fare in modo di chiedere al giocatore in che dev card zone vuole mettere la carta
    public void buyDevCard(MouseEvent mouseEvent) {
        SerializationConverter converter= new SerializationConverter();
        Color color=converter.stringToColor(colorLabel.toString());
        int level=Integer.parseInt(levelLabel.toString());
        gui.sendAction(new DevelopmentAction(color,level,index));
    }

    public void goBack(MouseEvent mouseEvent) {
        gui.changeStage("gameboard.fxml");
    }

    public void init(DevelopmentCardForGUI developmentCardForGUI){
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
    }
}
