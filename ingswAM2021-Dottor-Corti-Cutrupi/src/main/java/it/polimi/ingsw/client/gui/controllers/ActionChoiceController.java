package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;

import java.util.Objects;

public class ActionChoiceController implements GUIController{
    private GUI gui;

    public void retunToDashboard(MouseEvent mouseEvent) {
        gui.changeStage("dashboard.fxml");
    }

    public void marketChoice(ActionEvent actionEvent) {
        gui.changeStage("market.fxml");
        Media pick = new Media(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("music/Stronghold 2 Soundtrack Draft arranged (O'Carolan's Draught).mp3")).toExternalForm());
        gui.changeMusic(pick);
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
