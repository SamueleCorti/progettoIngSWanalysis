package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.actions.mainActions.productionActions.LeaderProductionAction;
import it.polimi.ingsw.client.actions.secondaryActions.ViewDashboardAction;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.utility.LeaderCardForGUI;
import it.polimi.ingsw.model.resource.*;
import it.polimi.ingsw.server.messages.showingMessages.SerializationConverter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;

public class LeaderCardDetailsController implements GUIController {
    private GUI gui;
    private LeaderCardForGUI card;

    @FXML private Button backButton;
    @FXML private Button buttonForProdLeader;
    @FXML private ImageView image;
    @FXML private Label specialPowerLabel;
    @FXML private Label victoryPointsLabel;
    @FXML private Label requirementsLabel;
    @FXML private ImageView coinImage;
    @FXML private ImageView stoneImage;
    @FXML private ImageView servantImage;
    @FXML private ImageView shieldImage;
    @FXML private Button addCoinButton;
    @FXML private Button removeCoinButton;
    @FXML private Button addStoneButton;
    @FXML private Button removeStoneButton;
    @FXML private Button addServantButton;
    @FXML private Button removeServantButton;
    @FXML private Button addShieldButton;
    @FXML private Label counterCoin;
    @FXML private Label stoneCounter;
    @FXML private Label servantCounter;
    @FXML private Label shieldCounter;
    @FXML private Button prodSelectedButton;
    @FXML private Button removeShieldButton;
    private int coins,stones,servants,shields, numOfProduced, index;

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void initData(LeaderCardForGUI selectedCard){
        SerializationConverter converter= new SerializationConverter();
        index=selectedCard.getCardIndex();
        this.card = selectedCard;
        this.specialPowerLabel.setText(converter.parseIntToSpecialPowerPretty(selectedCard.getSpecialPower(),selectedCard.getSpecialPowerResources()));
        this.victoryPointsLabel.setText(Integer.toString(selectedCard.getVictoryPoints()));
        if(selectedCard.isNeedsResources())     requirementsLabel.setText(converter.parseIntArrayToStringOfResourcesPretty(selectedCard.getResourcesRequired()));
        else                                    requirementsLabel.setText(converter.parseIntToDevCardRequirementPretty(selectedCard.getDevelopmentCardsRequired()));
        //this.requirementsLabel.setText(Integer.toString(selectedCard.get));
        this.image.setImage(selectedCard.getCardImage());
        if(selectedCard.getSpecialPower()!=2 || selectedCard.getStatus().equals("Inactive")){
            buttonForProdLeader.setOpacity(0);
            buttonForProdLeader.setDisable(true);
            hideItems();
        }
        else{
            for(int i=0; i<selectedCard.getSpecialPowerResources().length;i++){
                if(selectedCard.getSpecialPowerResources()[i]>0)    numOfProduced=selectedCard.getSpecialPowerResources()[i];    
            }
            hideItems();
            buttonForProdLeader.setOpacity(1);
            buttonForProdLeader.setDisable(false);
        }

    }

    public void prepareProd() {
        coinImage.setOpacity(1);    stoneImage.setOpacity(1);   servantImage.setOpacity(1);     shieldImage.setOpacity(1);
        addCoinButton.setOpacity(1);    removeCoinButton.setOpacity(1);     addServantButton.setOpacity(1);     removeServantButton.setOpacity(1);
        addShieldButton.setOpacity(1);      removeShieldButton.setOpacity(1);       addStoneButton.setOpacity(1);       removeStoneButton.setOpacity(1);
        addCoinButton.setDisable(false);    removeCoinButton.setDisable(false);      addServantButton.setDisable(false);      removeServantButton.setDisable(false);
        addShieldButton.setDisable(false);      removeShieldButton.setDisable(false);        addStoneButton.setDisable(false);       removeStoneButton.setDisable(false);
        String string= Integer.toString(0);     counterCoin.setText(string);    servantCounter.setText(string);     shieldCounter.setText(string);
        stoneCounter.setText(string);
    }

