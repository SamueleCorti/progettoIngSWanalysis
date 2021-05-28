package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import javax.swing.text.TableView;
import javax.swing.text.html.ImageView;

public class DashboardController implements GUIController{

    private GUI gui;

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }

    public void openActionMenu(MouseEvent mouseEvent) {

    }

    //todo: if you're in order >3, move on the papalpath



}
