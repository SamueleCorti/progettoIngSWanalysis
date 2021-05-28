package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import javafx.scene.input.MouseEvent;

public class AnotherPlayerLeaderCardsController implements GUIController{

    private GUI gui;

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }


    public void userClickedOnTable(MouseEvent mouseEvent) {
        //todo: select the card (copy the other method)
    }

    public void viewCard(MouseEvent mouseEvent) {
        //todo: show the selected card (copy the other method)
    }
}
