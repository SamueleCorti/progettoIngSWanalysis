package it.polimi.ingsw.gui.controllers;

import it.polimi.ingsw.gui.GUI;

public class InitializationController implements GUIController{
    private GUI gui;

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }
}
