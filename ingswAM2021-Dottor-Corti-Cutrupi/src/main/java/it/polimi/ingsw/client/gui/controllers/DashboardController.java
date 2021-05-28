package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.actions.secondaryActions.ViewDashboardAction;
import it.polimi.ingsw.client.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import javax.swing.text.TableView;
import javax.swing.text.html.ImageView;

public class DashboardController implements GUIController{


    @FXML private Button updateYourDashboardButton;
    @FXML private Button viewLeaderCardsButton;

    private GUI gui;

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }

    public void goToLeaderCards(MouseEvent mouseEvent) {
        gui.changeStage("yourLeaderCards.fxml");
    }

    public void viewYourDashboard(MouseEvent mouseEvent) {
        ViewDashboardAction actionToSend = new ViewDashboardAction(0);
        gui.sendAction(actionToSend);
    }



    public void openActionMenu(MouseEvent mouseEvent) {

    }

    //todo: if you're in order >3, move on the papalpath


}
