package it.polimi.ingsw.parametersEditor.controllers;

import it.polimi.ingsw.parametersEditor.GUIFA;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import it.polimi.ingsw.parametersEditor.leaderCardsTools.LeaderCardForFA;
import it.polimi.ingsw.parametersEditor.leaderCardsTools.LeaderCardModifier;

public class DevelopmentRequirementPageController implements GUIControllerFA {

    @FXML private Label numBlue1;
    @FXML private Label numBlue2;
    @FXML private Label numBlue3;
    @FXML private Label numGreen1;
    @FXML private Label numGreen2;
    @FXML private Label numGreen3;
    @FXML private Label numYellow1;
    @FXML private Label numYellow2;
    @FXML private Label numYellow3;
    @FXML private Label numPurple1;
    @FXML private Label numPurple2;
    @FXML private Label numPurple3;



    private GUIFA gui;
    private LeaderCardModifier leaderCardModifier;
    private LeaderCardForFA card;


    @Override
    public void setGui(GUIFA gui) {
        this.gui = gui;
        resetLabels();
    }

    public void resetLabels(){
        numBlue1.setText("0");
        numBlue2.setText("0");
        numBlue3.setText("0");
        numGreen1.setText("0");
        numGreen2.setText("0");
        numGreen3.setText("0");
        numYellow1.setText("0");
        numYellow2.setText("0");
        numYellow3.setText("0");
        numPurple1.setText("0");
        numPurple2.setText("0");
        numPurple3.setText("0");
    }

    /**
     * this method sets up the card modifier (doesnt create a new one so that is the same as the one used in DevCardsPageController)
     * and the card to modify
     * @param leaderCardModifier
     * @param card
     */
    public void setModifierAndCard(LeaderCardModifier leaderCardModifier, LeaderCardForFA card) {
        this.leaderCardModifier = leaderCardModifier;
        this.card = card;
        resetLabels();
        int i = 0;
        for (Integer numRequired : card.getAmountOfForDevelopmentRequirement()){
            if (card.getColorsRequired().get(i).equals("blue")){
                if(card.getLevelsRequired().get(i)==1){
                    numBlue1.setText(numRequired.toString());
                }else if(card.getLevelsRequired().get(i)==2){
                    numBlue2.setText(numRequired.toString());
                }else if(card.getLevelsRequired().get(i)==3){
                    numBlue3.setText(numRequired.toString());
                }
            }else if (card.getColorsRequired().get(i).equals("green")){
                if(card.getLevelsRequired().get(i)==1){
                    numGreen1.setText(numRequired.toString());
                }else if(card.getLevelsRequired().get(i)==2){
                    numGreen2.setText(numRequired.toString());
                }else if(card.getLevelsRequired().get(i)==3){
                    numGreen3.setText(numRequired.toString());
                }
            }else if (card.getColorsRequired().get(i).equals("yellow")){
                if(card.getLevelsRequired().get(i)==1){
                    numYellow1.setText(numRequired.toString());
                }else if(card.getLevelsRequired().get(i)==2){
                    numYellow2.setText(numRequired.toString());
                }else if(card.getLevelsRequired().get(i)==3){
                    numYellow3.setText(numRequired.toString());
                }
            }else if (card.getColorsRequired().get(i).equals("purple")){
                if(card.getLevelsRequired().get(i)==1){
                    numPurple1.setText(numRequired.toString());
                }else if(card.getLevelsRequired().get(i)==2){
                    numPurple2.setText(numRequired.toString());
                }else if(card.getLevelsRequired().get(i)==3){
                    numPurple3.setText(numRequired.toString());
                }
            }
            i++;
        }
    }

