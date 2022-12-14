package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.actions.mainActions.productionActions.DevelopmentProductionAction;
import it.polimi.ingsw.client.actions.secondaryActions.ViewDashboardAction;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.utility.ImageSearcher;
import it.polimi.ingsw.model.developmentcard.Color;
import it.polimi.ingsw.server.messages.showingMessages.DevelopmentCardMessage;
import it.polimi.ingsw.server.messages.showingMessages.SerializationConverter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class DevCardProductionController implements GUIController{
    @FXML private Label victoryPointsLabel;
    @FXML private Label levelLabel;
    @FXML private Label colorLabel;
    @FXML private Label prodCostLabel;
    @FXML private Label prodResultsLabel;
    @FXML private ImageView devCardImage;
    @FXML private Button prodButton;
    private GUI gui;
    private int level, victoryPoints, devZone;
    private Color color;

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }

    public void initializeProd(DevelopmentCardMessage message){
        SerializationConverter serializationConverter= new SerializationConverter();    ImageSearcher imageSearcher= new ImageSearcher();
        devZone=message.getDevCardZone();
        color= serializationConverter.intToColor(message.getColor());                          colorLabel.setText(color.toString());
        level=message.getLevel();                                                              victoryPoints=message.getVictoryPoints();
        victoryPointsLabel.setText(Integer.toString(victoryPoints));                            levelLabel.setText(Integer.toString(level));
        prodCostLabel.setText(serializationConverter.parseIntArrayToStringOfResourcesPretty(message.getProdRequirements()));
        prodResultsLabel.setText(serializationConverter.parseIntArrayToStringOfResourcesPretty(message.getProdResults()));
        prodButton.setDisable(false);       prodButton.setOpacity(1);
        Image image;
        if(!message.isWasCardModified()) {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imageSearcher.getImageFromColorVictoryPoints(message.getColor(), victoryPoints))));
        }else {
            image = new Image ((getClass().getResourceAsStream("/images/cardsFrontJPG/customdevcard.jpg")));
        }
        devCardImage.setImage(image);
    }

    /**
     * Used when a player wants to activate this card production, sends the acton to the server
     * @param mouseEvent
     */
    public void activateProd(MouseEvent mouseEvent) {
        gui.sendAction(new DevelopmentProductionAction(devZone+1));
        gui.sendAction(new ViewDashboardAction());
        gui.changeStage("dashboard.fxml");
    }

    public void goBack(MouseEvent mouseEvent) {
        gui.changeStage("dashboard.fxml");
    }
}
