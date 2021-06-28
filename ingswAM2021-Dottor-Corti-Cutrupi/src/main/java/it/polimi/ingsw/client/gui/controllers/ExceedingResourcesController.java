package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.actions.tertiaryActions.DiscardExcedingResourcesAction;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.model.resource.*;
import it.polimi.ingsw.server.messages.showingMessages.SerializationConverter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Objects;

public class ExceedingResourcesController implements GUIController{
    GUI gui;

    @FXML
    private ImageView img11;
    @FXML private Label counterCoin;
    @FXML private Label stoneCounter;
    @FXML private Label servantCounter;
    @FXML private Label shieldCounter;
    @FXML private Button prodSelectedButton;
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
    private int coins,stones,servants,shields, numOfProduced, index;

    ArrayList<ImageView> images;
    
    @Override
    public void setGui(GUI gui) {
        this.gui = gui;

        images = new ArrayList<>();
        images.add(img11);images.add(img12);images.add(img13);images.add(img14);images.add(img15);images.add(img21);
        images.add(img22);images.add(img23);images.add(img24);images.add(img25);images.add(img26);images.add(img31);
        images.add(img32);images.add(img33);images.add(img34);images.add(img35);images.add(img36);images.add(img37);
        images.add(img41);images.add(img42);images.add(img43);images.add(img44);

        coins=0; servants=0; stones=0;shields=0;
        counterCoin.setText(Integer.toString(coins)); stoneCounter.setText(Integer.toString(stones));
        servantCounter.setText(Integer.toString(servants)); shieldCounter.setText(Integer.toString(shields));
    }

    public void addCoin(MouseEvent mouseEvent) {
        coins++;
        counterCoin.setText(Integer.toString(coins));
    }

    public void removeCoin(MouseEvent mouseEvent) {
        if(coins==0)    return;
        coins--;
        counterCoin.setText(Integer.toString(coins));
    }

    public void addStone(MouseEvent mouseEvent) {
        stones++;
        stoneCounter.setText(Integer.toString(stones));
    }

    public void removeStone(MouseEvent mouseEvent) {
        if (stones==0)  return;;
        stones--;
        stoneCounter.setText(Integer.toString(stones));
    }

    public void addServant(MouseEvent mouseEvent) {
        servants++;
        servantCounter.setText(Integer.toString(servants));
    }

    public void removeServant(MouseEvent mouseEvent) {
        if(servants==0) return;;
        servants--;
        servantCounter.setText(Integer.toString(servants));
    }

    public void addShield(MouseEvent mouseEvent) {
        shields++;
        shieldCounter.setText(Integer.toString(shields));
    }

    public void removeShield(MouseEvent mouseEvent) {
        if(shields==0)  return;
        shields--;
        shieldCounter.setText(Integer.toString(shields));
    }

    public void produceSelected(MouseEvent mouseEvent) {
        ArrayList<ResourceType> resourceTypes=new ArrayList<>();
        for(int i=0; i<coins;i++)   resourceTypes.add(new CoinResource().getResourceType());
        for(int i=0; i<stones;i++)   resourceTypes.add(new StoneResource().getResourceType());
        for(int i=0; i<servants;i++)   resourceTypes.add(new ServantResource().getResourceType());
        for(int i=0; i<shields;i++)   resourceTypes.add(new ShieldResource().getResourceType());
        coins=0;    stones=0; servants=0; shields=0;        String string= Integer.toString(0);
        counterCoin.setText(string);  stoneCounter.setText(string);  servantCounter.setText(string);  shieldCounter.setText(string);
        gui.sendAction(new DiscardExcedingResourcesAction(resourceTypes));
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
    }

    private void resetImages() {
        for (ImageView img:images) {
            img.setImage(null);
        }
    }
}
