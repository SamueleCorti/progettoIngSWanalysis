package it.polimi.ingsw.parametersEditor;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import it.polimi.ingsw.parametersEditor.standardProdTools.StandardProdModifier;

public class StandardProdController implements GUIControllerFA {



    private StandardProdModifier standardProdModifier;

    private GUIFA gui;
    @FXML private Button goBackButton;
    @FXML private Button increaseButton1;
    @FXML private Button increaseButton2;
    @FXML private Button decreaseButton1;
    @FXML private Button decreaseButton2;
    @FXML private Label numConsumedLabel;
    @FXML private Label numProducedLabel;

    public void goBackAndSaveChanges(MouseEvent mouseEvent) {
        standardProdModifier.writeValuesInJson();
        gui.changeStage("mainMenuPage.fxml");
    }



    @Override
    public void setGui(GUIFA gui) {
        this.gui=gui;
        this.standardProdModifier = new StandardProdModifier();
        standardProdModifier.importValues();
        numConsumedLabel.setText("" + standardProdModifier.getNumOfStandardProdRequirements());
        numProducedLabel.setText("" + standardProdModifier.getNumOfStandardProdResults());
    }

    public void refreshLabels(){
        numConsumedLabel.setText("" + standardProdModifier.getNumOfStandardProdRequirements());
        numProducedLabel.setText("" + standardProdModifier.getNumOfStandardProdResults());
    }

    public void decreaseRequired(MouseEvent mouseEvent) {
        if(standardProdModifier.getNumOfStandardProdRequirements()>0) {
            standardProdModifier.decreaseNumOfStandardProdRequirements();
            refreshLabels();
        }else{
            //todo: print some kind of error
        }
    }

    public void increaseRequired(MouseEvent mouseEvent) {
        standardProdModifier.increaseNumOfStandardProdRequirements();
        refreshLabels();
    }

    public void decreaseProduced(MouseEvent mouseEvent) {
        if(standardProdModifier.getNumOfStandardProdResults()>0) {
        standardProdModifier.decreaseNumOfStandardProdResults();
        refreshLabels();
        }else{
            //todo: print some kind of error
        }
    }

    public void increaseProduced(MouseEvent mouseEvent) {
        standardProdModifier.increaseNumOfStandardProdResults();
        refreshLabels();
    }

}