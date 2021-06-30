package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.server.messages.gameplayMessages.ResultsMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class endGamePageController implements GUIController{


    @FXML private Label label1;@FXML private Label label2;@FXML private Label label3;@FXML private Label label4;
    private GUI gui;

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
        label1.setOpacity(0);
        label2.setOpacity(0);
        label3.setOpacity(0);
        label4.setOpacity(0);
    }

    /**
     * Shows the leaderboard at the end of the game
     */
    public void updateResults(ArrayList<String> playersInOrder,ArrayList<Integer> playersPoints) {
        ArrayList<String> resultsPlayers = playersInOrder;
        ArrayList<Integer> resultsPoints = playersPoints;
        for(int i=0;i<resultsPlayers.size();i++){
            if(i==0){
                label1.setText(resultsPlayers.get(0)+"   "+resultsPoints.get(0));
                label1.setOpacity(1);
            }
            if(i==1){
                label2.setText(resultsPlayers.get(1)+"   "+resultsPoints.get(1));
                label2.setOpacity(1);
            }
            if(i==2){
                label3.setText(resultsPlayers.get(2)+"   "+resultsPoints.get(2));
                label3.setOpacity(1);
            }
            if(i==3){
                label4.setText(resultsPlayers.get(3)+"   "+resultsPoints.get(3));
                label4.setOpacity(1);
            }
        }
    }
}
