package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.actions.secondaryActions.ViewDashboardAction;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.utility.DevelopmentCardForGUI;
import it.polimi.ingsw.client.gui.utility.ImageSearcher;
import it.polimi.ingsw.server.messages.jsonMessages.DevelopmentCardMessage;
import it.polimi.ingsw.server.messages.jsonMessages.PapalPathMessage;
import it.polimi.ingsw.server.messages.jsonMessages.SerializationConverter;
import it.polimi.ingsw.server.messages.printableMessages.YouActivatedPapalCard;
import it.polimi.ingsw.server.messages.printableMessages.YouActivatedPapalCardToo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javax.swing.text.TableView;
import java.util.ArrayList;
import java.util.Objects;

public class DashboardController implements GUIController{


    @FXML private ImageView PapalPos0;
    @FXML private ImageView PapalPos1;
    @FXML private ImageView PapalPos2;
    @FXML private ImageView PapalPos3;
    @FXML private ImageView PapalPos4;
    @FXML private ImageView PapalPos5;
    @FXML private ImageView PapalPos6;
    @FXML private ImageView PapalPos7;
    @FXML private ImageView PapalPos8;
    @FXML private ImageView PapalPos9;
    @FXML private ImageView PapalPos10;
    @FXML private ImageView PapalPos11;
    @FXML private ImageView PapalPos12;
    @FXML private ImageView PapalPos13;
    @FXML private ImageView PapalPos14;
    @FXML private ImageView PapalPos15;
    @FXML private ImageView PapalPos16;
    @FXML private ImageView PapalPos17;
    @FXML private ImageView PapalPos18;
    @FXML private ImageView PapalPos19;
    @FXML private ImageView PapalPos20;
    @FXML private ImageView PapalPos21;
    @FXML private ImageView PapalPos22;
    @FXML private ImageView PapalPos23;
    @FXML private ImageView PapalPos24;
    @FXML private ImageView PapalFavorCard1;
    @FXML private ImageView PapalFavorCard2;
    @FXML private ImageView PapalFavorCard3;
    @FXML private Button updateYourDashboardButton;
    @FXML private Button viewLeaderCardsButton;
    @FXML private ImageView DevCardZone11;
    @FXML private ImageView DevCardZone12;
    @FXML private ImageView DevCardZone13;
    @FXML private ImageView DevCardZone21;
    @FXML private ImageView DevCardZone22;
    @FXML private ImageView DevCardZone23;
    @FXML private ImageView DevCardZone31;
    @FXML private ImageView DevCardZone32;
    @FXML private ImageView DevCardZone33;
    private ArrayList<ImageView> devCardZones;
    private ArrayList<ImageView> papalPath;
    Image redCross;


    private GUI gui;

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
        redCross= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/indicatorefede.png")));
        devCardZones=new ArrayList<>();
        papalPath= new ArrayList<>();
        devCardZones.add(DevCardZone11);    devCardZones.add(DevCardZone12);    devCardZones.add(DevCardZone13);
        devCardZones.add(DevCardZone21);    devCardZones.add(DevCardZone22);    devCardZones.add(DevCardZone23);
        devCardZones.add(DevCardZone31);    devCardZones.add(DevCardZone32);    devCardZones.add(DevCardZone33);
        papalPath.add(PapalPos0);  papalPath.add(PapalPos1);  papalPath.add(PapalPos2);  papalPath.add(PapalPos3);  papalPath.add(PapalPos4);  papalPath.add(PapalPos5);
        papalPath.add(PapalPos6);  papalPath.add(PapalPos7);  papalPath.add(PapalPos8);  papalPath.add(PapalPos9);  papalPath.add(PapalPos10);
        papalPath.add(PapalPos11);  papalPath.add(PapalPos12);  papalPath.add(PapalPos13);  papalPath.add(PapalPos14);  papalPath.add(PapalPos15);
        papalPath.add(PapalPos16);  papalPath.add(PapalPos17);  papalPath.add(PapalPos18);  papalPath.add(PapalPos19);  papalPath.add(PapalPos20);
        papalPath.add(PapalPos21);  papalPath.add(PapalPos22);  papalPath.add(PapalPos23);  papalPath.add(PapalPos24);
    }

    public void goToLeaderCards(MouseEvent mouseEvent) {
        gui.changeStage("yourLeaderCards.fxml");
    }

    public void viewYourDashboard(MouseEvent mouseEvent) {

        //we reset our dashboard before asking the server to send it again
        resetDashboard();


        ViewDashboardAction actionToSend = new ViewDashboardAction(0);
        gui.sendAction(actionToSend);
    }

    public void resetDashboard(){

        //todo: reset strongbox too

        gui.resetMyLeaderCards();

        for(ImageView devZone: this.devCardZones){

            if(devZone.getImage()!=null) {
                devZone.getImage().cancel();
            }
        }
    }



    public void openActionMenu(MouseEvent mouseEvent) {
        gui.changeStage("actionChoice.fxml");
    }

    public void addCardToDevCardZone(DevelopmentCardMessage message) {
        ImageSearcher imageSearcher= new ImageSearcher();
        int position= message.getLevel()+ (message.getDevCardZone()-1)*3 -1;
        String devCard= imageSearcher.getImageFromColorVictoryPoints(message.getColor(), message.getVictoryPoints());
        Image image= new Image(Objects.requireNonNull(getClass().getResourceAsStream(devCard)));
        devCardZones.get(position).setImage(image);
    }

    public void viewPlayer1Dashboard(MouseEvent mouseEvent) {
        ViewDashboardAction actionToSend = new ViewDashboardAction(1);
        gui.sendAction(actionToSend);
        gui.changeStage("anotherPlayerDashboard.fxml");
    }


    public void printPapalPath(PapalPathMessage message) {
        int pos= message.getPlayerFaithPos();
        for(int i=0;i<pos;i++){
            papalPath.get(i).setOpacity(0);
        }
        papalPath.get(pos).setImage(redCross);
    }


    public void activatePapalCard(int index) {
        Image image;
        switch (index){
            case 1:
                image= new Image(getClass().getResourceAsStream("/images/general/papalActive1.png"));
                PapalFavorCard1.setImage(image);
                break;
            case 2:
                image= new Image(getClass().getResourceAsStream("/images/general/papalActive2.png"));
                PapalFavorCard2.setImage(image);
                break;
            case 3:
                image= new Image(getClass().getResourceAsStream("/images/general/papalActive3.png"));
                PapalFavorCard3.setImage(image);
                break;
            default:
                break;
        }
    }

    public void discardPapalCard(int index) {
        Image image;
        switch (index){
            case 1:
                image= new Image(getClass().getResourceAsStream("/images/general/papalCard2Disc.png.png"));
                PapalFavorCard1.setImage(image);
                break;
            case 2:
                image= new Image(getClass().getResourceAsStream("/images/general/papalCard3Disc.png.png"));
                PapalFavorCard2.setImage(image);
                break;
            case 3:
                image= new Image(getClass().getResourceAsStream("/images/general/papalCard4Disc.png.png"));
                PapalFavorCard3.setImage(image);
                break;
            default:
                break;
        }
    }
}
