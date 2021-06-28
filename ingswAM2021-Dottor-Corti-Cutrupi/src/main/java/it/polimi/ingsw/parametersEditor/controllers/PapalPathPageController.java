package it.polimi.ingsw.parametersEditor.controllers;

import it.polimi.ingsw.parametersEditor.GUIFA;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import it.polimi.ingsw.parametersEditor.papalPathTools.PapalPathModifier;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PapalPathPageController implements GUIControllerFA {

    @FXML private TextField VP0,VP1,VP2,VP3,VP4,VP5,VP6,VP7,VP8,VP9,VP10,VP11,VP12,VP13,VP14,VP15,VP16,VP17,VP18,VP19,VP20,VP21,VP22,VP23,VP24;
    @FXML private TextField ZoneStart1, ZoneStart2, ZoneStart3, ZoneFinish1, ZoneFinish2, ZoneFinish3, VictoryPointsCard1, VictoryPointsCard2, VictoryPointsCard3;
    private GUIFA gui;
    @FXML private Button goBackButton, applyChangesButton;
    private ArrayList<TextField> victoryPoints= new ArrayList<>();
    private PapalPathModifier papalPathPageModifier;
    private ArrayList<TextField> startingZones= new ArrayList<>(),finishingZones= new ArrayList<>(), victoryPointsCards= new ArrayList<>();


    public void goBack(MouseEvent mouseEvent) {
        gui.changeStage("mainMenuPage.fxml");
    }

    @Override
    public void setGui(GUIFA gui) {
        this.gui=gui;
    }

    /**
     * method that verifies if the changes inserted are correct; if they are, it saves them into json;
     * otherwise, gives the user an alert
     * @param mouseEvent
     */
    public void applyChanges(MouseEvent mouseEvent) {
        if( victoryPointsOk() && reportSectionsOk() && cardsVictoryPointsOk()){
            saveChanges();
            papalPathPageModifier.writeCardsInJson();
            papalPathPageModifier.writeFavorCardsInJson();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Wrong configuration");
            alert.setHeaderText("The papal path changes you inserted are not correct!");
            alert.setContentText("Pleaser check that the changes you've made respect the conditions");
            alert.showAndWait();
        }
        gui.changeStage("mainMenuPage.fxml");
    }

    /**
     * method that applies the changes to the papal path modifier class
     */
    private void saveChanges() {
        for(int i=0; i<victoryPoints.size();i++)    papalPathPageModifier.setVictoryPoints(i, Integer.parseInt(victoryPoints.get(i).getText()));
        for(int i=0; i<3; i++)                      papalPathPageModifier.setFavorCardsVictoryPoints(i, Integer.parseInt(victoryPointsCards.get(i).getText()));
        boolean found=false;
        for(int i=0; i<victoryPoints.size() ;i++){
            papalPathPageModifier.changeIsPopeSpace(i,false);
            for(int numOfSection=0; numOfSection<3; numOfSection++)
                if(i>=Integer.parseInt(startingZones.get(numOfSection).getText()) && i<=Integer.parseInt(finishingZones.get(numOfSection).getText())){
                    papalPathPageModifier.changeNumOfReportSection(i,numOfSection+1);
                    if(i==Integer.parseInt(finishingZones.get(numOfSection).getText()))     papalPathPageModifier.changeIsPopeSpace(i,true);
                    found=true;
                }
            if(!found)  papalPathPageModifier.changeNumOfReportSection(i, 0);
            found=false;
        }
    }

    /**
     * method that checks if the victory points inserted are correct
     * @return
     */
    private boolean victoryPointsOk() {
        for(int i=1; i<victoryPoints.size(); i++)   {
            if(Integer.parseInt(victoryPoints.get(i).getText())<Integer.parseInt(victoryPoints.get(i-1).getText())) return false;
            else if (Integer.parseInt(victoryPoints.get(i).getText())<0) return false;
        }
        return true;
    }

    /**
     * method that checks if the report section values are correct
     * @return
     */
    private boolean reportSectionsOk(){
        for(int i=0; i<3; i++){
            if(startingZones.get(i).getText()==null || finishingZones.get(i).getText()==null)    return false;
            if (Integer.parseInt(startingZones.get(i).getText())<0 || Integer.parseInt(startingZones.get(i).getText())>24)  return false;
            if (Integer.parseInt(finishingZones.get(i).getText())<0 || Integer.parseInt(finishingZones.get(i).getText())>24)  return false;

        }

        for(int i=0; i<3;i++)
            if (Integer.parseInt(startingZones.get(i).getText())>Integer.parseInt(finishingZones.get(i).getText()))    return false;

        for(int i=1;i<3;i++){
            if (Integer.parseInt(startingZones.get(i).getText())<=Integer.parseInt(startingZones.get(i-1).getText())) return false;
            if (Integer.parseInt(finishingZones.get(i).getText())<=Integer.parseInt(finishingZones.get(i-1).getText())) return false;
            if (Integer.parseInt(startingZones.get(i).getText())<=Integer.parseInt(finishingZones.get(i-1).getText())) return false;
        }

        return true;
    }

    /**
     * methdo that checks if the favor cards victory points are correct
     * @return
     */
    private boolean cardsVictoryPointsOk(){
        for(int i=0; i<3; i++){
            if(Integer.parseInt(victoryPointsCards.get(i).getText())<0) return false;
            if(Integer.parseInt(victoryPointsCards.get(i).getText())>99) return false;
        }
        return true;
    }

    /**
     * method that initializes the page, setting the buttons and labels and importing the values from
     * the PapalPathModifier
     * @throws FileNotFoundException
     */
    public void initialize() throws FileNotFoundException {
        applyChangesButton.setDisable(false);        applyChangesButton.setOpacity(1);
        goBackButton.setOpacity(1);     goBackButton.setDisable(false);
        victoryPoints.add(VP0); victoryPoints.add(VP1); victoryPoints.add(VP2); victoryPoints.add(VP3); victoryPoints.add(VP4); victoryPoints.add(VP5);  victoryPoints.add(VP6);
        victoryPoints.add(VP7); victoryPoints.add(VP8); victoryPoints.add(VP9); victoryPoints.add(VP10); victoryPoints.add(VP11);     victoryPoints.add(VP12);
        victoryPoints.add(VP13); victoryPoints.add(VP14); victoryPoints.add(VP15);  victoryPoints.add(VP16); victoryPoints.add(VP17);      victoryPoints.add(VP18);
        victoryPoints.add(VP19); victoryPoints.add(VP20); victoryPoints.add(VP21); victoryPoints.add(VP22); victoryPoints.add(VP23);    victoryPoints.add(VP24);
        startingZones.add(ZoneStart1); startingZones.add(ZoneStart2);   startingZones.add(ZoneStart3);
        finishingZones.add(ZoneFinish1);    finishingZones.add(ZoneFinish2);    finishingZones.add(ZoneFinish3);
        victoryPointsCards.add(VictoryPointsCard1);     victoryPointsCards.add(VictoryPointsCard2);      victoryPointsCards.add(VictoryPointsCard3);

        papalPathPageModifier= new PapalPathModifier();
        papalPathPageModifier.importTiles();        papalPathPageModifier.importFavorCards();

        for(int i=0; i<papalPathPageModifier.getTileList().size(); i++)
            victoryPoints.get(i).setText(Integer.toString(papalPathPageModifier.getTileList().get(i).getVictoryPoints()));


        for(int i=0; i<papalPathPageModifier.getTileList().size(); i++)
            if(i==0 && papalPathPageModifier.getTileList().get(i).getNumOfReportSection()==1)   {
                startingZones.get(0).setText(Integer.toString(0));
                if(papalPathPageModifier.getTileList().get(1).getNumOfReportSection()==0)   finishingZones.get(0).setText(Integer.toString(1));
            }
            else if(i==24 && papalPathPageModifier.getTileList().get(i).getNumOfReportSection()!=0) finishingZones.get(2).setText(Integer.toString(24));

            else if(papalPathPageModifier.getTileList().get(i).getNumOfReportSection()!=0 &&
                    papalPathPageModifier.getTileList().get(i-1).getNumOfReportSection()!= papalPathPageModifier.getTileList().get(i).getNumOfReportSection())
                startingZones.get(papalPathPageModifier.getTileList().get(i).getNumOfReportSection()-1).setText(Integer.toString(i));

            else if(papalPathPageModifier.getTileList().get(i).getNumOfReportSection()!=0 &&
                    papalPathPageModifier.getTileList().get(i+1).getNumOfReportSection()!= papalPathPageModifier.getTileList().get(i).getNumOfReportSection())
                finishingZones.get(papalPathPageModifier.getTileList().get(i).getNumOfReportSection()-1).setText(Integer.toString(i));

        for(int i=0; i<papalPathPageModifier.getVictoryPointsOfFavorCards().size();i++){
            victoryPointsCards.get(i).setText(Integer.toString(papalPathPageModifier.getVictoryPointsOfFavorCards().get(i)));
        }
    }
}