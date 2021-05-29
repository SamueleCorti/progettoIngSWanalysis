package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.exception.NicknameAlreadyTakenException;
import it.polimi.ingsw.exception.NoGameFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ConnectionController implements GUIController{
    @FXML private TextField address;
    @FXML private TextField port;
    @FXML private Label okcreatemessage;
    @FXML private Label errormessage;
    @FXML private ImageView audiobutton;
    boolean muted=false;
    GUI gui;

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }

    public void audiochange(MouseEvent mouseEvent) {
        if (muted) {
            gui.getPlayer().play();
            audiobutton.setImage(new Image(getClass().getResourceAsStream("/images/icons/speaker.png")));
            muted = false;
            gui.setAudioInStartingMenu(false);
        } else {
            gui.getPlayer().stop();
            audiobutton.setImage(new Image(getClass().getResourceAsStream("/images/icons/mute.png")));
            muted = true;
            gui.setAudioInStartingMenu(true);
        }
    }

    public void okconnect(MouseEvent mouseEvent) throws NicknameAlreadyTakenException, NoGameFoundException {
        try {
            if (address.getText().equals("") || port.getText().equals("")) {
                errormessage.setText("Error: you must insert both address and port!");
                errormessage.setOpacity(1);
            } else if (Integer.parseInt(port.getText()) < 1000) {
                errormessage.setText("Error: you must insert a number over 1000!");
                errormessage.setOpacity(1);
            }
            else {
                String addressToUse = address.getText();
                int portToUse = Integer.parseInt(port.getText());
                if(gui.activateConnection(addressToUse,portToUse)){
                    gui.changeStage("startingMenu.fxml");
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Server not reachable");
                    alert.setContentText(
                            "The entered IP/port doesn't match any active server or the server is not "
                                    + "running. Please try again!");
                    alert.showAndWait();
                }
            }
        }catch (NumberFormatException e){
            errormessage.setText("Error: you must insert a number in port text field!");
            errormessage.setOpacity(1);
        }
    }
}
