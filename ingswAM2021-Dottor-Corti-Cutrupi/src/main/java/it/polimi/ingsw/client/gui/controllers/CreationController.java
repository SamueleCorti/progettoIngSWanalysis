package it.polimi.ingsw.client.gui.controllers;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.client.actions.matchManagementActions.CreateMatchAction;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.model.boardsAndPlayer.Player;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

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

            //we check if the size of the game is not too big for the num of cards given
            JsonReader reader1 = null;
            JsonParser parser1 = new JsonParser();

            reader1 = new JsonReader(new InputStreamReader(getClass().getResourceAsStream("/leadercardsparametersFA.json")));

            JsonArray leaderParametersForCheck = parser1.parse(reader1).getAsJsonArray();
            Gson gson1 = new Gson();
            int[] arr = gson1.fromJson(leaderParametersForCheck, int[].class);

            if(arr[0]*Integer.parseInt(size.getText())>18&&versionChoiceBox.getValue().equals("Customized version")){
                errormessage.setText("Error: you can't create a game of this size with the selected number of leader cards!");
                errormessage.setOpacity(1);
            }else if(nickname.getText().equals("")||size.getText().equals("")){
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

                    reader = new JsonReader(new InputStreamReader(getClass().getResourceAsStream("/LeaderCardsInstancingFA.json")));
                    JsonArray leaderCardsArray = parser.parse(reader).getAsJsonArray();
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String leaderCardsArrayJson = gson.toJson(leaderCardsArray);

                    reader = new JsonReader(new InputStreamReader(getClass().getResourceAsStream("/standardprodParametersFa.json")));
                    JsonArray standardProdArray = parser.parse(reader).getAsJsonArray();
                    String standardProdArrayJson = gson.toJson(standardProdArray);

                    reader = new JsonReader(new InputStreamReader(getClass().getResourceAsStream("/DevCardInstancingFA.json")));
                    JsonArray devCardsArray = parser.parse(reader).getAsJsonArray();
                    String devCardsArrayJson = gson.toJson(devCardsArray);

                    reader = new JsonReader(new InputStreamReader(getClass().getResourceAsStream("/leadercardsparametersFA.json")));
                    JsonArray leaderParametersProdArray = parser.parse(reader).getAsJsonArray();
                    String leaderParametersProdArrayJson = gson.toJson(leaderParametersProdArray);

                    reader = new JsonReader(new InputStreamReader(getClass().getResourceAsStream("/papalpathtilesFA.json")));
                    JsonArray tilesArray = parser.parse(reader).getAsJsonArray();
                    String tilesArrayJson = gson.toJson(tilesArray);

                    reader = new JsonReader(new InputStreamReader(getClass().getResourceAsStream("/favorcardsFA.json")));
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
