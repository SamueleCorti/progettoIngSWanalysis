package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.actions.matchManagementActions.CreateMatchAction;
import it.polimi.ingsw.client.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class CreationController implements GUIController{
    @FXML private ChoiceBox<String> versionChoiceBox;
    @FXML private TextField nickname;
    @FXML private TextField size;
    @FXML private Label okcreatemessage;
    @FXML private Label errormessage;
    private GUI gui;

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
        versionChoiceBox.getItems().add("Original version");
        versionChoiceBox.getItems().add("Customized version");
        versionChoiceBox.setValue("Original version");
        versionChoiceBox.setStyle("-fx-font-size:30");
    }

    /**
     * Used when a player wants to create a game
     * @param mouseEvent
     */
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
                gui.setNickname(nicknameToSend);
                int sizeToSend = Integer.parseInt(size.getText());
                CreateMatchAction createMatchAction= new CreateMatchAction(sizeToSend, nicknameToSend, "JSON");
                gui.sendAction(createMatchAction);
            }
        }catch (NumberFormatException e){
            errormessage.setText("Error: you must insert a number in size text field!");
            errormessage.setOpacity(1);
        }
    }

    /**
     * Goes back to mainMenu page
     * @param mouseEvent
     */
    public void goback(MouseEvent mouseEvent) {
        gui.changeStage("startingMenu.fxml");
    }
}