    public void hideItems() {
        coinImage.setOpacity(0);    stoneImage.setOpacity(0);   servantImage.setOpacity(0);     shieldImage.setOpacity(0);
        addCoinButton.setOpacity(0);    removeCoinButton.setOpacity(0);     addServantButton.setOpacity(0);     removeServantButton.setOpacity(0);
        addShieldButton.setOpacity(0);      removeShieldButton.setOpacity(0);       addStoneButton.setOpacity(0);       removeStoneButton.setOpacity(0);
        addCoinButton.setDisable(true);    removeCoinButton.setDisable(true);      addServantButton.setDisable(true);      removeServantButton.setDisable(true);
        addShieldButton.setDisable(true);      removeShieldButton.setDisable(true);        addStoneButton.setDisable(true);       removeStoneButton.setDisable(true);
        counterCoin.setOpacity(0);    servantCounter.setOpacity(0);     shieldCounter.setOpacity(0);       stoneCounter.setOpacity(0);
        prodSelectedButton.setDisable(true);    prodSelectedButton.setOpacity(0);
    }


    public void goBack(MouseEvent mouseEvent) throws IOException {
        if (gui.checkShowingOtherPlayerDashboard()){
            gui.changeStage("anotherPlayerLeadercards.fxml");
        }else if(gui.isGameStarted()){
            gui.changeStage("yourLeaderCards.fxml");
        }else {
            gui.changeStage("discardleadercards.fxml");
        }
    }

    public void activateProduction(MouseEvent mouseEvent) {
        prepareProd();

    }

    public void removeCoin(MouseEvent mouseEvent) {
        if(coins==0)    return;
        coins--;
        counterCoin.setText(Integer.toString(coins));
        checkNum();
    }

    public void addCoin(MouseEvent mouseEvent) {
        coins++;
        counterCoin.setText(Integer.toString(coins));
        checkNum();
    }

    public void removeStone(MouseEvent mouseEvent) {
        if (stones==0)  return;;
        stones--;
        stoneCounter.setText(Integer.toString(stones));
        checkNum();
    }

    public void addStone(MouseEvent mouseEvent) {
        stones++;
        stoneCounter.setText(Integer.toString(stones));
        checkNum();
    }

    public void removeServant(MouseEvent mouseEvent) {
        if(servants==0) return;;
        servants--;
        servantCounter.setText(Integer.toString(servants));
        checkNum();
    }

    public void addServant(MouseEvent mouseEvent) {
        servants++;
        servantCounter.setText(Integer.toString(servants));
        checkNum();
    }

    public void removeShield(MouseEvent mouseEvent) {
        if(shields==0)  return;
        shields--;
        shieldCounter.setText(Integer.toString(shields));
        checkNum();
    }

    public void addShield(MouseEvent mouseEvent) {
        shields++;
        shieldCounter.setText(Integer.toString(shields));
        checkNum();
    }

    public void checkNum() {
        if(stones+coins+shields+servants==numOfProduced){
            prodSelectedButton.setDisable(false);   prodSelectedButton.setOpacity(1);
        }
        else {
            prodSelectedButton.setOpacity(0);   prodSelectedButton.setDisable(true);
        }
    }

    public void produceSelected(MouseEvent mouseEvent) {
        ArrayList<ResourceType> resourceTypes=new ArrayList<>();
        for(int i=0; i<coins;i++)   resourceTypes.add(new CoinResource().getResourceType());
        for(int i=0; i<stones;i++)   resourceTypes.add(new StoneResource().getResourceType());
        for(int i=0; i<servants;i++)   resourceTypes.add(new ServantResource().getResourceType());
        for(int i=0; i<shields;i++)   resourceTypes.add(new ShieldResource().getResourceType());
        coins=0;    stones=0; servants=0; shields=0;        String string= Integer.toString(0);
        counterCoin.setText(string);  stoneCounter.setText(string);  servantCounter.setText(string);  shieldCounter.setText(string);
        prodSelectedButton.setOpacity(0);    prodSelectedButton.setDisable(true);
        gui.sendAction(new LeaderProductionAction(index, resourceTypes));
        hideItems();
        gui.sendAction(new ViewDashboardAction());
        gui.changeStage("dashboard.fxml");
    }
}
