package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.actions.mainActions.DevelopmentAction;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.utility.DevelopmentCardForGUI;
import it.polimi.ingsw.model.developmentcard.Color;
import it.polimi.ingsw.model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.server.messages.jsonMessages.SerializationConverter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class DevCardDetailsController implements GUIController{
    private GUI gui;
    private DevelopmentCardForGUI card;
    @FXML private Label LevelLabel;
    @FXML private Label ColorLabel;
    @FXML private Label DevCostLabel;
    @FXML private Label ProdCostLabel;
    @FXML private Label ProdResultsLabel;
    @FXML private ImageView DevCardImage;
    private int index;




    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }

    //TODO: fare in modo di chiedere al giocatore in che dev card zone vuole mettere la carta
    public void buyDevCard(MouseEvent mouseEvent) {
        SerializationConverter converter= new SerializationConverter();
        Color color=converter.stringToColor(ColorLabel.toString());
        int level=Integer.parseInt(LevelLabel.toString());
        gui.sendAction(new DevelopmentAction(color,level,index));
    }

    public void goBack(MouseEvent mouseEvent) {
        gui.changeStage("gameboard.fxml");
    }

    public void init(DevelopmentCardForGUI developmentCardForGUI){

    }
}
