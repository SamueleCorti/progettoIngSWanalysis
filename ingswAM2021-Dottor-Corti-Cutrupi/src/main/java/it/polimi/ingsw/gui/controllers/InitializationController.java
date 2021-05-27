package it.polimi.ingsw.gui.controllers;

import it.polimi.ingsw.gui.GUI;
import it.polimi.ingsw.gui.Parser;
import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.server.messages.jsonMessages.LeaderCardMessage;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class InitializationController implements GUIController{
    @FXML private ImageView img1;
    @FXML private ImageView img2;
    @FXML private ImageView img4;
    @FXML private ImageView img3;
    private GUI gui;
    private int cardsReceived=0;



    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }

    public void discardCardsPhase(){
       /* img2.setImage(new Image(getClass().getResourceAsStream("/images/cardsBackJPG/Masters of Renaissance__Cards_BACK_3mmBleed-1.jpg")));
        img1.setImage(new Image(getClass().getResourceAsStream("/images/icons/speaker.png")));
        img3.setImage(new Image(getClass().getResourceAsStream("/images/cardsFrontJPG/extraProdServant.jpg")));
        img4.setImage(new Image(getClass().getResourceAsStream("images/cardsFrontJPG/extraProdCoin.jpg")));*/
    }

    public void addCardToDiscardScene(String string){
        System.out.println(cardsReceived);
        switch (cardsReceived){
            case 1: img1.setImage(new Image(getClass().getResourceAsStream(string)));
                break;
            case 2: img2.setImage(new Image(getClass().getResourceAsStream(string)));
                break;
            case 3: img3.setImage(new Image(getClass().getResourceAsStream(string)));
                break;
            case 4: img4.setImage(new Image(getClass().getResourceAsStream(string)));
                break;
            default:     img4.setImage(new Image(getClass().getResourceAsStream(string)));
        }
    }

    public void addCardToDiscardScene(Message message){
        cardsReceived++;
        LeaderCardMessage leaderCardMessage= (LeaderCardMessage) message;
        Parser parser= new Parser();
        addCardToDiscardScene(parser.getImageFromPowerTypeResource(leaderCardMessage.getSpecialPower(),leaderCardMessage.getSpecialPowerResources()[0]));
        System.out.println(parser.getImageFromPowerTypeResource(leaderCardMessage.getSpecialPower(),leaderCardMessage.getSpecialPowerResources()[0]));
        //guiSideSocket.addCardToDiscardScene(parser.getImageFromPowerTypeResource(leaderCardMessage.getSpecialPower(),leaderCardMessage.getSpecialPowerResources()[0]));
    }
}