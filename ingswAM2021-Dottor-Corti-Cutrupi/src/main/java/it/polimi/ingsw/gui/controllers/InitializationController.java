package it.polimi.ingsw.gui.controllers;

import it.polimi.ingsw.client.actions.initializationActions.DiscardLeaderCardsAction;
import it.polimi.ingsw.gui.GUI;
import it.polimi.ingsw.gui.LeaderCardForGUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.util.ArrayList;

public class InitializationController implements GUIController{

    @FXML private Button confirmResourceChoiceButton;
    @FXML private ChoiceBox secondResourceChoice;
    @FXML private ChoiceBox firstResourceChoice;
    @FXML private HBox chooseExtraResourcesBox;
    @FXML private Label error;
    @FXML private Button viewCardButton;
    @FXML private TableView<LeaderCardForGUI> tableView;
    @FXML private TableColumn<LeaderCardForGUI,String> cardName;
    @FXML private TableColumn<LeaderCardForGUI, String> checkbox;
    @FXML private TableColumn<LeaderCardForGUI,Integer> index;



    private GUI gui;


    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }



    @FXML
    public void initialize() {
        cardName.setCellValueFactory(data -> data.getValue().cardNameProperty());
        index.setCellValueFactory(new PropertyValueFactory<>("cardIndex"));
        checkbox.setCellValueFactory(new PropertyValueFactory<LeaderCardForGUI,String>("checkbox"));

        this.viewCardButton.setDisable(true);
        tableView.setItems(getCards());

        tableView.setEditable(true);
        chooseExtraResourcesBox.setOpacity(0);

        firstResourceChoice.getItems().add("coin");
        firstResourceChoice.getItems().add("stone");
        firstResourceChoice.getItems().add("servant");
        firstResourceChoice.getItems().add("shield");
        firstResourceChoice.setDisable(true);

        secondResourceChoice.getItems().add("coin");
        secondResourceChoice.getItems().add("stone");
        secondResourceChoice.getItems().add("servant");
        secondResourceChoice.getItems().add("shield");
        secondResourceChoice.setDisable(true);

        confirmResourceChoiceButton.setDisable(true);

    }

    private ObservableList<LeaderCardForGUI> getCards() {
        return FXCollections.observableArrayList();
    }

    public void userClickedOnTable(){
        this.viewCardButton.setDisable(false);
    }

    public void addCardToTableView(LeaderCardForGUI cardToAdd){
        tableView.getItems().add(cardToAdd);
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




    @FXML
    private void deleteSelectedRows(MouseEvent mouseEvent) {
        ArrayList<Integer> indexesToRemove = new ArrayList<>();
        for(LeaderCardForGUI card : tableView.getItems())
        {
            if(card.getCheckbox().isSelected())
            {
                indexesToRemove.add(card.getCardIndex());
            }

        }

        if(indexesToRemove.size() == gui.cardsToDiscard()){

            //sending cards index to discard
            DiscardLeaderCardsAction discardCards = new DiscardLeaderCardsAction(indexesToRemove);
            gui.sendAction(discardCards);
            for(int i=indexesToRemove.size()-1;i>=0;i--){
                tableView.getItems().remove(indexesToRemove.get(i)-1);
            }

            if(gui.getOrder()>1&&gui.getOrder()<4){
                chooseExtraResourcesBox.setOpacity(1);
                secondResourceChoice.setOpacity(0);
                firstResourceChoice.setDisable(false);
                confirmResourceChoiceButton.setDisable(false);


            }else if (gui.getOrder()>3){
                chooseExtraResourcesBox.setOpacity(1);
                firstResourceChoice.setDisable(false);
                secondResourceChoice.setDisable(false);
                confirmResourceChoiceButton.setDisable(false);
            }else{
                //todo: go to next scene
            }



        }
        else{

            error.setText("You must select "+gui.cardsToDiscard()+" cards!");
            error.setOpacity(1);
        }

    }

    public void choiceConfirmed(MouseEvent mouseEvent) {
        if(gui.getOrder()>1&&gui.getOrder()<4){
            if(!firstResourceChoice.getValue().toString().equals("")){
                //todo: go to next scene
            }
        }

    }
}