package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.awt.*;


public class StartingMenuController implements GUIController{
    private GUI gui;
    private boolean muted;
    @FXML private ImageView audiobutton;
    @FXML private TextField nickname;
    @FXML private TextField size;
    @FXML private Label okcreatemessage;
    @FXML private Label errormessage;

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void create(MouseEvent mouseEvent) {
        gui.changeStage("creationPage.fxml");
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
        if (muted) {
            gui.getPlayer().play();
            audiobutton.setImage(new Image(getClass().getResourceAsStream("/images/icons/speaker.png")));
            muted = false;
        } else {
            gui.getPlayer().stop();
            audiobutton.setImage(new Image(getClass().getResourceAsStream("/images/icons/mute.png")));
            muted = true;
        }
    }

    public void okcreate(MouseEvent mouseEvent) {
        try {
            if(nickname.getText().equals("")||size.getText().equals("")){
                errormessage.setText("Error: you must insert both nickname and size!");
                errormessage.setOpacity(1);
            }
            else if (Integer.parseInt(size.getText()) < 1 || Integer.parseInt(size.getText()) > 4) {
                errormessage.setText("Error: you must insert a size between 1 and 4!");
                errormessage.setOpacity(1);
            }
            else{
                String nicknameToSend = nickname.getText();
                int sizeToSend = Integer.parseInt(size.getText());
            }
        }catch (NumberFormatException e){
            errormessage.setText("Error: you must insert a number in size text field!");
            errormessage.setOpacity(1);
        }
    }
}
