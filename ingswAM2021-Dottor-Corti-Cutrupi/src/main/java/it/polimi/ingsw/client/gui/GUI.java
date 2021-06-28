package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.actions.Action;
import it.polimi.ingsw.client.gui.controllers.*;
import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.exception.NicknameAlreadyTakenException;
import it.polimi.ingsw.exception.NoGameFoundException;
import it.polimi.ingsw.client.gui.utility.LeaderCardForGUI;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.server.messages.gameplayMessages.ResultsMessage;
import it.polimi.ingsw.server.messages.gameplayMessages.ViewGameboardMessage;
import it.polimi.ingsw.server.messages.initializationMessages.BaseProdParametersMessage;
import it.polimi.ingsw.server.messages.jsonMessages.*;
import it.polimi.ingsw.server.messages.printableMessages.ActivatedLeaderCardAck;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.*;

public class GUI extends Application {
    private final HashMap<String, GUIController> nameToController = new HashMap<>();
    private final HashMap<String, Scene> nameToScene = new HashMap<>();
    private ClientSideSocket guiSideSocket = null;
    private Scene currentScene;
    private Stage stage;
    private MediaPlayer player;
    private final String CONNECTION = "connectionPage.fxml";
    private final String STARTING_MENU = "startingMenu.fxml";
    private final String CREATION = "creationPage.fxml";
    private final String JOINING = "joiningPage.fxml";
    private final String REJOINING = "rejoiningPage.fxml";
    private final String LOBBY = "lobby.fxml";
    private final String INITIALIZATION = "discardleadercards.fxml";
    private final String LCDETAILS = "leadercarddetails.fxml";
    private final String MARKET= "market.fxml";
    private final String DASHBOARD = "dashboard.fxml";
    private final String ANOTHER_PLAYER_DASHBOARD = "anotherPlayerDashboard.fxml";
    private final String YOUR_LEADER_CARDS = "yourLeaderCards.fxml";
    private final String ANOTHER_PLAYER_LEADERCARDS = "anotherPlayerLeadercards.fxml";
    private final String GAMEBOARD = "gameboard.fxml";
    private final String DCDETAILS = "devCardDetails.fxml";
    private final String BASE_PRODUCTION = "baseProduction.fxml";
    private final String WIN_PAGE = "youWonPage.fxml";
    private final String EXCEEDING_PAGE = "exceedingdepot.fxml";
    private final String EXCEEDING_RES = "exceedingresources.fxml";
    private final String DEV_PROD= "developmentCardProduction.fxml";
    private final String WHITE_TO_COLOR = "whiteToColor.fxml";
    private final String END_GAME_PAGE = "endGamePage.fxml";

    private boolean showingOtherPlayerDashboard;
    private boolean gameStarted;

    private ArrayList<String> playersNicknamesInOrder;
    private String playerNickname;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {setup();
        this.stage = stage;
        run();
    }

