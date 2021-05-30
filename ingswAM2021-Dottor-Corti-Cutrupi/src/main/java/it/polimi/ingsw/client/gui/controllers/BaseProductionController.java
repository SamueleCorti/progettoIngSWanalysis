package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.actions.mainActions.productionActions.BaseProductionAction;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.model.resource.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class BaseProductionController implements GUIController{
    @FXML private Button useButton;
    @FXML private Button produceButton;
    @FXML private Label coinCount;
    @FXML private Label stoneCount;
    @FXML private Label servantCount;
    @FXML private Label shieldCount;
    private int numOfRequired;
    private int numOfProduced;
    private GUI gui;
    private int coins;
    private int stones;
    private int servants;
    private int shields;
    private ArrayList<ResourceType> resourcesUsed;
    private ArrayList<ResourceType> resourcesProduced;
    boolean isDiscarding;

    
    
    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }

    public void removeCoin(MouseEvent mouseEvent) {
        if(coins==0)    return;
        coins--;
        coinCount.setText(Integer.toString(coins));
        checkNum();
    }

    public void addCoin(MouseEvent mouseEvent) {
        coins++;
        coinCount.setText(Integer.toString(coins));
        checkNum();
    }

    public void removeStone(MouseEvent mouseEvent) {
        if (stones==0)  return;;
        stones--;
        coinCount.setText(Integer.toString(stones));
        checkNum();
    }

    public void addStone(MouseEvent mouseEvent) {
        stones++;
        coinCount.setText(Integer.toString(stones));
        checkNum();
    }

    public void removeServant(MouseEvent mouseEvent) {
        if(servants==0) return;;
        servants--;
        coinCount.setText(Integer.toString(servants));
        checkNum();
    }

    public void addServant(MouseEvent mouseEvent) {
        servants++;
        coinCount.setText(Integer.toString(servants));
        checkNum();
    }

    public void removeShield(MouseEvent mouseEvent) {
        if(shields==0)  return;
        shields--;
        coinCount.setText(Integer.toString(shields));
        checkNum();
    }

    public void addShield(MouseEvent mouseEvent) {
        shields++;
        coinCount.setText(Integer.toString(shields));
        checkNum();
    }

    public void checkNum(){
        if (coins+shields+servants+stones==numOfRequired    && isDiscarding){
            useButton.setOpacity(1);    useButton.setDisable(false);
        }
        else if(coins+shields+stones+servants==numOfProduced && !isDiscarding){
            produceButton.setDisable(false);    produceButton.setOpacity(1);
        }
    }

    public void useSelected(MouseEvent mouseEvent) {
        for(int i=0; i<coins;i++)   resourcesUsed.add(new CoinResource().getResourceType());
        for(int i=0; i<stones;i++)   resourcesUsed.add(new StoneResource().getResourceType());
        for(int i=0; i<servants;i++)   resourcesUsed.add(new ServantResource().getResourceType());
        for(int i=0; i<shields;i++)   resourcesUsed.add(new ShieldResource().getResourceType());
        coins=0;    stones=0; servants=0; shields=0;        String string= Integer.toString(0);
        coinCount.setText(string);  stoneCount.setText(string);  servantCount.setText(string);  shieldCount.setText(string);
        useButton.setOpacity(0);    useButton.setDisable(true);
        isDiscarding=false;
    }

    public void produceSelected(MouseEvent mouseEvent) {
        gui.sendAction(new BaseProductionAction(resourcesUsed,resourcesProduced));
    }

    public void newTurn(){
        String string= Integer.toString(0);
        coinCount.setText(string);      stoneCount.setText(string);     servantCount.setText(string);       shieldCount.setText(string);
        produceButton.setOpacity(0);    produceButton.setDisable(true);     useButton.setDisable(true);    useButton.setOpacity(0);
        coins=0; stones=0; servants=0; shields=0;   isDiscarding=true;
    }
}
