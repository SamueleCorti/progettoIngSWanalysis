package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.actions.mainActions.MarketAction;
import it.polimi.ingsw.client.actions.secondaryActions.PrintMarketAction;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.server.messages.showingMessages.MarketMessage;
import it.polimi.ingsw.server.messages.showingMessages.SerializationConverter;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class MarketController implements GUIController{
    private GUI gui;

    @FXML private ImageView Market11;
    @FXML private ImageView Market12;
    @FXML private ImageView Market13;
    @FXML private ImageView Market14;
    @FXML private ImageView Market21;
    @FXML private ImageView Market22;
    @FXML private ImageView Market23;
    @FXML private ImageView Market24;
    @FXML private ImageView Market31;
    @FXML private ImageView Market32;
    @FXML private ImageView Market33;
    @FXML private ImageView Market34;
    @FXML private ImageView FloatingMarble;
    private ArrayList<ImageView> marketView;

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
        marketView=new ArrayList<>();
        marketView.add(Market11);   marketView.add(Market12);   marketView.add(Market13);   marketView.add(Market14);   marketView.add(Market21);   marketView.add(Market22);
        marketView.add(Market23);   marketView.add(Market24);   marketView.add(Market31);   marketView.add(Market32);   marketView.add(Market33);   marketView.add(Market34);
        marketView.add(FloatingMarble);
    }


    public void returnToDashboard(MouseEvent mouseEvent) {
        gui.changeStage("dashboard.fxml");
    }

    public void MarketColumn1(MouseEvent mouseEvent) {
        marketAction(0,false);
    }

    public void MarketColumn2(MouseEvent mouseEvent) {
        marketAction(1,false);
    }

    public void MarketColumn3(MouseEvent mouseEvent) {
        marketAction(2,false);
    }

    public void MarketColumn4(MouseEvent mouseEvent) {
        marketAction(3,false);
    }

    public void MarketRow1(MouseEvent mouseEvent) {
        marketAction(0,true);
    }

    public void MarketRow2(MouseEvent mouseEvent) {
        marketAction(1,true);
    }

    public void MarketRow3(MouseEvent mouseEvent) {
        marketAction(2,true);
    }

    public void refreshMarket(MouseEvent mouseEvent) {
        gui.sendAction(new PrintMarketAction());
    }

    public void refreshMarket(MarketMessage message) {
        SerializationConverter converter= new SerializationConverter();
        int[][] market= message.getRepresentation();
        for(int row=0;row<3;row++){
            for(int column=0; column<4; column++){
                String resource= converter.intToMarbleStringMarket(market[row][column]);
                Image image= new Image(getClass().getResourceAsStream(resource));
                int position=row*4 + column;
                marketView.get(position).setImage(image);
            }
        }
        Image image= new Image(getClass().getResourceAsStream(converter.intToMarbleStringMarket(message.getFloatingMarbleRepresentation())));
        FloatingMarble.setImage(image);
    }

    public void marketAction(int index, boolean isRow){
        gui.sendAction(new MarketAction(index,isRow));
        gui.sendAction(new PrintMarketAction());
        gui.changeStage("dashboard.fxml");
    }
}
