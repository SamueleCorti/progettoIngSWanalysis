package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.cli.ClientSideSocket;
import it.polimi.ingsw.client.gui.controllers.GUIController;
import it.polimi.ingsw.client.gui.controllers.ResizeHandler;
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
    private ClientSideSocket connectionSocket = null;
    private boolean activeGame;
    private Scene currentScene;
    private Stage stage;
    private MediaPlayer player;
    private boolean[] actionCheckers;
    private final String STARTING_MENU= "startingMenu.fxml";


    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {setup();
        this.stage = stage;
        run();
    }

    private void setup() {
        List<String> fxmList = new ArrayList<>(Arrays.asList(STARTING_MENU));
        try {
            for (String path : fxmList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + path));
                Parent root = (Parent) loader.load();
                GUIController controller = loader.getController();
                controller.setGui(this);
                nameToScene.put(path,new Scene(root));
                nameToController.put(path, controller);
            }
            currentScene = nameToScene.get(STARTING_MENU);
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
}
