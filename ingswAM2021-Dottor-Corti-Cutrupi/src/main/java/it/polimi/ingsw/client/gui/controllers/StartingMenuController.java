package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.actions.matchManagementActions.CreateMatchAction;
import it.polimi.ingsw.client.actions.matchManagementActions.JoinMatchAction;
import it.polimi.ingsw.client.actions.matchManagementActions.RejoinMatchAction;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.exception.NicknameAlreadyTakenException;
import it.polimi.ingsw.exception.NoGameFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


public class StartingMenuController implements GUIController{
    private GUI gui;
    private boolean muted;
    @FXML private ImageView audiobutton;
    @FXML private TextField nickname;
    @FXML private TextField size;
    @FXML private Label okcreatemessage;
    @FXML private Label errormessage;
    @FXML private TextField address;
    @FXML private TextField port;
    @FXML private TextField gameid;
    @FXML private Pane mainPane;

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
        gui.changeStage("rejoiningPage.fxml");
    }

    public void join(MouseEvent mouseEvent) {
        gui.changeStage("joiningPage.fxml");
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
                errormessage.setOpacity(0);
                okcreatemessage.setText("Creation completed: wait for the server to create the lobby");
                okcreatemessage.setOpacity(1);
                String nicknameToSend = nickname.getText();
                int sizeToSend = Integer.parseInt(size.getText());
                CreateMatchAction createMatchAction= new CreateMatchAction(sizeToSend, nicknameToSend, "JSON");
                gui.sendAction(createMatchAction);
            }
        }catch (NumberFormatException e){
            errormessage.setText("Error: you must insert a number in size text field!");
            errormessage.setOpacity(1);
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

    public void okrejoin(MouseEvent mouseEvent) {
        if(nickname.getText().equals("")||gameid.getText().equals("")){
            errormessage.setText("Error: you must insert both nickname and gameid!");
            errormessage.setOpacity(1);
        }
        else{
            errormessage.setOpacity(0);
            okcreatemessage.setText("Rejoin completed: wait for the server to search for your game");
            okcreatemessage.setOpacity(1);
            String nicknameToSend = nickname.getText();
            int gameIdToSend = Integer.parseInt(gameid.getText());
            RejoinMatchAction rejoinMatchAction = new RejoinMatchAction(gameIdToSend,nicknameToSend);
            gui.sendAction(rejoinMatchAction);
        }
    }

    public void okjoin(MouseEvent mouseEvent) {
        if(nickname.getText().equals("")){
            errormessage.setText("Error: you must insert a valid nickname!");
            errormessage.setOpacity(1);
        }
        else{
            errormessage.setOpacity(0);
            okcreatemessage.setText("Join completed: wait for the server to create the lobby");
            okcreatemessage.setOpacity(1);
            String nicknameToSend = nickname.getText();
            JoinMatchAction joinMatchAction = new JoinMatchAction(nicknameToSend);
            gui.sendAction(joinMatchAction);
        }
    }

    public void goback(MouseEvent mouseEvent) {
        gui.changeStage("startingMenu.fxml");
    }
}
