package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.actions.initializationActions.DiscardLeaderCardsAction;
import it.polimi.ingsw.client.actions.secondaryActions.ActivateLeaderCardAction;
import it.polimi.ingsw.client.actions.secondaryActions.DiscardLeaderCard;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.utility.LeaderCardForGUI;
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

import java.io.IOException;
import java.util.ArrayList;

public class YourLeaderCardsController implements GUIController{

    @FXML private ImageView imgView;
    @FXML private Button discardCardButton;
    @FXML private Button activateCardButton;
    @FXML private TableView<LeaderCardForGUI> tableView;
    @FXML private Button goBackButton;
    @FXML private TableColumn<LeaderCardForGUI,String> cardName;
    @FXML private TableColumn<LeaderCardForGUI,String> status;
    @FXML private TableColumn<LeaderCardForGUI,Integer> index;
    @FXML private Button viewCardButton;


    private GUI gui;

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }

    public void initialize(){
        cardName.setCellValueFactory(data -> data.getValue().cardNameProperty());
        index.setCellValueFactory(new PropertyValueFactory<>("cardIndex"));
        status.setCellValueFactory(data -> data.getValue().statusProperty());
        this.viewCardButton.setDisable(true);
        this.activateCardButton.setDisable(true);
        this.discardCardButton.setDisable(true);
    }

    public void goBackToDashboard(MouseEvent mouseEvent) {
        gui.changeStage("dashboard.fxml");
    }


    public void viewCard(MouseEvent mouseEvent) throws IOException {

        //todo: show the selected card (copy the other method)
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

    public void removeAllCards(){
            tableView.getItems().clear();
    }

    public void userClickedOnTable(MouseEvent mouseEvent) {
        this.viewCardButton.setDisable(false);
        this.activateCardButton.setDisable(false);
        this.discardCardButton.setDisable(false);
        LeaderCardForGUI selectedCard = tableView.getSelectionModel().getSelectedItem();
        Image image = selectedCard.getCardImage();
        imgView.setImage(image);
    }

    public void addCardToTableView(LeaderCardForGUI cardToAdd) {
        tableView.getItems().add(cardToAdd);
    }

    public void removeCardsGivenIndexes(ArrayList<Integer> indexesToRemove) {
        for(int i=indexesToRemove.size()-1;i>=0;i--){
            tableView.getItems().remove(indexesToRemove.get(i)-1);
        }
        updateIndexes();
    }

    private void updateIndexes() {
        for(int i=0;i<tableView.getItems().size();i++){
            tableView.getItems().get(i).setCardIndex(i+1);
        }
    }

    public void activateCard(MouseEvent mouseEvent) {
        LeaderCardForGUI selectedCard = tableView.getSelectionModel().getSelectedItem();
        gui.sendAction(new ActivateLeaderCardAction(selectedCard.getCardIndex()));
    }

    public void discardCard(MouseEvent mouseEvent) {
        LeaderCardForGUI selectedCard = tableView.getSelectionModel().getSelectedItem();
        gui.sendAction(new DiscardLeaderCard(selectedCard.getCardIndex()));
    }
}
