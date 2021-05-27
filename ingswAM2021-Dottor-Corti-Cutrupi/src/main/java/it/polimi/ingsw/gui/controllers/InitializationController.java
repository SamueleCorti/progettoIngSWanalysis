package it.polimi.ingsw.gui.controllers;

import it.polimi.ingsw.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
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

    public void discardCardsPhase(){
        img2.setImage(new Image(getClass().getResourceAsStream("/images/cardsBackJPG/Masters of Renaissance__Cards_BACK_3mmBleed-1.jpg")));
        img1.setImage(new Image(getClass().getResourceAsStream("/images/icons/speaker.png")));
        img3.setImage(new Image(getClass().getResourceAsStream("/images/cardsBackJPG/Masters of Renaissance__Cards_BACK_3mmBleed-1.jpg")));
        img4.setImage(new Image(getClass().getResourceAsStream("/images/cardsBackJPG/Masters of Renaissance__Cards_BACK_3mmBleed-1.jpg")));
    }
}
