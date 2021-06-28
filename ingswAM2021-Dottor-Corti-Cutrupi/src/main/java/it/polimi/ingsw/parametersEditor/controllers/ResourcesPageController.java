package it.polimi.ingsw.parametersEditor.controllers;

import it.polimi.ingsw.parametersEditor.GUIFA;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import it.polimi.ingsw.parametersEditor.leaderCardsTools.LeaderCardForFA;
import it.polimi.ingsw.parametersEditor.leaderCardsTools.LeaderCardModifier;

/**
 * controller of the resources page card;
 * we can use this page in different modes, whose change where we set the parameters given by the user
 */
public class ResourcesPageController implements GUIControllerFA {


    private GUIFA gui;

    private LeaderCardModifier leaderCardModifier;
    private LeaderCardForFA card;

    // 1 = resources Requirements for leader cards , 2 = resources for special power in Leader cards, 3 = resources for dev cards card price
    //4 = resources for dev cards prod requirements, 5 = resources for dev cards prod results
    int mode;


    @FXML private Label numCoins;
    @FXML private Label numStones;
    @FXML private Label numServants;
    @FXML private Label numShields;

    @Override
    public void setGui(GUIFA gui) {
        this.gui = gui;
        resetLabels();
    }

    /**
     * depending on the mode value, we save the labels values in Leader Cards requirements (mode =1),
     * in Leader Card Special Powers (mode = 2), in Development Cards Price (mode = 3), in Development Cards
     * Prod Requirements (mode = 4), in Development Cards Prod Results (mode = 5)
     * @param mouseEvent
     */
    public void goBack(MouseEvent mouseEvent) {
        if(mode==1){
            gui.setResourcesForLeaderCardsRequirement(Integer.parseInt(numCoins.getText()),Integer.parseInt(numStones.getText()),Integer.parseInt(numServants.getText()),Integer.parseInt(numShields.getText()));
            gui.refreshLeaderCards();
            gui.changeStage("leaderCardsPage.fxml");
        }else if(mode==2){
            gui.setResourcesForLeaderCardsSpecialPower(Integer.parseInt(numCoins.getText()),Integer.parseInt(numStones.getText()),Integer.parseInt(numServants.getText()),Integer.parseInt(numShields.getText()));
            gui.refreshLeaderCards();
            gui.changeStage("leaderCardsPage.fxml");
        }else if(mode==3){
            gui.setResourcesForDevelopmentCardsPrice(Integer.parseInt(numCoins.getText()),Integer.parseInt(numStones.getText()),Integer.parseInt(numServants.getText()),Integer.parseInt(numShields.getText()));
            gui.changeStage("devCardsPage.fxml");
        }else if(mode==4){
            gui.setResourcesForDevelopmentCardsProdRequirements(Integer.parseInt(numCoins.getText()),Integer.parseInt(numStones.getText()),Integer.parseInt(numServants.getText()),Integer.parseInt(numShields.getText()));
            gui.changeStage("devCardsPage.fxml");
        }else if(mode==5){
            gui.setResourcesForDevelopmentCardsProdResults(Integer.parseInt(numCoins.getText()),Integer.parseInt(numStones.getText()),Integer.parseInt(numServants.getText()),Integer.parseInt(numShields.getText()));
            gui.changeStage("devCardsPage.fxml");
        }
    }

    /**
     * method that sets the resources labels to a certain value
     * @param numOfCoins
     * @param numOfStones
     * @param numOfServants
     * @param numOfShields
     * @param mode
     */
    public void setAmountsAndMode(int numOfCoins, int numOfStones, int numOfServants, int numOfShields,int mode) {
        numCoins.setText(""+numOfCoins);
        numStones.setText(""+numOfStones);
        numServants.setText(""+numOfServants);
        numShields.setText(""+numOfShields);
        this.mode = mode;
    }

    public void resetLabels(){
        numCoins.setText("0");
        numStones.setText("0");
        numServants.setText("0");
        numShields.setText("0");
    }

    public void minusCoins(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numCoins.getText());
        temp -= 1;
        if(temp>=0) {
            numCoins.setText("" + temp);
        }
    }

    public void plusCoins(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numCoins.getText());
        temp += 1;
        numCoins.setText(""+temp);
    }

    public void minusStones(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numStones.getText());
        temp -= 1;
        if(temp>=0) {
            numStones.setText("" + temp);
        }
    }

    public void plusStones(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numStones.getText());
        temp += 1;
        numStones.setText(""+temp);
    }

    public void minusServants(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numServants.getText());
        temp -= 1;
        if(temp>=0) {
            numServants.setText("" + temp);
        }
    }

    public void plusServants(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numServants.getText());
        temp += 1;
        numServants.setText(""+temp);
    }

    public void minusShields(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numShields.getText());
        temp -= 1;
        if(temp>=0) {
            numShields.setText("" + temp);
        }
    }

    public void plusShields(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numShields.getText());
        temp += 1;
        numShields.setText(""+temp);
    }



}
