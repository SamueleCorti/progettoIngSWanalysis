package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.actions.secondaryActions.ViewGameboardAction;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.utility.ImageSearcher;
import it.polimi.ingsw.model.boardsAndPlayer.GameBoard;
import it.polimi.ingsw.server.messages.gameplayMessages.ViewGameboardMessage;
import it.polimi.ingsw.server.messages.jsonMessages.DevelopmentCardMessage;
import it.polimi.ingsw.server.messages.jsonMessages.GameBoardMessage;
import it.polimi.ingsw.server.messages.jsonMessages.LeaderCardMessage;
import it.polimi.ingsw.server.messages.jsonMessages.SerializationConverter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GameboardController implements GUIController{
    private GUI gui;

    @FXML private ImageView Blue1;
    @FXML private ImageView Blue2;
    @FXML private ImageView Blue3;
    @FXML private ImageView Green1;
    @FXML private ImageView Green2;
    @FXML private ImageView Green3;
    @FXML private ImageView Yellow1;
    @FXML private ImageView Yellow2;
    @FXML private ImageView Yellow3;
    @FXML private ImageView Purple1;
    @FXML private ImageView Purple2;
    @FXML private ImageView Purple3;
    ArrayList<ImageView> images;

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
        images=new ArrayList<>();
        images.add(Blue1);  images.add(Blue2);  images.add(Blue3);  images.add(Green1);  images.add(Green2);  images.add(Green3);
        images.add(Yellow1);  images.add(Yellow2);  images.add(Yellow3);  images.add(Purple1);  images.add(Purple2);  images.add(Purple3);
    }


    public void refreshGameboard(ViewGameboardMessage message) {
        ImageSearcher imageSearcher= new ImageSearcher();
        for(DevelopmentCardMessage developmentCardMessage: message.getMessages()){
            int pos= developmentCardMessage.getColor()*3 + developmentCardMessage.getLevel()-1;
            String devCard= imageSearcher.getImageFromColorVictoryPoints(developmentCardMessage.getColor(), developmentCardMessage.getVictoryPoints());
            Image image= new Image(Objects.requireNonNull(getClass().getResourceAsStream(devCard)));
            images.get(pos).setImage(image);
        }
    }

    public void refreshGameboard(MouseEvent mouseEvent) {
        gui.sendAction(new ViewGameboardAction());
    }

    public void openBlue3(MouseEvent mouseEvent) {
        gui.changeStage("devCardDetails.fxml");
    }

    public void openBlue2(MouseEvent mouseEvent) {
        gui.changeStage("devCardDetails.fxml");
    }

    public void openBlue1(MouseEvent mouseEvent) {
        gui.changeStage("devCardDetails.fxml");
    }

    public void openGreen3(MouseEvent mouseEvent) {
        gui.changeStage("devCardDetails.fxml");
    }

    public void openGreen2(MouseEvent mouseEvent) {
        gui.changeStage("devCardDetails.fxml");
    }

    public void openGreen1(MouseEvent mouseEvent) {
        gui.changeStage("devCardDetails.fxml");
    }

    public void openPurple3(MouseEvent mouseEvent) {
        gui.changeStage("devCardDetails.fxml");
    }

    public void openPurple2(MouseEvent mouseEvent) {
        gui.changeStage("devCardDetails.fxml");
    }

    public void openPurple1(MouseEvent mouseEvent) {
        gui.changeStage("devCardDetails.fxml");
    }

    public void openYellow3(MouseEvent mouseEvent) {
        gui.changeStage("devCardDetails.fxml");
    }

    public void openYellow2(MouseEvent mouseEvent) {
        gui.changeStage("devCardDetails.fxml");
    }

    public void openYellow1(MouseEvent mouseEvent) {
        gui.changeStage("devCardDetails.fxml");
    }

    public void seeDetails() throws IOException {/*
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/devCardDetails.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        LeaderCardDetailsController controller = loader.getController();
        controller.setGui(gui);
        controller.initData(tableView.getSelectionModel().getSelectedItem());

        Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();*/
    }
}
