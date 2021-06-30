package it.polimi.ingsw.client.gui.controllers;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.client.actions.matchManagementActions.CreateMatchAction;
import it.polimi.ingsw.client.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

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
                    CreateMatchAction createMatchAction;
                    errormessage.setOpacity(0);
                    okcreatemessage.setText("Creation completed: wait for the server to create the lobby");
                    okcreatemessage.setOpacity(1);
                    String nicknameToSend = nickname.getText();
                    gui.setNickname(nicknameToSend);
                    int sizeToSend = Integer.parseInt(size.getText());

                if(versionChoiceBox.getValue().equals("Original version")){
                    createMatchAction= new CreateMatchAction(sizeToSend, nicknameToSend);
                }else {

                    JsonReader reader = null;
                    JsonParser parser = new JsonParser();

                    try {
                        reader = new JsonReader(new FileReader("src/main/resources/LeaderCardsInstancing.json"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    JsonArray leaderCardsArray = parser.parse(reader).getAsJsonArray();
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String leaderCardsArrayJson = gson.toJson(leaderCardsArray);

                    try {
                        reader = new JsonReader(new FileReader("src/main/resources/standardprodParametersFA.json"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    JsonArray standardProdArray = parser.parse(reader).getAsJsonArray();
                    String standardProdArrayJson = gson.toJson(standardProdArray);

                    try {
                        reader = new JsonReader(new FileReader("src/main/resources/DevCardInstancing.json"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    JsonArray devCardsArray = parser.parse(reader).getAsJsonArray();
                    String devCardsArrayJson = gson.toJson(devCardsArray);

                    try {
                        reader = new JsonReader(new FileReader("src/main/resources/leadercardsparameters.json"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    JsonArray leaderParametersProdArray = parser.parse(reader).getAsJsonArray();
                    String leaderParametersProdArrayJson = gson.toJson(leaderParametersProdArray);

                    try {
                        reader = new JsonReader(new FileReader("src/main/resources/papalpathtiles.json"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    JsonArray tilesArray = parser.parse(reader).getAsJsonArray();
                    String tilesArrayJson = gson.toJson(tilesArray);

                    try {
                        reader = new JsonReader(new FileReader("src/main/resources/favorcards.json"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    JsonArray favorArray = parser.parse(reader).getAsJsonArray();
                    String favorArrayJson = gson.toJson(favorArray);

                    createMatchAction = new CreateMatchAction(sizeToSend, nicknameToSend,devCardsArrayJson,favorArrayJson,leaderCardsArrayJson,leaderParametersProdArrayJson,standardProdArrayJson,tilesArrayJson);
                }
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
