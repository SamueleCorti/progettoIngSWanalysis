package it.polimi.ingsw.parametersEditor;

import it.polimi.ingsw.parametersEditor.GUIControllerFA;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.*;

import static javafx.application.Application.launch;

public class GUIFA extends Application {

    private final HashMap<String, GUIControllerFA> nameToController = new HashMap<>();
    private final HashMap<String, Scene> nameToScene = new HashMap<>();

    private Scene currentScene;
    private Stage stage;

    private final String MAINMENU = "mainMenuPage.fxml";
    private final String DEVCARDS = "devCardsPage.fxml";
    private final String LEADERCARDS = "leaderCardsPage.fxml";
    private final String PAPALPATH = "papalPathPage.fxml";
    private final String STANDARDPROD = "standardProdPage.fxml";
    private final String DEVREQUIREMENTS = "developmentRequirementsPage.fxml";
    private final String RESOURCES = "resourcesPage.fxml";


    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {setup();
        this.stage = stage;
        run();
    }

    private void setup() {

        List<String> fxmList = new ArrayList<>(Arrays.asList(MAINMENU,DEVCARDS,LEADERCARDS,PAPALPATH,STANDARDPROD,DEVREQUIREMENTS,RESOURCES));
        try {
            for (String path : fxmList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlForFA/" + path));
                Parent root = (Parent) loader.load();
                GUIControllerFA controller = loader.getController();
                controller.setGui(this);
                nameToScene.put(path,new Scene(root));
                nameToController.put(path, controller);
            }
            currentScene = nameToScene.get(MAINMENU);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run() {
        stage.setTitle("Parameters Editor");
        stage.setScene(currentScene);
        stage.show();
    }

    public void changeStage(String newScene) {
        currentScene = nameToScene.get(newScene);
        stage.setScene(currentScene);
        stage.show();
    }

    public GUIControllerFA getDevelopmentRequirementPageController(){
        return nameToController.get(DEVREQUIREMENTS);
    }

    public void refreshLeaderCards() {
        LeaderCardsPageController controller = ( LeaderCardsPageController) nameToController.get(LEADERCARDS);
        controller.refreshCards();
    }

    public GUIControllerFA getResourcesPageController() {
        return nameToController.get(RESOURCES);
    }

    public void setResourcesForLeaderCardsRequirement(int coins, int stones, int servants, int shields) {
        LeaderCardsPageController controller = ( LeaderCardsPageController) nameToController.get(LEADERCARDS);
        controller.setResourcesForRequirement(coins,stones,servants,shields);
    }

    public void setResourcesForLeaderCardsSpecialPower(int coins, int stones, int servants, int shields) {
        LeaderCardsPageController controller = ( LeaderCardsPageController) nameToController.get(LEADERCARDS);
        controller.setResourcesForSpecialPower(coins,stones,servants,shields);
    }

    public void refreshDevCards() {
        DevCardsPageController controller = ( DevCardsPageController) nameToController.get(DEVCARDS);
        controller.refreshCards();
    }

    public void setResourcesForDevelopmentCardsPrice(int coins, int stones, int servants, int shields) {
        DevCardsPageController controller = ( DevCardsPageController) nameToController.get(DEVCARDS);
        controller.setResourcesForPrice(coins,stones,servants,shields);
        controller.refreshCards();
    }

    public void setResourcesForDevelopmentCardsProdRequirements(int coins, int stones, int servants, int shields) {
        DevCardsPageController controller = ( DevCardsPageController) nameToController.get(DEVCARDS);
        controller.setResourcesForProdRequirements(coins,stones,servants,shields);
        controller.refreshCards();
    }

    public void setResourcesForDevelopmentCardsProdResults(int coins, int stones, int servants, int shields) {
        DevCardsPageController controller = ( DevCardsPageController) nameToController.get(DEVCARDS);
        controller.setResourcesForProdResults(coins,stones,servants,shields);
        controller.refreshCards();
    }
}
