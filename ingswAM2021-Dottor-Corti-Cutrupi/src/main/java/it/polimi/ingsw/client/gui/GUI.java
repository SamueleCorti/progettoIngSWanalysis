package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.actions.Action;
import it.polimi.ingsw.client.gui.controllers.*;
import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.exception.NicknameAlreadyTakenException;
import it.polimi.ingsw.exception.NoGameFoundException;
import it.polimi.ingsw.client.gui.utility.LeaderCardForGUI;
import it.polimi.ingsw.server.messages.gameplayMessages.ViewGameboardMessage;
import it.polimi.ingsw.server.messages.jsonMessages.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
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
    private final String CHOOSE_ACTION = "actionChoice.fxml";
    private final String GAMEBOARD = "gameboard.fxml";
    private final String DCDETAILS = "devCardDetails.fxml";
    private final String BASE_PRODUCTION = "baseProduction.fxml";

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

    private void setup() {
        List<String> fxmList = new ArrayList<>(Arrays.asList(STARTING_MENU,LCDETAILS,INITIALIZATION,LOBBY, CREATION, JOINING, REJOINING, CONNECTION,DASHBOARD,
                ANOTHER_PLAYER_DASHBOARD,YOUR_LEADER_CARDS,ANOTHER_PLAYER_LEADERCARDS,CHOOSE_ACTION,MARKET,GAMEBOARD,DCDETAILS,BASE_PRODUCTION));
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

    public MediaPlayer getPlayer() {
        return player;
    }

    public void changeStage(String newScene) {
        currentScene = nameToScene.get(newScene);
        stage.setScene(currentScene);
        stage.show();
        /*ResizeHandler resize = new ResizeHandler((Pane) currentScene.lookup("#mainPane"));
        currentScene.widthProperty().addListener(resize.getWidthListener());
        currentScene.heightProperty().addListener(resize.getHeightListener());*/
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
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Success");
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }

    public void addCardToInitializationTable(LeaderCardForGUI card) {
        InitializationController controller= (InitializationController) nameToController.get(INITIALIZATION);
        controller.addCardToTableView(card);
    }

    public void addCardToYourLeaderCardsList(LeaderCardForGUI card){
        YourLeaderCardsController controller = (YourLeaderCardsController) nameToController.get(YOUR_LEADER_CARDS);
        controller.addCardToTableView(card);
    }

    public void addCardToMyLeaderCardsTable(LeaderCardForGUI card) {
        YourLeaderCardsController controller = (YourLeaderCardsController) nameToController.get(YOUR_LEADER_CARDS);
        controller.addCardToTableView(card);
    }

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


    public void addCardToYourDevCardZone(DevelopmentCardMessage message) {
        DashboardController controller= (DashboardController) nameToController.get(DASHBOARD);
        controller.addCardToDevCardZone(message);
    }

    public void addCardToAnotherPlayerDevCardZone(DevelopmentCardMessage message) {
        AnotherPlayerDashboardController controller= (AnotherPlayerDashboardController) nameToController.get(ANOTHER_PLAYER_DASHBOARD);
        controller.addCardToDevCardZone(message);
    }

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

    public void resetMyLeaderCards() {
        YourLeaderCardsController controller = (YourLeaderCardsController) nameToController.get(YOUR_LEADER_CARDS);
        controller.removeAllCards();
    }

    public int amountOfPlayers(){
        return guiSideSocket.getSizeOfLobby();
    }

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

    public void activateCardGivenItsIndex(int index) {
        YourLeaderCardsController controller = (YourLeaderCardsController) nameToController.get(YOUR_LEADER_CARDS);
        controller.activateCardGivenItsIndex(index);
    }

    public void addPlayersNicknamesAndOrder(ArrayList<String> playersNicknamesInOrder) {
        this.playersNicknamesInOrder = new ArrayList<String>();
        for(String playerNickname: playersNicknamesInOrder){
            this.playersNicknamesInOrder.add(playerNickname);
        }
    }

    public ArrayList<String> getPlayersNicknamesInOrder() {
        return playersNicknamesInOrder;
    }

}
