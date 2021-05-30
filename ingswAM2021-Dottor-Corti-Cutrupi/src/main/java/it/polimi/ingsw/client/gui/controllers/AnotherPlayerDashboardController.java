package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.utility.ImageSearcher;
import it.polimi.ingsw.server.messages.jsonMessages.DevelopmentCardMessage;
import it.polimi.ingsw.server.messages.jsonMessages.StrongboxMessage;
import it.polimi.ingsw.server.messages.printableMessages.YouActivatedPapalCard;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Objects;

public class AnotherPlayerDashboardController implements GUIController{

    //strongbox items
    @FXML private ImageView coinResourceStrongbox;
    @FXML private ImageView stoneResourceStrongbox;
    @FXML private ImageView servantResourceStrongbox;
    @FXML private ImageView shieldResourceStrongbox;
    @FXML private Label coinInStrongboxLabel;
    @FXML private Label stoneInStrongboxLabel;
    @FXML private Label servantInStrongboxLabel;
    @FXML private Label shieldInStrongboxLabel;



    @FXML private ImageView DevCardZone11;
    @FXML private ImageView DevCardZone12;
    @FXML private ImageView DevCardZone13;
    @FXML private ImageView DevCardZone21;
    @FXML private ImageView DevCardZone22;
    @FXML private ImageView DevCardZone23;
    @FXML private ImageView DevCardZone31;
    @FXML private ImageView DevCardZone32;
    @FXML private ImageView DevCardZone33;
    @FXML private ImageView PapalPos0;
    @FXML private ImageView PapalPos1;
    @FXML private ImageView PapalPos2;
    @FXML private ImageView PapalPos3;
    @FXML private ImageView PapalPos4;
    @FXML private ImageView PapalPos5;
    @FXML private ImageView PapalPos6;
    @FXML private ImageView PapalPos7;
    @FXML private ImageView PapalPos8;
    @FXML private ImageView PapalPos9;
    @FXML private ImageView PapalPos10;
    @FXML private ImageView PapalPos11;
    @FXML private ImageView PapalPos12;
    @FXML private ImageView PapalPos13;
    @FXML private ImageView PapalPos14;
    @FXML private ImageView PapalPos15;
    @FXML private ImageView PapalPos16;
    @FXML private ImageView PapalPos17;
    @FXML private ImageView PapalPos18;
    @FXML private ImageView PapalPos19;
    @FXML private ImageView PapalPos20;
    @FXML private ImageView PapalPos21;
    @FXML private ImageView PapalPos22;
    @FXML private ImageView PapalPos23;
    @FXML private ImageView PapalPos24;
    @FXML private ImageView PapalFavorCard1;
    @FXML private ImageView PapalFavorCard2;
    @FXML private ImageView PapalFavorCard3;
    @FXML private Button goBackButton;
    @FXML private ImageView Depot11;
    @FXML private ImageView Depot21;
    @FXML private ImageView Depot22;
    @FXML private ImageView Depot31;
    @FXML private ImageView Depot32;
    @FXML private ImageView Depot33;

    private ArrayList<ImageView> devCardZones;
    private ArrayList<ImageView> papalPath;
    private Image redCross;

    private GUI gui;

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
        devCardZones=new ArrayList<>();
        devCardZones.add(DevCardZone11);    devCardZones.add(DevCardZone12);    devCardZones.add(DevCardZone13);
        devCardZones.add(DevCardZone21);    devCardZones.add(DevCardZone22);    devCardZones.add(DevCardZone23);
        devCardZones.add(DevCardZone31);    devCardZones.add(DevCardZone32);    devCardZones.add(DevCardZone33);

        Image coinImage= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/coin.png")));
        coinResourceStrongbox.setImage(coinImage);
        Image stoneImage= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/stone.png")));
        stoneResourceStrongbox.setImage(stoneImage);
        Image servantImage= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/servant.png")));
        servantResourceStrongbox.setImage(servantImage);
        Image shieldImage= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/shield.png")));
        shieldResourceStrongbox.setImage(shieldImage);
    }


    public void goBackToDashboard(MouseEvent mouseEvent) {
        System.out.println("we set to false showing other player dashboard");
        gui.setFalseShowingOtherPlayerDashboard();
        gui.changeStage("dashboard.fxml");
    }

    public void addCardToDevCardZone(DevelopmentCardMessage message) {
        ImageSearcher imageSearcher = new ImageSearcher();
        int position= message.getLevel()+ (message.getDevCardZone()-1)*3 -1;
        String devCard= imageSearcher.getImageFromColorVictoryPoints(message.getColor(), message.getVictoryPoints());
        Image image= new Image(getClass().getResourceAsStream(devCard));
        devCardZones.get(position).setImage(image);
    }

    public void resetDashboard() {
        for(ImageView devZone: this.devCardZones){
                devZone.setImage(null);
        }
    }

    public void goToLeaderCards(MouseEvent mouseEvent) {
        gui.changeStage("anotherPlayerLeadercards.fxml");
    }

    public void activatePapalCard(YouActivatedPapalCard message) {
        int index=message.getIndex();
    }

    public void refreshStrongbox(StrongboxMessage message) {
        String coins = Integer.toString(message.getResourcesContained()[0]);
        String stones = Integer.toString(message.getResourcesContained()[1]);
        String servants = Integer.toString(message.getResourcesContained()[2]);
        String shields = Integer.toString(message.getResourcesContained()[3]);
        coinInStrongboxLabel.setText(coins);
        stoneInStrongboxLabel.setText(stones);
        servantInStrongboxLabel.setText(servants);
        shieldInStrongboxLabel.setText(shields);
    }
}
