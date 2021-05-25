package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.cli.ClientSideSocket;
import it.polimi.ingsw.client.gui.controllers.GUIController;
import it.polimi.ingsw.client.gui.controllers.ResizeHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

import static javafx.application.Application.*;

public class GUI extends Application {
    private final HashMap<String, GUIController> nameMapController = new HashMap<>();
    private ClientSideSocket connectionSocket = null;
    private boolean activeGame;
    private Scene currentScene;
    private Stage stage;
    private MediaPlayer player;
    private boolean[] actionCheckers;


    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        setup();
        this.stage = stage;
        run();
    }

    private void setup() {
        List<String> fxmList = new ArrayList<>();
        for (String path : fxmList) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + path));
            GUIController controller = loader.getController();
            controller.setGui(this);
            nameMapController.put(path, controller);
        }
       //currentScene = nameMapScene.get(MENU);
    }

    public void run() {
        stage.setTitle("Masters of renaissance");
        stage.setScene(currentScene);
        //stage.getIcons().add(new Image(getClass().getResourceAsStream("src/main/resources/images/materiale-maestri/PUNCHBOARD/calamaio.png")));
        stage.getIcons().add(new Image("images/materiale-maestri/PUNCHBOARD/calamaio.png"));
        stage.show();
        ResizeHandler resize = new ResizeHandler((Pane) currentScene.lookup("#mainPane"));
        currentScene.widthProperty().addListener(resize.getWidthListener());
        currentScene.heightProperty().addListener(resize.getHeightListener());
        /*Media pick = new Media(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("media/Epic_Battle_Speech.mp3")).toExternalForm());
        player = new MediaPlayer(pick);
        player.setAutoPlay(true);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.setVolume(25);
        player.setOnEndOfMedia(() -> {
            player.seek(Duration.ZERO);
            player.play();
        });*/
    }
}
