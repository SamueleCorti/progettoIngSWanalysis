package it.polimi.ingsw.gui.controllers;

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
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;

public class InitializationController implements GUIController{
    @FXML private Button viewCardButton;
    @FXML private TableView<LeaderCardForGUI> tableView;
    @FXML private TableColumn<LeaderCardForGUI,String> cardName;
    @FXML private TableColumn<LeaderCardForGUI, Boolean> checkbox;

    private GUI gui;



    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }



    @FXML
    public void initialize() {
        cardName.setCellValueFactory(data -> data.getValue().cardNameProperty());

        checkbox.setCellValueFactory(new PropertyValueFactory("checked"));

        checkbox.setCellFactory(p -> new CheckBoxTableCell<>());
        Callback<TableColumn, TableCell> cellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };


        this.viewCardButton.setDisable(true);
        tableView.setItems(getCards());


        updateObservableListProperties(checkbox);

        tableView.setEditable(true);
    }

    private void updateObservableListProperties(TableColumn<LeaderCardForGUI, Boolean> checkbox) {
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


    public static class EditingCell extends TableCell<LeaderCardForGUI, String> {
        private TextField textField;

        public EditingCell() {
        }

        @Override public void startEdit() {
            super.startEdit();

            if (textField == null) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }



        @Override public void cancelEdit() {
            super.cancelEdit();
            setText((String) getItem());
            setGraphic(null);
        }

        @Override public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(textField.getText());
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

}