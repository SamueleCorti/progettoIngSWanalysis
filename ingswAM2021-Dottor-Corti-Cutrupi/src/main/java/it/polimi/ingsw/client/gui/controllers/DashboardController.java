package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.actions.mainActions.EndTurn;
import it.polimi.ingsw.client.actions.secondaryActions.ViewDashboardAction;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.utility.ImageSearcher;
import it.polimi.ingsw.server.messages.showingMessages.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class DashboardController implements GUIController{
    @FXML private ImageView audiobutton,coinImageCED,stoneImageCED,servantImageCED,shieldImageCED;
    @FXML private Label coinContainedCEDLabel,stoneContainedCEDLabel,servantContainedCEDLabel,shieldContainedCEDLabel,coinTotalCEDLabel,stoneTotalCEDLabel,servantTotalCEDLabel,shieldTotalCEDLabel;

    //strongbox items
    @FXML private ImageView coinResourceStrongbox,stoneResourceStrongbox,servantResourceStrongbox,shieldResourceStrongbox;
    @FXML private Label coinInStrongboxLabel,stoneInStrongboxLabel,servantInStrongboxLabel,shieldInStrongboxLabel;

    //player's name label
    @FXML private Label playerNameLabel;

    @FXML private ChoiceBox choiceViewDashboard;


    @FXML private ImageView PapalPos0,PapalPos1,PapalPos2,PapalPos3,PapalPos4,PapalPos5,PapalPos6,PapalPos7,PapalPos8,PapalPos9,PapalPos10,PapalPos11,PapalPos12,PapalPos13,PapalPos14,PapalPos15,PapalPos16,PapalPos17,PapalPos18,PapalPos19,PapalPos20,PapalPos21,PapalPos22,PapalPos23,PapalPos24;

    @FXML private ImageView PapalFavorCard1,PapalFavorCard2,PapalFavorCard3;

    @FXML private ImageView DevCardZone11,DevCardZone12,DevCardZone13,DevCardZone21,DevCardZone22,DevCardZone23,DevCardZone31,DevCardZone32,DevCardZone33;

    @FXML private ImageView Depot11,Depot21,Depot22,Depot31,Depot32,Depot33;

    @FXML private Label slashCoin, slashStone, slashServant, slashShield;

    private ArrayList<ImageView> devCardZones;
    private ArrayList<ImageView> papalPath, papalFavorCard;
    private Image redCross,blackCross,biColor;
    private boolean muted;
    private int pos,lorenzoPos, coinsInExtraDepot=0,stoneInExtraDepot=0,shieldInExtraDepot=0,servantInExtraDepot=0,maxCoinsInExtraDepot=0,
            maxStoneInExtraDepot=0,maxShieldInExtraDepot=0,maxServantInExtraDepot=0;
    private int[] devCards=new int[9], firstCardPosition = new int[3];
    private DevelopmentCardMessage[] developmentCardForGUIS= new DevelopmentCardMessage[9];


    private GUI gui;

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
        redCross= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/indicatorefede.png")));
        blackCross = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/croce.png")));
        biColor = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/crocebicolore.png")));
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
        papalFavorCard=new ArrayList<>();    papalFavorCard.add(PapalFavorCard1);    papalFavorCard.add(PapalFavorCard2);     papalFavorCard.add(PapalFavorCard3);

        coinTotalCEDLabel.setOpacity(0);coinContainedCEDLabel.setOpacity(0);stoneTotalCEDLabel.setOpacity(0);stoneContainedCEDLabel.setOpacity(0);
        servantTotalCEDLabel.setOpacity(0);servantContainedCEDLabel.setOpacity(0);shieldTotalCEDLabel.setOpacity(0);shieldContainedCEDLabel.setOpacity(0);
        coinImageCED.setOpacity(0);slashCoin.setOpacity(0);stoneImageCED.setOpacity(0);slashStone.setOpacity(0);shieldImageCED.setOpacity(0);   slashShield.setOpacity(0);
        servantImageCED.setOpacity(0);slashServant.setOpacity(0);

        Image coinImage= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/coin.png")));
        coinResourceStrongbox.setImage(coinImage);
        Image stoneImage= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/stone.png")));
        stoneResourceStrongbox.setImage(stoneImage);
        Image servantImage= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/servant.png")));
        servantResourceStrongbox.setImage(servantImage);
        Image shieldImage= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/shield.png")));
        shieldResourceStrongbox.setImage(shieldImage);
        for(ImageView view: devCardZones)    view.setDisable(true);

    }

    /**
     * Used at the beginning of the match to set the name of the user at the top of the dashboard and filling the choice box with
     * the names of the other players
     */
    public void setupDashboardNicknameAndChoiceBox() {
        String nicknameLabel = (gui.getPlayerNickname()+"'s Dashboard");
        playerNameLabel.setText(nicknameLabel);
        choiceViewDashboard.getItems().clear();
        ArrayList <String> playersNicknames = gui.getPlayersNicknamesInOrder();
        for (String nickname:playersNicknames) {
            choiceViewDashboard.getItems().add(nickname+"'s dashboard");
        }
    }

    public void goToLeaderCards(MouseEvent mouseEvent) {
        gui.changeStage("yourLeaderCards.fxml");
    }

    /**
     * Action sent to update the dashboard view
     */
    public void viewYourDashboard() {
        //we reset our dashboard before asking the server to send it again
        resetDashboard();
        ViewDashboardAction actionToSend = new ViewDashboardAction(0);
        gui.sendAction(actionToSend);
    }

    /**
     * Used to reset the dashboard in order to refresh it to the new version
     */
    public void resetDashboard(){
        resetDepots();
        resetPapalPath();

        gui.resetMyLeaderCards();
        for(ImageView devZone: this.devCardZones){
            devZone.setImage(null);
        }
    }

    private void resetPapalPath() {
        for(ImageView imageView : papalFavorCard){
            imageView.setImage(null);
        }
    }

    public void resetDepots(){
        Depot31.setImage(null);Depot32.setImage(null);Depot33.setImage(null);Depot21.setImage(null);Depot22.setImage(null);
        Depot11.setImage(null);
        coinContainedCEDLabel.setText("0");stoneContainedCEDLabel.setText("0");servantContainedCEDLabel.setText("0");shieldContainedCEDLabel.setText("0");
    }

    /**
     * Used to add a dev Card to its dev Zone in the view
     * @param messages
     */
    public void addCardToDevCardZone(DevelopmentCardsInDashboard messages) {
        for(int i=0;i<9;i++)    devCards[i]=0;
        for(DevelopmentCardMessage message: messages.getMessages()){
            Image image;
            int position= message.getLevel()+ (message.getDevCardZone())*3 - 1;

            if(!message.isWasCardModified()) {
                ImageSearcher imageSearcher = new ImageSearcher();
                String devCard = imageSearcher.getImageFromColorVictoryPoints(message.getColor(), message.getVictoryPoints());
                image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(devCard)));
            }else{
                image = new Image ((getClass().getResourceAsStream("/images/cardsFrontJPG/customdevcard.jpg")));
            }

            devCardZones.get(position).setImage(image);
            devCards[position]=1;
            developmentCardForGUIS[position]=message;
        }
        handleDevCardViews();
    }

    /**
     * Used to have an array that contains 1 on the position relative only to the top card of each dev card zone
     */
    private void handleDevCardViews() {
        for(int i=8; i>=0; i--){
            if(devCards[i]==1){
                if(i%3==2){
                    devCards[i-1]=0;
                    devCards[i-2]=0;
                }
                else if(i%3==1)
                    devCards[i-1]=0;
            }
        }
        for(int i=0; i<9;i++){
            if(devCards[i]==0)    devCardZones.get(i).setDisable(true);
            else if(devCards[i]==1)     devCardZones.get(i).setDisable(false);
        }
    }

    /**
     * Used to print player position in the faith track and eventually Lorenzo's one
     * @param message
     */
    public void printPapalPath(PapalPathMessage message) {
        lorenzoPos = message.getLorenzoFaithPos();
        pos= message.getPlayerFaithPos();
        if(pos>lorenzoPos || lorenzoPos==0) {
            for (int i = 0; i < pos; i++) {
                papalPath.get(i).setOpacity(0);
            }
            papalPath.get(pos).setImage(redCross);
            if(lorenzoPos>0){
                papalPath.get(lorenzoPos).setImage(blackCross);
                papalPath.get(lorenzoPos).setOpacity(1);
            }
        }
        else if(pos == lorenzoPos){
            for (int i = 0; i < pos; i++) {
                papalPath.get(i).setOpacity(0);
            }
            papalPath.get(pos).setImage(biColor);
        }
        else{
            for (int i = 0; i < lorenzoPos; i++) {
                papalPath.get(i).setOpacity(0);
            }
            papalPath.get(pos).setImage(redCross);
            papalPath.get(pos).setOpacity(1);
            if(lorenzoPos>0)papalPath.get(lorenzoPos).setImage(blackCross);
        }
        for(int i=0 ; i<3 ; i++){
            int index=i+1;
            if(message.getCardsInfo()[i]==2){
                String string= "/images/general/papalActive"+index+".png";
                papalFavorCard.get(i).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(string))));
            }
            if(message.getCardsInfo()[i]==1){
                index++;
                String string= "/images/general/papalCard"+index+"Disc.png";
                papalFavorCard.get(i).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(string))));
            }
        }
    }

    /**
     * Used to flip the papal favor card to active
     * @param index
     */
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

    /**
     * Used to flip the papal favor card to inactive
     * @param index
     */
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
        resetDepots();
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
        for(int i = 0 ; i<message.getSizeOfExtraDepots() ; i++){
            /*Image image= new Image(Objects.requireNonNull(getClass().getResourceAsStream(converter.intToResourceStringMarket(resources[i+message.getSizeOfWarehouse()][0]))));
            switch (i){
                case 0:
                    if(resources[i+message.getSizeOfWarehouse()][1]>0)       Depot41.setImage(image);
                    if(resources[i+message.getSizeOfWarehouse()][1]>1)       Depot42.setImage(image);
                    break;
                case 1:
                    if(resources[i+message.getSizeOfWarehouse()][1]>0)       Depot51.setImage(image);
                    if (resources[i+message.getSizeOfWarehouse()][1]>1)      Depot52.setImage(image);
                    break;
            }*/
            switch (resources[message.getSizeOfWarehouse()+i][0]){
                case 0:
                    coinsInExtraDepot=resources[message.getSizeOfWarehouse()+i][1];
                    coinContainedCEDLabel.setText(Integer.toString(coinsInExtraDepot));
                    break;
                case 1:
                    stoneInExtraDepot=resources[message.getSizeOfWarehouse()+i][1];
                    stoneContainedCEDLabel.setText(Integer.toString(stoneInExtraDepot));
                    break;
                case 2:
                    servantInExtraDepot=resources[message.getSizeOfWarehouse()+i][1];
                    servantContainedCEDLabel.setText(Integer.toString(servantInExtraDepot));
                    break;
                case 3:
                    shieldInExtraDepot=resources[message.getSizeOfWarehouse()+i][1];
                    shieldContainedCEDLabel.setText(Integer.toString(shieldInExtraDepot));
                    break;

            }
        }
    }

    /**
     * Method called when the user wants to see another player's dashboard, changes the scene to anotherPlayerDashboard
     * @param mouseEvent
     */
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

    /**
     * Method called to add a normal extra depot (not customized) to the dashboard view
     * @param specialPowerResources
     */
    public void addExtraDepot(ArrayList<String> specialPowerResources) {
        for(String resource: specialPowerResources){
            switch (resource.toLowerCase(Locale.ROOT)){
                case "coin":    maxCoinsInExtraDepot++;     break;
                case "stone":   maxStoneInExtraDepot++;     break;
                case "shield":  maxShieldInExtraDepot++;    break;
                case "servant": maxServantInExtraDepot++;   break;
                default: break;
            }
        }
        if(maxCoinsInExtraDepot>0)  {
            coinImageCED.setOpacity(1);
            coinTotalCEDLabel.setText(Integer.toString(maxCoinsInExtraDepot));      coinTotalCEDLabel.setOpacity(1);
            coinContainedCEDLabel.setText(Integer.toString(coinsInExtraDepot)); coinContainedCEDLabel.setOpacity(1);
            slashCoin.setOpacity(1);
        }
        if (maxStoneInExtraDepot>0){
            stoneImageCED.setOpacity(1);
            stoneTotalCEDLabel.setText(Integer.toString(maxStoneInExtraDepot));  stoneTotalCEDLabel.setOpacity(1);
            stoneContainedCEDLabel.setText(Integer.toString(stoneInExtraDepot));    stoneContainedCEDLabel.setOpacity(1);
            slashStone.setOpacity(1);
        }
        if(maxShieldInExtraDepot>0){
            shieldImageCED.setOpacity(1);
            shieldTotalCEDLabel.setText(Integer.toString(maxShieldInExtraDepot));   shieldTotalCEDLabel.setOpacity(1);
            shieldContainedCEDLabel.setText(Integer.toString(shieldInExtraDepot));  shieldContainedCEDLabel.setOpacity(1);
            slashShield.setOpacity(1);
        }
        if(maxServantInExtraDepot>0){
            servantImageCED.setOpacity(1);
            servantTotalCEDLabel.setText(Integer.toString(maxServantInExtraDepot)); servantTotalCEDLabel.setOpacity(1);
            servantContainedCEDLabel.setText(Integer.toString(servantInExtraDepot));    servantContainedCEDLabel.setOpacity(1);
            slashServant.setOpacity(1);
        }
    }


    public void viewDevCard11(MouseEvent mouseEvent) throws IOException {
        viewCard(0, mouseEvent,0);
    }

    public void viewDevCard12(MouseEvent mouseEvent) throws IOException {
        viewCard(1, mouseEvent,0);
    }

    public void viewDevCard13(MouseEvent mouseEvent) throws IOException {
        viewCard(2, mouseEvent,0);
    }

    public void viewDevCard21(MouseEvent mouseEvent) throws IOException {
        viewCard(3, mouseEvent,1);
    }

    public void viewDevCard22(MouseEvent mouseEvent) throws IOException {
        viewCard(4, mouseEvent,1);
    }

    public void viewDevCard23(MouseEvent mouseEvent) throws IOException {
        viewCard(5, mouseEvent,1);
    }

    public void viewDevCard31(MouseEvent mouseEvent) throws IOException {
        viewCard(6, mouseEvent,2);
    }

    public void viewDevCard32(MouseEvent mouseEvent) throws IOException {
        viewCard(7, mouseEvent,2);
    }

    public void viewDevCard33(MouseEvent mouseEvent) throws IOException {
        viewCard(8, mouseEvent,2);
    }

    /**
     * Used to open the details of a card given its zone and level
     * @param zone
     * @throws IOException
     */
    public void viewCard(int index, MouseEvent mouseEvent, int zone) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/developmentCardProduction.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        DevCardProductionController controller = loader.getController();
        controller.setGui(gui);
        controller.initializeProd(developmentCardForGUIS[index]);

        Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    /**
     * Used to change volume in game
     */
    public void audiochange(MouseEvent mouseEvent) {
        if (muted) {
            gui.getPlayer().play();
            gui.getPlayer().setMute(false);
            audiobutton.setImage(new Image(getClass().getResourceAsStream("/images/icons/speaker.png")));
            muted = false;
        } else {
            gui.getPlayer().stop();
            gui.getPlayer().setMute(true);
            audiobutton.setImage(new Image(getClass().getResourceAsStream("/images/icons/mute.png")));
            muted = true;
        }
    }

    /**
     * Used to mute/unmute the page externally
     */
    public void setAudio(boolean isMuted){
        if(isMuted){
            muted=true;
            audiobutton.setImage(new Image(getClass().getResourceAsStream("/images/icons/mute.png")));
        }
        else {
            muted=false;
            audiobutton.setImage(new Image(getClass().getResourceAsStream("/images/icons/speaker.png")));
        }
    }

    public void marketChoice(MouseEvent mouseEvent) {
        gui.changeStage("market.fxml");
    }

    public void developmentChoice(MouseEvent mouseEvent) {
        gui.changeStage("gameboard.fxml");
    }
}
