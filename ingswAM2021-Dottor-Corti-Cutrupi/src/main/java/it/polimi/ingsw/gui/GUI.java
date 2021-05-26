package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.actions.Action;
import it.polimi.ingsw.client.cli.ClientSideSocket;
import it.polimi.ingsw.gui.controllers.GUIController;
import it.polimi.ingsw.gui.controllers.ResizeHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private boolean activeGame;
    private Scene currentScene;
    private Stage stage;
    private MediaPlayer player;
    private boolean[] actionCheckers;
    private final String CONNECTION_PAGE = "connectionPage.fxml";
    private final String STARTING_MENU = "startingMenu.fxml";
    private final String CREATION_PAGE = "creationPage.fxml";
    private final String JOINING_PAGE = "joiningPage.fxml";
    private final String REJOINING_PAGE = "rejoiningPage.fxml";
    private final String LOBBY = "lobby.fxml";


    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {setup();
        this.stage = stage;
        run();
    }

    private void setup() {
        List<String> fxmList = new ArrayList<>(Arrays.asList(STARTING_MENU,LOBBY,CREATION_PAGE,JOINING_PAGE,REJOINING_PAGE,CONNECTION_PAGE));
        try {
            for (String path : fxmList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + path));
                Parent root = (Parent) loader.load();
                GUIController controller = loader.getController();
                controller.setGui(this);
                nameToScene.put(path,new Scene(root));
                nameToController.put(path, controller);
            }
            currentScene = nameToScene.get(CONNECTION_PAGE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run() {
        stage.setTitle("Masters of renaissance");
        stage.setScene(currentScene);
        stage.getIcons().add(new Image("images/general/calamaio.png"));
        stage.show();
        ResizeHandler resize = new ResizeHandler((Pane) currentScene.lookup("#mainPane"));
        currentScene.widthProperty().addListener(resize.getWidthListener());
        currentScene.heightProperty().addListener(resize.getHeightListener());
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

    public boolean activateConnection(String address, int port){
        guiSideSocket = new GuiSideSocket(address,port,this);
        if(!guiSideSocket.setup()){
            System.err.println("The entered IP/port doesn't match any active server or the server is not " +
                    "running. Please try again!");
            GUI.main(null);
        }
        return true;
    }

    public void sendAction(Action action){
        guiSideSocket.send(action);
    }
}
