package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.actions.mainActions.productionActions.BaseProductionAction;
import it.polimi.ingsw.client.actions.secondaryActions.ViewDashboardAction;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.model.resource.*;
import it.polimi.ingsw.server.messages.initializationMessages.BaseProdParametersMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class BaseProductionController implements GUIController{
    @FXML private Label messageToShow;
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
    private boolean isDiscarding;

    
    
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

    public void setNumbers(BaseProdParametersMessage message){
        numOfRequired=message.getUsed();
        numOfProduced=message.getProduced();
    }

    public void removeStone(MouseEvent mouseEvent) {
        if (stones==0)  return;;
        stones--;
        stoneCount.setText(Integer.toString(stones));
        checkNum();
    }

    public void addStone(MouseEvent mouseEvent) {
        stones++;
        stoneCount.setText(Integer.toString(stones));
        checkNum();
    }

    public void removeServant(MouseEvent mouseEvent) {
        if(servants==0) return;;
        servants--;
        servantCount.setText(Integer.toString(servants));
        checkNum();
    }

    public void addServant(MouseEvent mouseEvent) {
        servants++;
        servantCount.setText(Integer.toString(servants));
        checkNum();
    }

    public void removeShield(MouseEvent mouseEvent) {
        if(shields==0)  return;
        shields--;
        shieldCount.setText(Integer.toString(shields));
        checkNum();
    }

    public void addShield(MouseEvent mouseEvent) {
        shields++;
        shieldCount.setText(Integer.toString(shields));
        checkNum();
    }

    public void checkNum(){
        if(isDiscarding){
            if (coins+shields+servants+stones==numOfRequired){
                useButton.setOpacity(1);    useButton.setDisable(false);
            }
            else {
                useButton.setOpacity(0);    useButton.setDisable(true);
            }
        }
        else {
            if(coins+shields+stones+servants==numOfProduced){
                produceButton.setDisable(false);    produceButton.setOpacity(1);
            }
            else{
                produceButton.setDisable(true);    produceButton.setOpacity(0);
            }
        }
    }

    public void useSelected(MouseEvent mouseEvent) {
        resourcesUsed=new ArrayList<>();
        for(int i=0; i<coins;i++)   resourcesUsed.add(new CoinResource().getResourceType());
        for(int i=0; i<stones;i++)   resourcesUsed.add(new StoneResource().getResourceType());
        for(int i=0; i<servants;i++)   resourcesUsed.add(new ServantResource().getResourceType());
        for(int i=0; i<shields;i++)   resourcesUsed.add(new ShieldResource().getResourceType());
        coins=0;    stones=0; servants=0; shields=0;        String string= Integer.toString(0);
        coinCount.setText(string);  stoneCount.setText(string);  servantCount.setText(string);  shieldCount.setText(string);
        useButton.setOpacity(0);    useButton.setDisable(true);
        isDiscarding=false;
        messageToShow.setText("Select "+numOfProduced+" resources to produce");
    }

    public void produceSelected() {
        resourcesProduced=new ArrayList<>();
        for(int i=0; i<coins;i++)   resourcesProduced.add(new CoinResource().getResourceType());
        for(int i=0; i<stones;i++)   resourcesProduced.add(new StoneResource().getResourceType());
        for(int i=0; i<servants;i++)   resourcesProduced.add(new ServantResource().getResourceType());
        for(int i=0; i<shields;i++)   resourcesProduced.add(new ShieldResource().getResourceType());
        gui.sendAction(new BaseProductionAction(resourcesUsed,resourcesProduced));
        gui.sendAction(new ViewDashboardAction());
        newTurn();
    }

    public void newTurn(){
        String string= Integer.toString(0);     isDiscarding=true;
        coinCount.setText(string);      stoneCount.setText(string);     servantCount.setText(string);       shieldCount.setText(string);
        produceButton.setOpacity(0);    produceButton.setDisable(true);     useButton.setDisable(true);    useButton.setOpacity(0);
        coins=0; stones=0; servants=0; shields=0;   isDiscarding=true;
        messageToShow.setText("Select "+numOfRequired+" resources to discard");
    }

    public void returnToDashboard(MouseEvent mouseEvent) {
        newTurn();
        gui.changeStage("dashboard.fxml");
    }
}
