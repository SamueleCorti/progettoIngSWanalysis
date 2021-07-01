package it.polimi.ingsw.parametersEditor.controllers;

import it.polimi.ingsw.parametersEditor.GUIFA;
import it.polimi.ingsw.parametersEditor.leaderCardsTools.LeaderCardParametersModifier;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class LeaderParametersPageController implements GUIControllerFA {

    private LeaderCardParametersModifier leaderCardParametersModifier;

    private GUIFA gui;
    @FXML
    private Button goBackButton;
    @FXML private Button increaseButton1;
    @FXML private Button increaseButton2;
    @FXML private Button decreaseButton1;
    @FXML private Button decreaseButton2;
    @FXML private Label numGiven;
    @FXML private Label numKept;

    public void goBackAndSaveChanges(MouseEvent mouseEvent) {
        leaderCardParametersModifier.writeValuesInJson();
        gui.changeStage("mainMenuPage.fxml");
    }

    @Override
    public void setGui(GUIFA gui) {
        this.gui=gui;
        this.leaderCardParametersModifier = new LeaderCardParametersModifier();
        leaderCardParametersModifier.importValues();
        numGiven.setText("" + leaderCardParametersModifier.getNumOfLeaderCardsGiven());
        numKept.setText("" + leaderCardParametersModifier.getNumOfLeaderCardsKept());
    }

    /**
     * method that updates the values of the labels
     */
    public void refreshLabels(){
        numGiven.setText("" + leaderCardParametersModifier.getNumOfLeaderCardsGiven());
        numKept.setText("" + leaderCardParametersModifier.getNumOfLeaderCardsKept());
    }

    public void decreaseGiven(MouseEvent mouseEvent) {
        if(leaderCardParametersModifier.getNumOfLeaderCardsGiven()>0) {
            leaderCardParametersModifier.decreaseNumOfLeaderCardsGiven();
            refreshLabels();
        }
    }

    public void increaseGiven(MouseEvent mouseEvent) {
        if(leaderCardParametersModifier.getNumOfLeaderCardsGiven()<18) {
            leaderCardParametersModifier.increaseNumOfLeaderCardsGiven();
            refreshLabels();
        }
    }

    public void decreaseKept(MouseEvent mouseEvent) {
        if(leaderCardParametersModifier.getNumOfLeaderCardsKept()>0) {
            leaderCardParametersModifier.decreaseNumOfLeaderCardsKept();
            refreshLabels();
        }
    }

    public void increaseKept(MouseEvent mouseEvent) {
        if(leaderCardParametersModifier.getNumOfLeaderCardsKept()<leaderCardParametersModifier.getNumOfLeaderCardsGiven()) {
            leaderCardParametersModifier.increaseNumOfLeaderCardsKept();
            refreshLabels();
        }
    }

}