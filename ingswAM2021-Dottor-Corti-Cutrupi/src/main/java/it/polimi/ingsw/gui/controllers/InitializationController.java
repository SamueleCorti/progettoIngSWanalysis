package it.polimi.ingsw.gui.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.gui.GUI;
import it.polimi.ingsw.gui.LeaderCardForGUI;
import it.polimi.ingsw.gui.Parser;
import it.polimi.ingsw.model.developmentcard.Color;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.LeaderCardForJson;
import it.polimi.ingsw.model.leadercard.leaderpowers.*;
import it.polimi.ingsw.model.requirements.DevelopmentRequirements;
import it.polimi.ingsw.model.requirements.Requirements;
import it.polimi.ingsw.model.requirements.ResourcesRequirementsForAcquisition;
import it.polimi.ingsw.model.requirements.ResourcesRequirementsForLeaderCards;
import it.polimi.ingsw.model.resource.*;
import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.server.messages.jsonMessages.LeaderCardMessage;
import it.polimi.ingsw.server.messages.jsonMessages.SerializationConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class InitializationController implements GUIController{
    @FXML private Button viewCardButton;
    @FXML private ImageView img1;
    @FXML private ImageView img2;
    @FXML private ImageView img4;
    @FXML private ImageView img3;
    @FXML private TableView<LeaderCardForGUI> tableView;
    @FXML private TableColumn<LeaderCardForGUI,String> cardName;
    private GUI gui;
    private int cardsReceived=0;



    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }

    public void discardCardsPhase(){
       /* img2.setImage(new Image(getClass().getResourceAsStream("/images/cardsBackJPG/Masters of Renaissance__Cards_BACK_3mmBleed-1.jpg")));
        img1.setImage(new Image(getClass().getResourceAsStream("/images/icons/speaker.png")));
        img3.setImage(new Image(getClass().getResourceAsStream("/images/cardsFrontJPG/extraProdServant.jpg")));
        img4.setImage(new Image(getClass().getResourceAsStream("images/cardsFrontJPG/extraProdCoin.jpg")));*/
    }

    public void addCardToDiscardScene(String string){
        System.out.println(cardsReceived);
        switch (cardsReceived){
            case 1: img1.setImage(new Image(getClass().getResourceAsStream(string)));
                break;
            case 2: img2.setImage(new Image(getClass().getResourceAsStream(string)));
                break;
            case 3: img3.setImage(new Image(getClass().getResourceAsStream(string)));
                break;
            case 4: img4.setImage(new Image(getClass().getResourceAsStream(string)));
                break;
            default:     img4.setImage(new Image(getClass().getResourceAsStream(string)));
        }
    }

    @FXML
    public void initialize() {
        //cardName.setCellValueFactory(new PropertyValueFactory<LeaderCardForGUI,String>("cardName"));
        cardName.setCellValueFactory(data -> data.getValue().cardNameProperty());
        this.viewCardButton.setDisable(true);
        tableView.setItems(getCards());
    }

    private ObservableList<LeaderCardForGUI> getCards() {
        ObservableList<LeaderCardForGUI> cards = FXCollections.observableArrayList();
        return cards;
    }

    public void userClickedOnTable(){
        this.viewCardButton.setDisable(false);
    }

    public void addCardToTableView(LeaderCardForGUI cardToAdd){
        tableView.getItems().add(cardToAdd);
    }

    public void addCardToDiscardScene(Message message){
        SerializationConverter converter= new SerializationConverter();
        cardsReceived++;
        LeaderCardMessage leaderCardMessage= (LeaderCardMessage) message;
        Parser parser= new Parser();
        addCardToDiscardScene(parser.getImageFromPowerTypeResource(((LeaderCardMessage) message).getSpecialPower(),converter.getResourceRelatedFromArray(((LeaderCardMessage) message).getSpecialPowerResources())));
        System.out.println(parser.getImageFromPowerTypeResource(leaderCardMessage.getSpecialPower(),leaderCardMessage.getSpecialPowerResources()[0]));
        //guiSideSocket.addCardToDiscardScene(parser.getImageFromPowerTypeResource(leaderCardMessage.getSpecialPower(),leaderCardMessage.getSpecialPowerResources()[0]));
    }

    public void viewCard(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/leadercarddetails.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        LeaderCardDetailsController controller = loader.getController();
        controller.setGui(gui);
        controller.initData(tableView.getSelectionModel().getSelectedItem());

        Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
}