package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.actions.mainActions.EndTurn;
import it.polimi.ingsw.client.actions.secondaryActions.ViewDashboardAction;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.utility.DevelopmentCardForGUI;
import it.polimi.ingsw.client.gui.utility.ImageSearcher;
import it.polimi.ingsw.server.messages.jsonMessages.*;
import it.polimi.ingsw.server.messages.printableMessages.YouActivatedPapalCard;
import it.polimi.ingsw.server.messages.printableMessages.YouActivatedPapalCardToo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Objects;

public class DashboardController implements GUIController{


    //strongbox items
    @FXML private ImageView coinResourceStrongbox;
    @FXML private ImageView stoneResourceStrongbox;
    @FXML private ImageView servantResourceStrongbox;
    @FXML private ImageView shieldResourceStrongbox;
    @FXML private Label coinInStrongboxLabel;
    @FXML private Label stoneInStrongboxLabel;
    @FXML private Label servantInStrongboxLabel;
    @FXML private Label shieldInStrongboxLabel;

    //player's name label
    @FXML private Label playerNameLabel;

    @FXML private Button viewDashboardButton;
    @FXML private ChoiceBox choiceViewDashboard;
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
    @FXML private ImageView Depot11;
    @FXML private ImageView Depot21;
    @FXML private ImageView Depot22;
    @FXML private ImageView Depot31;
    @FXML private ImageView Depot32;
    @FXML private ImageView Depot33;

    private ArrayList<ImageView> devCardZones;
    private ArrayList<ImageView> papalPath;
    private Image redCross;



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


        Image coinImage= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/coin.png")));
        coinResourceStrongbox.setImage(coinImage);
        Image stoneImage= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/stone.png")));
        stoneResourceStrongbox.setImage(stoneImage);
        Image servantImage= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/servant.png")));
        servantResourceStrongbox.setImage(servantImage);
        Image shieldImage= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/shield.png")));
        shieldResourceStrongbox.setImage(shieldImage);

    }

    @FXML
    public void setupDashboardNicknameAndChoiceBox() {
        String nicknameLabel = (gui.getPlayerNickname()+"'s Dashboard");
        playerNameLabel.setText(nicknameLabel);
        ArrayList <String> playersNicknames = gui.getPlayersNicknamesInOrder();
        System.out.println("players nicknames:");
        for(String nickname: playersNicknames){
            System.out.println(nickname);
        }
        for (int i=0; i<gui.amountOfPlayers();i++){
            choiceViewDashboard.getItems().add(playersNicknames.get(i) +"'s dashboard");
        }
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
        //todo: reset depots
        Depot31.setImage(null);
        Depot32.setImage(null);
        Depot33.setImage(null);
        Depot21.setImage(null);
        Depot22.setImage(null);
        Depot11.setImage(null);

        gui.resetMyLeaderCards();
        for(ImageView devZone: this.devCardZones){
            devZone.setImage(null);
        }
    }



    public void openActionMenu(MouseEvent mouseEvent) {
        gui.changeStage("actionChoice.fxml");
    }

    public void addCardToDevCardZone(DevelopmentCardsInDashboard messages) {
        for(DevelopmentCardMessage message: messages.getMessages()){
            ImageSearcher imageSearcher= new ImageSearcher();
            int position= message.getLevel()+ (message.getDevCardZone())*3 -1;
            String devCard= imageSearcher.getImageFromColorVictoryPoints(message.getColor(), message.getVictoryPoints());
            Image image= new Image(Objects.requireNonNull(getClass().getResourceAsStream(devCard)));
            devCardZones.get(position).setImage(image);
        }
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
                image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/papalActive1.png")));
                PapalFavorCard1.setImage(image);
                break;
            case 2:
                image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/papalActive2.png")));
                PapalFavorCard2.setImage(image);
                break;
            case 3:
                image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/papalActive3.png")));
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
                image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/papalCard2Disc.png")));
                PapalFavorCard1.setImage(image);
                break;
            case 2:
                image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/papalCard3Disc.png")));
                PapalFavorCard2.setImage(image);
                break;
            case 3:
                image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/papalCard4Disc.png")));
                PapalFavorCard3.setImage(image);
                break;
            default:
                break;
        }
    }

    /**
     * Whenever a player refreshes his dashboard or gets resources from market this methods gets called. To better understand its implementation it's necessary to look at
     * {@link DepotMessage}
     */
    public void refreshDepot(DepotMessage message) {
        SerializationConverter converter= new SerializationConverter();
        int[][] resources= message.getDepots();
        for(int i= message.getSizeOfWarehouse()-1;i>=0;i--){
                Image image= new Image(Objects.requireNonNull(getClass().getResourceAsStream(converter.intToResourceStringMarket(resources[i][0]))));
                switch (i){
                    case 0:
                        if(resources[i][1]>0)       Depot31.setImage(image);
                        if(resources[i][1]>1)       Depot32.setImage(image);
                        if(resources[i][1]>2)       Depot33.setImage(image);
                        break;
                    case 1:
                        if(resources[i][1]>0)       Depot21.setImage(image);
                        if (resources[i][1]>1)      Depot22.setImage(image);
                        break;
                    case 2:
                        if(resources[i][1]>0)       Depot11.setImage(image);
                        break;
                }
        }
    }

    public void viewAnotherPlayerDashboard(MouseEvent mouseEvent) {
        int numOfDashboard;
        if(choiceViewDashboard.getValue().toString().equals(gui.getPlayersNicknamesInOrder().get(0)+"'s dashboard")){
            numOfDashboard = 1;
        }else if (choiceViewDashboard.getValue().toString().equals(gui.getPlayersNicknamesInOrder().get(1)+"'s dashboard")){
            numOfDashboard = 2;
        }else if (choiceViewDashboard.getValue().toString().equals(gui.getPlayersNicknamesInOrder().get(2)+"'s dashboard")){
            numOfDashboard = 3;
        }else if (choiceViewDashboard.getValue().toString().equals(gui.getPlayersNicknamesInOrder().get(3)+"'s dashboard")){
            numOfDashboard = 4;
        }else{
            numOfDashboard = 0;
        }
        ViewDashboardAction actionToSend = new ViewDashboardAction(numOfDashboard);
        gui.sendAction(actionToSend);
        gui.changeStage("anotherPlayerDashboard.fxml");

    }

    public void refreshStrongbox(StrongboxMessage message) {
        String coins = Integer.toString(message.getResourcesContained()[0]);
        String stones = Integer.toString(message.getResourcesContained()[1]);
        String servants = Integer.toString(message.getResourcesContained()[2]);
        String shields = Integer.toString(message.getResourcesContained()[3]);
        coinInStrongboxLabel.setText(coins);
        stoneInStrongboxLabel.setText(stones);
        servantInStrongboxLabel.setText(servants);
        shieldInStrongboxLabel.setText(shields);
    }

    public void endYourTurn(MouseEvent mouseEvent) {
        gui.sendAction(new EndTurn());
    }

    public void baseProd(MouseEvent mouseEvent) {
        gui.changeStage("baseProduction.fxml");
    }
}
