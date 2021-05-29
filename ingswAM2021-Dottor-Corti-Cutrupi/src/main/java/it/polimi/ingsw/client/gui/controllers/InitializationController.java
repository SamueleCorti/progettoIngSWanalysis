package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.actions.initializationActions.BonusResourcesAction;
import it.polimi.ingsw.client.actions.initializationActions.DiscardLeaderCardsAction;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.utility.LeaderCardForGUI;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.server.messages.jsonMessages.SerializationConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class InitializationController implements GUIController{

    @FXML private Button deleteRowButton;
    @FXML private Button confirmResourceChoiceButton;
    @FXML private ChoiceBox<String> secondResourceChoice;
    @FXML private ChoiceBox<String> firstResourceChoice;
    @FXML private HBox chooseExtraResourcesBox;
    @FXML private Label error;
    @FXML private Button viewCardButton;
    @FXML private TableView<LeaderCardForGUI> tableView;
    @FXML private TableColumn<LeaderCardForGUI,String> cardName;
    @FXML private TableColumn<LeaderCardForGUI, String> checkbox;
    @FXML private TableColumn<LeaderCardForGUI,Integer> index;


    DiscardLeaderCardsAction discardCards;



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
    private void discardSelectedCards(MouseEvent mouseEvent) {
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
            ArrayList<Integer> indexesToSend= new ArrayList<>();
            for(int i=0;i<indexesToRemove.size();i++)   indexesToSend.add(indexesToRemove.get(i)-1);
            discardCards = new DiscardLeaderCardsAction(indexesToSend);
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
                gui.sendAction(discardCards);
                gui.changeStage("lobby.fxml");
            }

            deleteRowButton.setDisable(true);


        }
        else{
            error.setText("You must select "+gui.cardsToDiscard()+" cards!");
            error.setOpacity(1);
        }
    }

    public void choiceConfirmed(MouseEvent mouseEvent) {
        if(gui.getOrder()>1&&gui.getOrder()<4){
            if(firstResourceChoice.getValue()!=null){
                SerializationConverter converter = new SerializationConverter();
                ResourceType resourceChosen = converter.parseStringToResourceType( firstResourceChoice.getValue().toString());

                gui.sendAction(discardCards);
                gui.sendAction(new BonusResourcesAction(resourceChosen));

                gui.changeStage("lobby.fxml");
            }
        }else if (gui.getOrder()>3){
            if(firstResourceChoice.getValue()!=null&&secondResourceChoice.getValue()!=null){
                SerializationConverter converter = new SerializationConverter();
                ResourceType resourceChosen1 = converter.parseStringToResourceType( firstResourceChoice.getValue().toString());
                ResourceType resourceChosen2 = converter.parseStringToResourceType( secondResourceChoice.getValue().toString());

                gui.sendAction(discardCards);
                gui.sendAction(new BonusResourcesAction(resourceChosen1,resourceChosen2));

                gui.changeStage("lobby.fxml");
            }
        }
    }
}