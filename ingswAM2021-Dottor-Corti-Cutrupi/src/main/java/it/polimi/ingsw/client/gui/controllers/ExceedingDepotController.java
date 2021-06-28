package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.actions.tertiaryActions.DiscardExcedingDepotAction;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.server.messages.showingMessages.SerializationConverter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Objects;

public class ExceedingDepotController implements GUIController{
    @FXML private ChoiceBox<String> choiceDepot;
    @FXML private Button deleteDepotButton;
    @FXML private ImageView img11;
    @FXML private ImageView img12;
    @FXML private ImageView img13;
    @FXML private ImageView img14;
    @FXML private ImageView img15;
    @FXML private ImageView img21;
    @FXML private ImageView img22;
    @FXML private ImageView img23;
    @FXML private ImageView img24;
    @FXML private ImageView img25;
    @FXML private ImageView img26;
    @FXML private ImageView img31;
    @FXML private ImageView img32;
    @FXML private ImageView img33;
    @FXML private ImageView img34;
    @FXML private ImageView img35;
    @FXML private ImageView img36;
    @FXML private ImageView img37;
    @FXML private ImageView img41;
    @FXML private ImageView img42;
    @FXML private ImageView img43;
    @FXML private ImageView img44;
    GUI gui;

    private ArrayList<ImageView> images;

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;

        images = new ArrayList<>();
        images.add(img11);images.add(img12);images.add(img13);images.add(img14);images.add(img15);images.add(img21);
        images.add(img22);images.add(img23);images.add(img24);images.add(img25);images.add(img26);images.add(img31);
        images.add(img32);images.add(img33);images.add(img34);images.add(img35);images.add(img36);images.add(img37);
        images.add(img41);images.add(img42);images.add(img43);images.add(img44);

        choiceDepot.setDisable(true); choiceDepot.setOpacity(0);
        deleteDepotButton.setOpacity(0); deleteDepotButton.setDisable(true);
        choiceDepot.getItems().add("1"); choiceDepot.getItems().add("2"); choiceDepot.getItems().add("3"); choiceDepot.getItems().add("4");
    }


    public void initializeExceeding(int[][] depots, int sizeOfWarehouse) {
        resetImages();
        SerializationConverter converter= new SerializationConverter();
        int[][] resources= depots;
        for(int i=0;i<sizeOfWarehouse;i++){
            Image image= new Image(Objects.requireNonNull(getClass().getResourceAsStream(converter.intToResourceStringMarket(resources[i][0]))));
            switch (i){
                case 0:
                    if(resources[i][1]>0)       img41.setImage(image);
                    if(resources[i][1]>1)       img42.setImage(image);
                    if(resources[i][1]>2)       img43.setImage(image);
                    if(resources[i][1]>3)       img44.setImage(image);
                    break;
                case 1:
                    if(resources[i][1]>0)       img31.setImage(image);
                    if(resources[i][1]>1)       img32.setImage(image);
                    if(resources[i][1]>2)       img33.setImage(image);
                    if(resources[i][1]>3)       img34.setImage(image);
                    if(resources[i][1]>4)       img35.setImage(image);
                    if(resources[i][1]>5)       img36.setImage(image);
                    if(resources[i][1]>6)       img37.setImage(image);
                    break;
                case 2:
                    if(resources[i][1]>0)       img21.setImage(image);
                    if(resources[i][1]>1)       img22.setImage(image);
                    if(resources[i][1]>2)       img23.setImage(image);
                    if(resources[i][1]>3)       img24.setImage(image);
                    if(resources[i][1]>4)       img25.setImage(image);
                    if(resources[i][1]>5)       img26.setImage(image);
                    break;
                case 3:
                    if(resources[i][1]>0)       img11.setImage(image);
                    if(resources[i][1]>1)       img12.setImage(image);
                    if(resources[i][1]>2)       img13.setImage(image);
                    if(resources[i][1]>3)       img14.setImage(image);
                    if(resources[i][1]>4)       img15.setImage(image);
                    break;
            }
        }
        choiceDepot.setDisable(false); choiceDepot.setOpacity(1);
        deleteDepotButton.setOpacity(1); deleteDepotButton.setDisable(false);
    }

    private void resetImages() {
        for (ImageView img:images) {
            img.setImage(null);
        }
    }

    public void deleteDepot(MouseEvent mouseEvent) {
        if(choiceDepot.getValue()!=null){
            int index = Integer.parseInt(choiceDepot.getValue());
            DiscardExcedingDepotAction action = new DiscardExcedingDepotAction(index);
            gui.sendAction(action);
        }
    }
}
