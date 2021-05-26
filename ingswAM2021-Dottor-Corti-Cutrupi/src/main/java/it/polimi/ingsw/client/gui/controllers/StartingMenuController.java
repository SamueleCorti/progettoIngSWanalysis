package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import javafx.scene.input.MouseEvent;

public class StartingMenuController implements GUIController{
    private GUI gui;

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void create(MouseEvent mouseEvent) {
    }

    public void quit(MouseEvent mouseEvent) {
        System.out.println("Thanks for playing! See you next time!");
        System.exit(0);
    }

    public void rejoin(MouseEvent mouseEvent) {
    }

    public void join(MouseEvent mouseEvent) {
    }

    public void audiochange(MouseEvent mouseEvent) {
    }
}
