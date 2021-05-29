package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.utility.LeaderCardForGUI;
import it.polimi.ingsw.server.messages.jsonMessages.SerializationConverter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class LeaderCardDetailsController implements GUIController {
    private GUI gui;
    private LeaderCardForGUI card;

    @FXML private ImageView image;
    @FXML private Label specialPowerLabel;
    @FXML private Label victoryPointsLabel;
    @FXML private Label requirementsLabel;

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void initData(LeaderCardForGUI selectedCard){
        SerializationConverter converter= new SerializationConverter();
        this.card = selectedCard;
        this.specialPowerLabel.setText(converter.parseIntToSpecialPowerPretty(selectedCard.getSpecialPower(),selectedCard.getSpecialPowerResources()));
        this.victoryPointsLabel.setText(Integer.toString(selectedCard.getVictoryPoints()));
        if(selectedCard.isNeedsResources())     requirementsLabel.setText(converter.parseIntArrayToStringOfResourcesPretty(selectedCard.getResourcesRequired()));
        else                                    requirementsLabel.setText(converter.parseIntToDevCardRequirementPretty(selectedCard.getDevelopmentCardsRequired()));
        //this.requirementsLabel.setText(Integer.toString(selectedCard.get));
        this.image.setImage(selectedCard.getCardImage());
    }

    public void goBack(MouseEvent mouseEvent) throws IOException {
        if (gui.checkShowingOtherPlayerDashboard()){
            gui.changeStage("anotherPlayerLeadercards.fxml");
        }else if(gui.isGameStarted()){
            gui.changeStage("yourLeaderCards.fxml");
        }else {
            gui.changeStage("discardleadercards.fxml");
        }
    }
}
