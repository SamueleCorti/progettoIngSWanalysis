package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.utility.LeaderCardForGUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class YourLeaderCardsController implements GUIController{

    @FXML private TableView<LeaderCardForGUI> tableView;
    @FXML private Button goBackButton;
    @FXML private TableColumn<LeaderCardForGUI,String> cardName;
    @FXML private TableColumn<LeaderCardForGUI,Integer> index;



    private GUI gui;

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }

    public void initialize(){
        cardName.setCellValueFactory(data -> data.getValue().cardNameProperty());
        index.setCellValueFactory(new PropertyValueFactory<>("cardIndex"));
    }

    public void goBackToDashboard(MouseEvent mouseEvent) {
        gui.changeStage("dashboard.fxml");
    }


    public void viewCard(MouseEvent mouseEvent) {
        //todo show the card selected (copy the other method)
    }

    public void removeAllCards(){
        for(int i=0;i<tableView.getItems().size();i++){
            tableView.getItems().remove(i);
        }
    }

    public void userClickedOnTable(MouseEvent mouseEvent) {
        //todo select the card (copy the other method)
    }

    public void addCardToTableView(LeaderCardForGUI cardToAdd) {
        tableView.getItems().add(cardToAdd);
    }
}
