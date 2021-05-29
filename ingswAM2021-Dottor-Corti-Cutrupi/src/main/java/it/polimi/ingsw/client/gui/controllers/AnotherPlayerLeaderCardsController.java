package it.polimi.ingsw.client.gui.controllers;

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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AnotherPlayerLeaderCardsController implements GUIController{

    private GUI gui;
    @FXML private TableView<LeaderCardForGUI> tableView;
    @FXML private Button goBackButton;
    @FXML private TableColumn<LeaderCardForGUI,String> cardName;
    @FXML private TableColumn<LeaderCardForGUI,Integer> index;
    @FXML private Button viewCardButton;

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }

    public void initialize(){
        cardName.setCellValueFactory(data -> data.getValue().cardNameProperty());
        index.setCellValueFactory(new PropertyValueFactory<>("cardIndex"));
    }

    public void userClickedOnTable(MouseEvent mouseEvent) {

        //todo: select the card (copy the other method)
        this.viewCardButton.setDisable(false);
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

    public void addCardToTableView(LeaderCardForGUI cardToAdd) {
        tableView.getItems().add(cardToAdd);
    }

    public void goBackToAnotherPlayerDashboard(MouseEvent mouseEvent) {
        gui.changeStage("anotherPlayerDashboard.fxml");
    }
}