    /**
     * method to go back and save changes, modifying the selected card values
     * @param mouseEvent
     */
    public void goBack(MouseEvent mouseEvent) {
        leaderCardModifier.clearDevelopmentRequirements(card.getCardIndex());
        if(Integer.parseInt(numBlue1.getText())>0){
            leaderCardModifier.addDevelopmentRequirementOfCard(card.getCardIndex(),Integer.parseInt(numBlue1.getText()),1,"blue");
        }
        if(Integer.parseInt(numBlue2.getText())>0){
            leaderCardModifier.addDevelopmentRequirementOfCard(card.getCardIndex(),Integer.parseInt(numBlue2.getText()),2,"blue");
        }
        if(Integer.parseInt(numBlue3.getText())>0){
            leaderCardModifier.addDevelopmentRequirementOfCard(card.getCardIndex(),Integer.parseInt(numBlue3.getText()),3,"blue");
        }
        if(Integer.parseInt(numGreen1.getText())>0){
            leaderCardModifier.addDevelopmentRequirementOfCard(card.getCardIndex(),Integer.parseInt(numGreen1.getText()),1,"green");
        }
        if(Integer.parseInt(numGreen2.getText())>0){
            leaderCardModifier.addDevelopmentRequirementOfCard(card.getCardIndex(),Integer.parseInt(numGreen2.getText()),2,"green");
        }
        if(Integer.parseInt(numGreen3.getText())>0){
            leaderCardModifier.addDevelopmentRequirementOfCard(card.getCardIndex(),Integer.parseInt(numGreen3.getText()),3,"green");
        }
        if(Integer.parseInt(numYellow1.getText())>0){
            leaderCardModifier.addDevelopmentRequirementOfCard(card.getCardIndex(),Integer.parseInt(numYellow1.getText()),1,"yellow");
        }
        if(Integer.parseInt(numYellow2.getText())>0){
            leaderCardModifier.addDevelopmentRequirementOfCard(card.getCardIndex(),Integer.parseInt(numYellow2.getText()),2,"yellow");
        }
        if(Integer.parseInt(numYellow3.getText())>0){
            leaderCardModifier.addDevelopmentRequirementOfCard(card.getCardIndex(),Integer.parseInt(numYellow3.getText()),3,"yellow");
        }
        if(Integer.parseInt(numPurple1.getText())>0){
            leaderCardModifier.addDevelopmentRequirementOfCard(card.getCardIndex(),Integer.parseInt(numPurple1.getText()),1,"purple");
        }
        if(Integer.parseInt(numPurple2.getText())>0){
            leaderCardModifier.addDevelopmentRequirementOfCard(card.getCardIndex(),Integer.parseInt(numPurple2.getText()),2,"purple");
        }
        if(Integer.parseInt(numPurple3.getText())>0){
            leaderCardModifier.addDevelopmentRequirementOfCard(card.getCardIndex(),Integer.parseInt(numPurple3.getText()),3,"purple");
        }
        gui.refreshLeaderCards();
        gui.changeStage("leaderCardsPage.fxml");

    }


    public void minusBlue1(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numBlue1.getText());
        temp -= 1;
        if(temp>=0) {
            numBlue1.setText("" + temp);
        }
    }

    public void plusBlue1(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numBlue1.getText());
        temp += 1;
        numBlue1.setText(""+temp);
    }

    public void minusBlue2(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numBlue2.getText());
        temp -= 1;
        if(temp>=0) {
            numBlue2.setText("" + temp);
        }
    }

    public void plusBlue2(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numBlue2.getText());
        temp += 1;
        numBlue2.setText(""+temp);
    }

    public void minusBlue3(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numBlue3.getText());
        temp -= 1;
        if(temp>=0) {
            numBlue3.setText("" + temp);
        }
    }

    public void plusBlue3(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numBlue3.getText());
        temp += 1;
        numBlue3.setText(""+temp);
    }

    public void minusGreen1(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numGreen1.getText());
        temp -= 1;
        if(temp>=0) {
            numGreen1.setText("" + temp);
        }
    }

    public void plusGreen1(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numGreen1.getText());
        temp += 1;
        numGreen1.setText(""+temp);
    }

    public void minusGreen2(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numGreen2.getText());
        temp -= 1;
        if(temp>=0) {
            numGreen2.setText("" + temp);
        }
    }

    public void plusGreen2(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numGreen2.getText());
        temp += 1;
        numGreen2.setText(""+temp);
    }

    public void minusGreen3(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numGreen3.getText());
        temp -= 1;
        if(temp>=0) {
            numGreen3.setText("" + temp);
        }
    }

    public void plusGreen3(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numGreen3.getText());
        temp += 1;
        numGreen3.setText(""+temp);
    }
    public void minusYellow1(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numYellow1.getText());
        temp -= 1;
        if(temp>=0) {
            numYellow1.setText("" + temp);
        }
    }

    public void plusYellow1(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numYellow1.getText());
        temp += 1;
        numYellow1.setText(""+temp);
    }

    public void minusYellow2(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numYellow2.getText());
        temp -= 1;
        if(temp>=0) {
            numYellow2.setText("" + temp);
        }
    }

    public void plusYellow2(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numYellow2.getText());
        temp += 1;
        numYellow2.setText(""+temp);
    }

    public void minusYellow3(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numYellow3.getText());
        temp -= 1;
        if(temp>=0) {
            numYellow3.setText("" + temp);
        }
    }

    public void plusYellow3(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numYellow3.getText());
        temp += 1;
        numYellow3.setText(""+temp);
    }

    public void plusPurple1(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numPurple1.getText());
        temp += 1;
        numPurple1.setText(""+temp);
    }

    public void minusPurple1(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numPurple1.getText());
        temp -= 1;
        if(temp>=0) {
            numPurple1.setText("" + temp);
        }
    }

    public void minusPurple2(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numPurple2.getText());
        temp -= 1;
        if(temp>=0) {
            numPurple2.setText("" + temp);
        }
    }

    public void plusPurple2(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numPurple2.getText());
        temp += 1;
        numPurple2.setText(""+temp);
    }

    public void minusPurple3(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numPurple3.getText());
        temp -= 1;
        if(temp>=0) {
            numPurple3.setText("" + temp);
        }
    }

    public void plusPurple3(MouseEvent mouseEvent) {
        int temp = Integer.parseInt(numPurple3.getText());
        temp += 1;
        numPurple3.setText(""+temp);
    }
}
