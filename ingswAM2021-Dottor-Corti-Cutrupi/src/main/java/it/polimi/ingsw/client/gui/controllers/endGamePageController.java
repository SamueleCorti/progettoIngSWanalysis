package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.server.messages.gameplayMessages.ResultsMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class endGamePageController implements GUIController{

    @FXML
    private Label finalLeaderboardLabel;
    private GUI gui;

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }


    public void updateResults(ResultsMessage message) {
        int i=1;
        String leaderboard = new String();
        for(String player: message.getPlayersInOrder()){
            leaderboard += "Position nr"+i+" :"+player+" with "+message.getPlayersPoints().get(i-1)+" victory points!";
        }
        finalLeaderboardLabel.setText(leaderboard);
    }
}
