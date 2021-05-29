package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.actions.mainActions.MarketAction;
import it.polimi.ingsw.client.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MarketController implements GUIController{
    public Button returntodashboardbutton;
    private GUI gui;
    @FXML private ImageView Market11;
    @FXML private ImageView Market12;
    @FXML private ImageView Market13;
    @FXML private ImageView Market14;
    @FXML private ImageView Market21;
    @FXML private ImageView Market22;
    @FXML private ImageView Market23;
    @FXML private ImageView Market24;
    @FXML private ImageView Market31;
    @FXML private ImageView Market32;
    @FXML private ImageView Market33;
    @FXML private ImageView Market34;

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }


    public void returnToDashboard(MouseEvent mouseEvent) {
        gui.changeStage("dashboard.fxml");
    }

    public void MarketColumn1(MouseEvent mouseEvent) {
        gui.sendAction(new MarketAction(0,false));
        gui.changeStage("dashboard.fxml");
    }

    public void MarketColumn2(MouseEvent mouseEvent) {
        gui.sendAction(new MarketAction(1,false));
        gui.changeStage("dashboard.fxml");
    }

    public void MarketColumn3(MouseEvent mouseEvent) {
        gui.sendAction(new MarketAction(2,false));
        gui.changeStage("dashboard.fxml");
    }

    public void MarketColumn4(MouseEvent mouseEvent) {
        gui.sendAction(new MarketAction(3,false));
        gui.changeStage("dashboard.fxml");
    }

    public void MarketRow1(MouseEvent mouseEvent) {
        gui.sendAction(new MarketAction(0,true));
        gui.changeStage("dashboard.fxml");
    }

    public void MarketRow2(MouseEvent mouseEvent) {
        gui.sendAction(new MarketAction(1,true));
        gui.changeStage("dashboard.fxml");
    }

    public void MarketRow3(MouseEvent mouseEvent) {
        gui.sendAction(new MarketAction(2,true));
        gui.changeStage("dashboard.fxml");
    }
}
