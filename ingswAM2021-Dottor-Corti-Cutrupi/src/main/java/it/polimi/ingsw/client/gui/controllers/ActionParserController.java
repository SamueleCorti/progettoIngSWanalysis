package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class ActionParserController implements GUIController{
    private GUI gui;

    public void retunToDashboard(MouseEvent mouseEvent) {
        gui.changeStage("dashboard.fxml");
    }

    public void marketChoice(ActionEvent actionEvent) {
        gui.changeStage("market.fxml");
    }

    public void DevelopmentChoice(ActionEvent actionEvent) {
        gui.changeStage("gameboard.fxml");
    }

    public void SecondaryActionChoice(ActionEvent actionEvent) {
    }

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }
}
