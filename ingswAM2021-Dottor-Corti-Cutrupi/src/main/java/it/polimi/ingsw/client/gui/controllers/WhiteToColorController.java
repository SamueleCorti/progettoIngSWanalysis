package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.actions.mainActions.WhiteToColorAction;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.utility.ImageSearcher;
import it.polimi.ingsw.client.gui.utility.LeaderCardForGUI;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.server.messages.jsonMessages.SerializationConverter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class WhiteToColorController implements GUIController{

    public Button selectCardsButton;
    @FXML private Label numOfWhite;
    @FXML private ImageView leader2;
    @FXML private ImageView leader1;
    @FXML private ImageView leader3;
    @FXML private Button remove1;
    @FXML private ImageView leader4;
    @FXML private Button add1;
    @FXML private Button remove2;
    @FXML private Button add2;
    @FXML private Button remove3;
    @FXML private Button add3;
    @FXML private Button remove4;
    @FXML private Button add4;
    @FXML private Label counter1;
    @FXML private Label counter2;
    @FXML private Label counter3;
    @FXML private Label counter4;
    private GUI gui;
    private int numOfBlanks,count1, count2, count3, count4;
    private ArrayList<ImageView> cardsView= new ArrayList<>();
    private ArrayList<Button> buttons= new ArrayList<>();
    private ArrayList<Label> counters= new ArrayList<>();
    private ArrayList<LeaderCardForGUI> leaderCardForGUIS;

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
        buttons.add(add1);  buttons.add(remove1);   buttons.add(add2);  buttons.add(remove2);   buttons.add(add3);  buttons.add(remove3);
        buttons.add(add4);  buttons.add(remove4);   counters.add(counter1);     counters.add(counter2);     counters.add(counter3);     counters.add(counter4);
        cardsView.add(leader1);     cardsView.add(leader2);     cardsView.add(leader3);     cardsView.add(leader4);
        for(ImageView imageView: cardsView) imageView.setOpacity(0);
        for(Button button : buttons) {
            button.setOpacity(0);
            button.setDisable(false);
        }
        for(Label label: counters)  label.setOpacity(0);
    }

    public void initData(ArrayList<LeaderCardForGUI> cards, int numOfBlanks){
        ImageSearcher imageSearcher= new ImageSearcher();
        int size= cards.size();
        numOfWhite.setText("YOU DREW "+numOfBlanks+" WHITE MARBLES, SELECT WHICH LEADER CARDS TO ACTIVATE TO TRANSFORM THEM");
        for(int i=0; i<size; i++){
            cardsView.get(i).setImage(cards.get(i).getCardImage());     cardsView.get(i).setOpacity(1);
            counters.get(i).setOpacity(1);  counters.get(i).setText(Integer.toString(0));
            buttons.get(i*2).setOpacity(1);     buttons.get(i*2).setDisable(false);
            buttons.get(1+i*2).setOpacity(1);     buttons.get(1+i*2).setDisable(false);
        }
        count1=0;   count2=0;   count3=0;   count4=0;   this.numOfBlanks=numOfBlanks;
        leaderCardForGUIS=cards;
        selectCardsButton.setDisable(true); selectCardsButton.setOpacity(0);
    }

    public void add1(MouseEvent mouseEvent) {
        count1++;
        counter1.setText(Integer.toString(count1));
        checkNum();
    }

    public void remove1(MouseEvent mouseEvent) {
        if(count1==0)   return;
        count1--;
        counter1.setText(Integer.toString(count1));
        checkNum();
    }

    public void remove2(MouseEvent mouseEvent) {
        if(count2==0)   return;
        count2--;
        counter2.setText(Integer.toString(count2));
        checkNum();
    }

    public void add2(MouseEvent mouseEvent) {
        count2++;
        counter2.setText(Integer.toString(count2));
        checkNum();
    }

    public void remove3(MouseEvent mouseEvent) {
        if(count3==0)   return;
        count3--;
        counter3.setText(Integer.toString(count3));
        checkNum();
    }

    public void remove4(MouseEvent mouseEvent) {
        if(count4==0)   return;
        count4--;
        counter4.setText(Integer.toString(count4));
        checkNum();
    }

    public void add3(MouseEvent mouseEvent) {
        count3++;
        counter3.setText(Integer.toString(count3));
        checkNum();
    }

    public void add4(MouseEvent mouseEvent) {
        count4++;
        counter4.setText(Integer.toString(count4));
        checkNum();
    }

    public void selectCards(MouseEvent mouseEvent) {
        ArrayList<Integer> resources = getResourcesRequested();
        for(ImageView imageView: cardsView) imageView.setOpacity(0);
        for(Button button : buttons) {
            button.setOpacity(0);
            button.setDisable(false);
        }
        for(Label label: counters)  label.setOpacity(0);
        gui.sendAction(new WhiteToColorAction(resources,true));
        gui.changeStage("dashboard.fxml");
    }

    private ArrayList<Integer> getResourcesRequested() {
        SerializationConverter converter= new SerializationConverter();
        ArrayList<Integer> resourceTypes= new ArrayList<>();
        for (int i=0; i<count1;i++)
            for(int index=0; index<converter.getQuantityOfResourceFromPowerResources(leaderCardForGUIS.get(0).getSpecialPowerResources());index++)
                resourceTypes.add(converter.getResourceRelatedFromArray(leaderCardForGUIS.get(0).getSpecialPowerResources()));
        for (int i=0; i<count2;i++)
            for(int index=0; index<converter.getQuantityOfResourceFromPowerResources(leaderCardForGUIS.get(1).getSpecialPowerResources());index++)
                resourceTypes.add(converter.getResourceRelatedFromArray(leaderCardForGUIS.get(1).getSpecialPowerResources()));
        for (int i=0; i<count3;i++)
            for(int index=0; index<converter.getQuantityOfResourceFromPowerResources(leaderCardForGUIS.get(2).getSpecialPowerResources());index++)
                resourceTypes.add(converter.getResourceRelatedFromArray(leaderCardForGUIS.get(2).getSpecialPowerResources()));
        for (int i=0; i<count4;i++)
            for(int index=0; index<converter.getQuantityOfResourceFromPowerResources(leaderCardForGUIS.get(3).getSpecialPowerResources());index++)
                resourceTypes.add(converter.getResourceRelatedFromArray(leaderCardForGUIS.get(3).getSpecialPowerResources()));
        return resourceTypes;
    }

    public void checkNum(){
        if(count1+count2+count3+count4==numOfBlanks)    {
            selectCardsButton.setOpacity(1);
            selectCardsButton.setDisable(false);
        }
        else{
            selectCardsButton.setOpacity(0);
            selectCardsButton.setDisable(true);
        }
    }
}