    /**
     * Method used to link each url to its controller, useful to modify scenes when a message arrives
     */
    private void setup() {
        List<String> fxmList = new ArrayList<>(Arrays.asList(EXCEEDING_PAGE,EXCEEDING_RES,STARTING_MENU,LCDETAILS,INITIALIZATION,LOBBY, CREATION, JOINING, REJOINING, CONNECTION,DASHBOARD,
                ANOTHER_PLAYER_DASHBOARD,YOUR_LEADER_CARDS,ANOTHER_PLAYER_LEADERCARDS,MARKET,GAMEBOARD,WHITE_TO_COLOR,
                WIN_PAGE,DCDETAILS,BASE_PRODUCTION,DEV_PROD,END_GAME_PAGE));
        try {
            for (String path : fxmList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + path));
                Parent root = (Parent) loader.load();
                GUIController controller = loader.getController();
                controller.setGui(this);
                nameToScene.put(path,new Scene(root));
                nameToController.put(path, controller);
            }
            currentScene = nameToScene.get(CONNECTION);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Method used to set up the scene with title, image and music
     */
    public void run() {
        stage.setTitle("Masters of renaissance");
        stage.setScene(currentScene);
        stage.getIcons().add(new Image("images/general/calamaio.png"));
        stage.show();
       // ResizeHandler resize = new ResizeHandler((Pane) currentScene.lookup("#mainPane"));
        //currentScene.widthProperty().addListener(resize.getWidthListener());
       // currentScene.heightProperty().addListener(resize.getHeightListener());
        Media pick = new Media(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("music/Fabritio_Caroso_Ballo_del_fiore.mp3")).toExternalForm());
        player = new MediaPlayer(pick);
        player.setAutoPlay(true);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.setVolume(25);
        player.setOnEndOfMedia(() -> {
            player.seek(Duration.ZERO);
            player.play();
        });
    }

    /**
     * Method to change music (not used at the moment, but ready for future improvements)
     * @param chosenMusic
     */
    public void changeMusic(Media chosenMusic){
        boolean wasMuted=false;
        if(player.isMute()) wasMuted=true;
        player.stop();
        player = new MediaPlayer(chosenMusic);
        if(!wasMuted) player.play();
        else player.setMute(true);
    }

    public MediaPlayer getPlayer() {
        return player;
    }

    public void changeStage(String newScene) {
        currentScene = nameToScene.get(newScene);
        stage.setScene(currentScene);
        stage.show();
    }

    public boolean activateConnection(String address, int port) throws NicknameAlreadyTakenException, NoGameFoundException {
        guiSideSocket = new ClientSideSocket(address,port,true,this);
        if(!guiSideSocket.setup()){
            System.err.println("The entered IP/port doesn't match any active server or the server is not " +
                    "running. Please try again!");
            return false;
        }
        return true;
    }

    public void sendAction(Action action){
        guiSideSocket.send(action);
    }

    public void addErrorAlert(String header, String context) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }



    public void addOkAlert(String header, String context) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }

    public void addLorenzoAlert(String header, String context) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Lorenzo made his move");
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }

    /**
     * Method called to add cards to initialization page (so the user can discard the cards he wants to)
     * @param card is one of the cards in the user hand
     */
    public void addCardToInitializationTable(LeaderCardForGUI card) {
        InitializationController controller= (InitializationController) nameToController.get(INITIALIZATION);
        controller.addCardToTableView(card);
    }

    /**
     * Method called to add a leader card to the page where all the leader cards are stored
     * @param card is the one to add
     */
    public void addCardToYourLeaderCardsList(LeaderCardForGUI card){
        YourLeaderCardsController controller = (YourLeaderCardsController) nameToController.get(YOUR_LEADER_CARDS);
        controller.addCardToTableView(card);
    }

    /**
     * Method called to add a leader card to the page where all the cards of other players are stored
     * @param card is the one to add
     */
    public void addCardToAnotherPlayerLeaderCardsTable(LeaderCardForGUI card) {
        AnotherPlayerLeaderCardsController controller = (AnotherPlayerLeaderCardsController) nameToController.get(ANOTHER_PLAYER_LEADERCARDS);
        controller.addCardToTableView(card);
    }

    public int cardsToDiscard(){
        return guiSideSocket.cardsToDiscard();
    }

    public int getOrder(){
        return guiSideSocket.getOrder();
    }

    /**
     * Method called to add a dev card to the page where all the cards are stored
     */
    public void addCardToYourDevCardZone(DevelopmentCardsInDashboard message) {
        DashboardController controller= (DashboardController) nameToController.get(DASHBOARD);
        controller.addCardToDevCardZone(message);
    }

    /**
     * Method called to add a dev card to the page where all the dev cards of another player are stored
     * @param message
     */
    public void addCardToAnotherPlayerDevCardZone(DevelopmentCardsInDashboard message) {
        AnotherPlayerDashboardController controller= (AnotherPlayerDashboardController) nameToController.get(ANOTHER_PLAYER_DASHBOARD);
        controller.addCardToDevCardZone(message);
    }

    /**
     * Used to clear the scene showing the dashboard of another player
     */
    public void resetAnotherPlayerDashboard() {
        AnotherPlayerDashboardController controller= (AnotherPlayerDashboardController) nameToController.get(ANOTHER_PLAYER_DASHBOARD);
        controller.resetDashboard();
        AnotherPlayerLeaderCardsController controller1= (AnotherPlayerLeaderCardsController) nameToController.get(ANOTHER_PLAYER_LEADERCARDS);
        controller1.removeAllCards();
    }

    public void printPapalPath(PapalPathMessage message) {
        DashboardController controller= (DashboardController) nameToController.get(DASHBOARD);
        controller.printPapalPath(message);
    }

    public void setTrueShowingOtherPlayerDashboard() {
        this.showingOtherPlayerDashboard = true;
    }

    public boolean checkShowingOtherPlayerDashboard() {
        return this.showingOtherPlayerDashboard;
    }

    public void setFalseShowingOtherPlayerDashboard() {
        this.showingOtherPlayerDashboard = false;
    }

    public void activatePapalCard(int index) {
        DashboardController controller= (DashboardController) nameToController.get(DASHBOARD);
        controller.activatePapalCard(index);
    }

    public void discardPapalCard(int index) {
        DashboardController controller= (DashboardController) nameToController.get(DASHBOARD);
        controller.discardPapalCard(index);
    }

    /**
     * Used to clear the page that stores all the leader cards
     */
    public void resetMyLeaderCards() {
        YourLeaderCardsController controller = (YourLeaderCardsController) nameToController.get(YOUR_LEADER_CARDS);
        controller.removeAllCards();
    }

    public int amountOfPlayers(){
        return guiSideSocket.getSizeOfLobby();
    }

    /**
     * Used at the beginning of the game to set the name of the user at the top of the scene and filling the choice box with
     * the names of the other players
     */
    public void setupDashboardNicknameAndChoiceBox() {
        DashboardController controller= (DashboardController) nameToController.get(DASHBOARD);
        controller.setupDashboardNicknameAndChoiceBox();
    }

    public void refreshMarket(MarketMessage message) {
        MarketController controller= (MarketController) nameToController.get(MARKET);
        controller.refreshMarket(message);
    }

    public void refreshYourDepot(DepotMessage message) {
        DashboardController controller= (DashboardController) nameToController.get(DASHBOARD);
        controller.refreshDepot(message);
    }

    public void refreshAnotherPlayerDepot(DepotMessage message) {
        AnotherPlayerDashboardController controller= (AnotherPlayerDashboardController) nameToController.get(ANOTHER_PLAYER_DASHBOARD);
        controller.refreshDepot(message);
    }

    public void setAudioInStartingMenu(boolean b) {
        StartingMenuController controller= (StartingMenuController) nameToController.get(STARTING_MENU);
        controller.setAudio(b);
    }

    public void setGameStarted(){
        this.gameStarted = true;
    }

    public boolean isGameStarted(){
        return this.gameStarted;
    }

    /**
     * Used to remove leader cards from the selected scene given their indexes
     * @param indexesToRemove idxs of the cards to remove
     */
    public void removeIndexesFromLeaderView(ArrayList<Integer> indexesToRemove) {
        YourLeaderCardsController controller = (YourLeaderCardsController) nameToController.get(YOUR_LEADER_CARDS);
        controller.removeCardsGivenIndexes(indexesToRemove);
    }


    public void refreshStrongbox(StrongboxMessage message) {
        if(!showingOtherPlayerDashboard){
            DashboardController controller= (DashboardController) nameToController.get(DASHBOARD);
            controller.refreshStrongbox(message);
        }else if(showingOtherPlayerDashboard){
            AnotherPlayerDashboardController controller= (AnotherPlayerDashboardController) nameToController.get(ANOTHER_PLAYER_DASHBOARD);
            controller.refreshStrongbox(message);
        }
    }

    public void refreshGameboard(ViewGameboardMessage message) {
        GameboardController controller = (GameboardController) nameToController.get(GAMEBOARD);
        controller.refreshGameBoard(message);
    }

    public String getPlayerNickname() {
        return playerNickname;
    }

    public void setNickname(String nicknameToSend) {
        this.playerNickname = nicknameToSend;
    }

    /**
     * Method called when a card activation works, so the card itself must be updated to activated
     * @param index
     */
    public void activateCardGivenItsIndex(int index) {
        YourLeaderCardsController controller = (YourLeaderCardsController) nameToController.get(YOUR_LEADER_CARDS);
        controller.activateCardGivenItsIndex(index);
    }

    /**
     * Used to add all the players to the client with their respective names and orders
     * @param playersNicknamesInOrder
     */
    public void addPlayersNicknamesAndOrder(ArrayList<String> playersNicknamesInOrder) {
        this.playersNicknamesInOrder = new ArrayList<String>();
        for(String playerNickname: playersNicknamesInOrder){
            this.playersNicknamesInOrder.add(playerNickname);
        }
    }

    public ArrayList<String> getPlayersNicknamesInOrder() {
        return playersNicknamesInOrder;
    }

    public void refreshGameboard(DevelopmentCardMessage message) {
        GameboardController controller = (GameboardController) nameToController.get(GAMEBOARD);
        controller.refreshGameBoard(message);
    }

    public void resetBaseProd() {
        BaseProductionController controller = (BaseProductionController) nameToController.get(BASE_PRODUCTION);
        controller.newTurn();
    }

    public void setBaseProd(BaseProdParametersMessage message) {
        BaseProductionController controller = (BaseProductionController) nameToController.get(BASE_PRODUCTION);
        controller.setNumbers(message);
    }

    public void resetDashboard() {
        DashboardController controller = (DashboardController) nameToController.get(DASHBOARD);
        controller.resetDashboard();
    }

    public void initializeExceeding(int[][] depots, int sizeOfWarehouse) {
        ExceedingDepotController controller = (ExceedingDepotController) nameToController.get(EXCEEDING_PAGE);
        controller.initializeExceeding(depots,sizeOfWarehouse);
    }

    public void initializeExceedingRes(int[][] depots, int sizeOfWarehouse) {
        ExceedingResourcesController controller = (ExceedingResourcesController) nameToController.get(EXCEEDING_RES);
        controller.initializeExceeding(depots,sizeOfWarehouse);
    }

    /**
     * Special method called when the card just activated is an extra deposit one
     * @param message
     */
    public void activateIfDepot(ActivatedLeaderCardAck message) {
        int index = message.getIndex()-1;
        YourLeaderCardsController controller1 = (YourLeaderCardsController) nameToController.get(YOUR_LEADER_CARDS);

        DashboardController controller2 = (DashboardController) nameToController.get(DASHBOARD);

        //first we check if the card we just activated was a extra depot one
        if(controller1.getPowerType(index).equals("extraDeposit")) {

            ArrayList <String> specialPowerResources = controller1.getSpecialPowerResources(index);
            //we then chek if it was a regular depot (not modified with the FA)
            if(specialPowerResources.size()==2 && specialPowerResources.get(0).equals(specialPowerResources.get(1))){
                controller2.addRegularExtraDepot(specialPowerResources);
            }else{
                //if it isn't a regular one,
                controller2.addCustomizedExtraDepot(specialPowerResources);
            }

        }

    }

    public void refreshResourcesForDevelopment(int[] resources) {
        GameboardController controller = (GameboardController) nameToController.get(GAMEBOARD);
        controller.refreshResourcesForDevelopment(resources);
    }

    public void refreshPapalPath(PapalPathMessage message) {
        AnotherPlayerDashboardController controller = (AnotherPlayerDashboardController) nameToController.get(ANOTHER_PLAYER_DASHBOARD);
        controller.printPapalPath(message);
    }

    /**
     * Method used to initialize the scene for the moment when a player has 2 white to color leader cards activated and gets resources
     * from the market
     * @param numOfBlanks
     * @param messages
     */
    public void initializeWhiteToColor(int numOfBlanks, ArrayList<LeaderCardMessage> messages) {
        WhiteToColorController controller = (WhiteToColorController) nameToController.get(WHITE_TO_COLOR);
        ArrayList<LeaderCardForGUI> cards;
        cards= new ArrayList<>();
        for(int i=0; i<messages.size(); i++){
            cards.add(new LeaderCardForGUI(messages.get(i)));
        }
        controller.initData(cards, numOfBlanks);
        //changeStage("whiteToColor.fxml");
    }

    public void setAudioInDashboard(boolean b) {
        DashboardController controller= (DashboardController) nameToController.get(DASHBOARD);
        controller.setAudio(b);
    }

    public void updateResultPage(ResultsMessage message) {
            endGamePageController controller = (endGamePageController) nameToController.get(END_GAME_PAGE);
            controller.updateResults(message);
    }
}
