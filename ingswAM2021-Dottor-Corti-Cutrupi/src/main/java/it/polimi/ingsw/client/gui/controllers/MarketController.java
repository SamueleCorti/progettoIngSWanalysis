package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.actions.mainActions.MarketAction;
import it.polimi.ingsw.client.actions.secondaryActions.PrintMarketAction;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.server.messages.jsonMessages.MarketMessage;
import it.polimi.ingsw.server.messages.jsonMessages.SerializationConverter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Locale;

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
        gui.sendAction(new MarketAction(0,false));
        gui.sendAction(new PrintMarketAction());
        gui.changeStage("dashboard.fxml");
    }

    public void MarketColumn2(MouseEvent mouseEvent) {
        gui.sendAction(new MarketAction(1,false));
        gui.sendAction(new PrintMarketAction());
        gui.changeStage("dashboard.fxml");
    }

    public void MarketColumn3(MouseEvent mouseEvent) {
        gui.sendAction(new MarketAction(2,false));
        gui.sendAction(new PrintMarketAction());
        gui.changeStage("dashboard.fxml");
    }

    public void MarketColumn4(MouseEvent mouseEvent) {
        gui.sendAction(new MarketAction(3,false));
        gui.sendAction(new PrintMarketAction());
        gui.changeStage("dashboard.fxml");
    }

    public void MarketRow1(MouseEvent mouseEvent) {
        gui.sendAction(new MarketAction(0,true));
        gui.sendAction(new PrintMarketAction());
        gui.changeStage("dashboard.fxml");
    }

    public void MarketRow2(MouseEvent mouseEvent) {
        gui.sendAction(new MarketAction(1,true));
        gui.sendAction(new PrintMarketAction());
        gui.changeStage("dashboard.fxml");
    }

    public void MarketRow3(MouseEvent mouseEvent) {
        gui.sendAction(new MarketAction(2,true));
        gui.sendAction(new PrintMarketAction());
        gui.changeStage("dashboard.fxml");
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
}
