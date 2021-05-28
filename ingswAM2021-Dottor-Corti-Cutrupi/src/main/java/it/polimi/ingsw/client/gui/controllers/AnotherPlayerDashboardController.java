package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class AnotherPlayerDashboardController implements GUIController{

    @FXML private Button goBackButton;
    private GUI gui;

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }


    public void goBackToDashboard(MouseEvent mouseEvent) {
        gui.changeStage("dashboard.fxml");
    }
}
