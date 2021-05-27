package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.actions.Action;
import it.polimi.ingsw.exception.NicknameAlreadyTakenException;
import it.polimi.ingsw.exception.NoGameFoundException;
import it.polimi.ingsw.gui.controllers.GUIController;
import it.polimi.ingsw.gui.controllers.InitializationController;
import it.polimi.ingsw.gui.controllers.ResizeHandler;
import it.polimi.ingsw.server.messages.Message;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.*;

public class GUI extends Application {
    private final HashMap<String, GUIController> nameToController = new HashMap<>();
    private final HashMap<String, Scene> nameToScene = new HashMap<>();
    private GuiSideSocket guiSideSocket = null;
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


    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {setup();
        this.stage = stage;
        run();
    }

    private void setup() {
        List<String> fxmList = new ArrayList<>(Arrays.asList(STARTING_MENU,LCDETAILS,INITIALIZATION,LOBBY, CREATION, JOINING, REJOINING, CONNECTION));
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
        guiSideSocket = new GuiSideSocket(address,port,this);
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

    public void addAlert(String header, String context) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }

    public void addCardToTable(LeaderCardForGUI card) {
        InitializationController controller= (InitializationController) nameToController.get(INITIALIZATION);
        controller.addCardToTableView(card);
    }

    public int cardsToDiscard(){
        return guiSideSocket.cardsToDiscard();
    }

    public int getOrder(){
        return guiSideSocket.getOrder();
    }
}
