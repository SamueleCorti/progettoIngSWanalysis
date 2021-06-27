package it.polimi.ingsw.parametersEditor;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;



public class MainMenuController implements GUIControllerFA {


    @FXML private Button goToStandardProdButton;
    @FXML private Button goToPapalPathButton;
    @FXML private Button goToLeaderButton;
    @FXML private Button goToDevButton;
    @FXML private Button goBackButton;
    @FXML private Label numConsumedLabel;
    @FXML private Label numProducedLabel;

    private GUIFA gui;

    @Override
    public void setGui(GUIFA gui) {
        this.gui=gui;
    }


    public void goToDev(MouseEvent mouseEvent) {
        gui.changeStage("devCardsPage.fxml");
    }

    public void goToLeader(MouseEvent mouseEvent) {
        gui.changeStage("leaderCardsPage.fxml");
    }
    
    public void goToPapalPath(MouseEvent mouseEvent) {
        gui.changeStage("papalPathPage.fxml");
    }

    public void goToStandardProd(MouseEvent mouseEvent) {
        gui.changeStage("standardProdPage.fxml");
    }
}
