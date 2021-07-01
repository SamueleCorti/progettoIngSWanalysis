package it.polimi.ingsw.parametersEditor;

import it.polimi.ingsw.parametersEditor.controllers.DevCardsPageController;
import it.polimi.ingsw.parametersEditor.controllers.GUIControllerFA;
import it.polimi.ingsw.parametersEditor.controllers.LeaderCardsPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.*;

/**
 * GUI of the Parameters Editor extra functionality
 */
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
    private final String LEADERPARAMETERS = "leaderCardsParametersPage.fxml";


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

        List<String> fxmList = new ArrayList<>(Arrays.asList(MAINMENU,DEVCARDS,LEADERCARDS,PAPALPATH,STANDARDPROD,DEVREQUIREMENTS,RESOURCES,LEADERPARAMETERS));
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

    /**
     * Method used to set up the first scene
     */
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

    /**
     * this method updates the values of the cards in the LeaderCardsPage's TableView
     */
    public void refreshLeaderCards() {
        LeaderCardsPageController controller = ( LeaderCardsPageController) nameToController.get(LEADERCARDS);
        controller.refreshCards();
    }

    /**
     * this method updates the values of the cards in the DevCardsPage's TableView
     */
    public void refreshDevCards() {
        DevCardsPageController controller = ( DevCardsPageController) nameToController.get(DEVCARDS);
        controller.refreshCards();
    }

    public GUIControllerFA getResourcesPageController() {
        return nameToController.get(RESOURCES);
    }


    public void setResourcesForLeaderCardsRequirement(int coins, int stones, int servants, int shields) {
        LeaderCardsPageController controller = ( LeaderCardsPageController) nameToController.get(LEADERCARDS);
        controller.setResourcesForRequirement(coins,stones,servants,shields);
    }

    /**
     * This method sets the card special power resources to a certain number of resources
     * @param coins
     * @param stones
     * @param servants
     * @param shields
     */
    public void setResourcesForLeaderCardsSpecialPower(int coins, int stones, int servants, int shields) {
        LeaderCardsPageController controller = ( LeaderCardsPageController) nameToController.get(LEADERCARDS);
        controller.setResourcesForSpecialPower(coins,stones,servants,shields);
    }


    /**
     * This method sets the card price to a certain number of resources
     * @param coins
     * @param stones
     * @param servants
     * @param shields
     */
    public void setResourcesForDevelopmentCardsPrice(int coins, int stones, int servants, int shields) {
        DevCardsPageController controller = ( DevCardsPageController) nameToController.get(DEVCARDS);
        controller.setResourcesForPrice(coins,stones,servants,shields);
        controller.refreshCards();
    }

    /**
     * This method sets the card prod requirements to a certain number of resources
     * @param coins
     * @param stones
     * @param servants
     * @param shields
     */
    public void setResourcesForDevelopmentCardsProdRequirements(int coins, int stones, int servants, int shields) {
        DevCardsPageController controller = ( DevCardsPageController) nameToController.get(DEVCARDS);
        controller.setResourcesForProdRequirements(coins,stones,servants,shields);
        controller.refreshCards();
    }

    /**
     * This method sets the card prod results to a certain number of resources
     * @param coins
     * @param stones
     * @param servants
     * @param shields
     */
    public void setResourcesForDevelopmentCardsProdResults(int coins, int stones, int servants, int shields) {
        DevCardsPageController controller = ( DevCardsPageController) nameToController.get(DEVCARDS);
        controller.setResourcesForProdResults(coins,stones,servants,shields);
        controller.refreshCards();
    }
}
