package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.utility.DevelopmentCardForGUI;
import it.polimi.ingsw.client.gui.utility.ImageSearcher;
import it.polimi.ingsw.server.messages.gameplayMessages.ViewGameboardMessage;
import it.polimi.ingsw.server.messages.showingMessages.DevelopmentCardMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GameboardController implements GUIController{
    private GUI gui;

    @FXML private ImageView Blue1;
    @FXML private ImageView Blue2;
    @FXML private ImageView Blue3;
    @FXML private ImageView Green1;
    @FXML private ImageView Green2;
    @FXML private ImageView Green3;
    @FXML private ImageView Yellow1;
    @FXML private ImageView Yellow2;
    @FXML private ImageView Yellow3;
    @FXML private ImageView Purple1;
    @FXML private ImageView Purple2;
    @FXML private ImageView Purple3;
    @FXML private Label coinCount;
    @FXML private Label shieldCount;
    @FXML private Label stoneCount;
    @FXML private Label servantCount;
    ArrayList<ImageView> images;
    DevelopmentCardForGUI[] cards= new DevelopmentCardForGUI[12];

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
        images=new ArrayList<>();   cards=new DevelopmentCardForGUI[12];
        images.add(Blue1);  images.add(Blue2);  images.add(Blue3);  images.add(Green1);  images.add(Green2);  images.add(Green3);
        images.add(Yellow1);  images.add(Yellow2);  images.add(Yellow3);  images.add(Purple1);  images.add(Purple2);  images.add(Purple3);
    }


    public void refreshGameBoard(ViewGameboardMessage message) {
        ImageSearcher imageSearcher= new ImageSearcher();
        for(int i=0;i<message.getMessages().length;i++){
            if(message.getMessages()[i]!=null)    {
                cards[i]=new DevelopmentCardForGUI(message.getMessages()[i]);
                if(!cards[i].isWasCardModified()) {
                    String devCard = imageSearcher.getImageFromColorVictoryPoints(message.getMessages()[i].getColor(), message.getMessages()[i].getVictoryPoints());
                    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(devCard)));
                    images.get(i).setImage(image);
                }else{
                    images.get(i).setImage(new Image ((getClass().getResourceAsStream("/images/cardsFrontJPG/customdevcard.jpg"))));
                }
            }
            else{
                cards[i]=null;
                Image image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsBackJPG/leaderCardBack.jpg")));
                images.get(i).setImage(image);
            }
        }
        coinCount.setText(Integer.toString(message.getResources()[0]));
        stoneCount.setText(Integer.toString(message.getResources()[1]));
        servantCount.setText(Integer.toString(message.getResources()[2]));
        shieldCount.setText(Integer.toString(message.getResources()[3]));
    }

    public void openBlue3(MouseEvent mouseEvent) throws IOException {
        seeDetails(mouseEvent,2);
    }

    public void openBlue2(MouseEvent mouseEvent) throws IOException {
        seeDetails(mouseEvent,1);
    }

    public void openBlue1(MouseEvent mouseEvent) throws IOException {
        seeDetails(mouseEvent,0);
    }

    public void openGreen3(MouseEvent mouseEvent) throws IOException {
        seeDetails(mouseEvent,5);
    }

    public void openGreen2(MouseEvent mouseEvent) throws IOException {
        seeDetails(mouseEvent,4);
    }

    public void openGreen1(MouseEvent mouseEvent) throws IOException {
        seeDetails(mouseEvent,3);
    }

    public void openPurple3(MouseEvent mouseEvent) throws IOException {
        seeDetails(mouseEvent,11);
    }

    public void openPurple2(MouseEvent mouseEvent) throws IOException {
        seeDetails(mouseEvent,10);
    }

    public void openPurple1(MouseEvent mouseEvent) throws IOException {
        seeDetails(mouseEvent,9);
    }

    public void openYellow3(MouseEvent mouseEvent) throws IOException {
        seeDetails(mouseEvent,8);
    }

    public void openYellow2(MouseEvent mouseEvent) throws IOException {
        seeDetails(mouseEvent,7);
    }

    public void openYellow1(MouseEvent mouseEvent) throws IOException {
        seeDetails(mouseEvent,6);
    }

    public void seeDetails(MouseEvent mouseEvent, int index) throws IOException {
        if(cards[index]==null) return;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/devCardDetails.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        DevCardDetailsController controller = loader.getController();
        controller.setGui(gui);
        controller.init(cards[index]);

        Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void returnToDashboard(MouseEvent mouseEvent) {
        gui.changeStage("dashboard.fxml");
    }

    public void refreshGameBoard(DevelopmentCardMessage message) {
        ImageSearcher imageSearcher= new ImageSearcher();
        int pos= message.getColor()*3 + message.getLevel()-1;
        if(message!=null)    {
            cards[pos]=new DevelopmentCardForGUI(message);
            String devCard= imageSearcher.getImageFromColorVictoryPoints(message.getColor(), message.getVictoryPoints());
            Image image= new Image(Objects.requireNonNull(getClass().getResourceAsStream(devCard)));
            images.get(pos).setImage(image);
        }
        else{
            cards[pos]=null;
            Image image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cardsBackJPG/leaderCardBack.jpg")));
            images.get(pos).setImage(image);
        }

    }

    public void refreshResourcesForDevelopment(int[] resources) {
        coinCount.setText(Integer.toString(resources[0]));
        stoneCount.setText(Integer.toString(resources[1]));
        servantCount.setText(Integer.toString(resources[2]));
        shieldCount.setText(Integer.toString(resources[3]));
    }
}
