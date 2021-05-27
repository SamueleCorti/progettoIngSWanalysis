package it.polimi.ingsw.gui.controllers;

import it.polimi.ingsw.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class InitializationController implements GUIController{
    @FXML private ImageView img1;
    @FXML private ImageView img2;
    @FXML private ImageView img4;
    @FXML private ImageView img3;
    private GUI gui;



    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }
}
