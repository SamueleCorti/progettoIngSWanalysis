package it.polimi.ingsw.Model.papalpath;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.Exceptions.LorenzoWonTheMatch;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class PapalPath {

    private int faithPosition;
    private int faithPositionLorenzo=0;
    private PapalFavorCard[] cards=new PapalFavorCard[3];
    private ArrayList<PapalPathTile> papalPath;


    /**
     * @param playerOrder: give the third and fourth 1 as the starting faith position, 0 to the other two players
     */

    public PapalPath(int playerOrder){
        if (playerOrder <3 || playerOrder==100)     this.faithPosition = 0;
        else                    this.faithPosition = 1;
        papalPath = new ArrayList<PapalPathTile>();

        //part where we import all the papal path tiles from json
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader("ingswAM2021-Dottor-Corti-Cutrupi/papalpathtiles.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JsonParser parser = new JsonParser();
        JsonArray tilesArray = parser.parse(reader).getAsJsonArray();
        for(JsonElement jsonElement : tilesArray) {
            Gson gson = new Gson();
            PapalPathTile tileRecreated = gson.fromJson(jsonElement.getAsJsonObject(), PapalPathTile.class);
            this.papalPath.add(tileRecreated);
        }

        JsonReader reader1 = null;
        try {
            reader1 = new JsonReader(new FileReader("ingswAM2021-Dottor-Corti-Cutrupi/favorcards.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JsonParser parser1 = new JsonParser();
        JsonArray favorArray = parser1.parse(reader1).getAsJsonArray();
        Gson gson1 = new Gson();
        int[] arr = gson1.fromJson(favorArray, int[].class);

        int cardIndex=0;
        for(int i=0; i<=24;i++){
            if (papalPath.get(i).isPopeSpace()) {
                cards[cardIndex]= new PapalFavorCard(i,arr[cardIndex]);
                cardIndex++;
            }
        }
    }

    public void endGame (){
        System.out.println("Game finished, papal path completed!");
        //chiamo l'end game del game handler
    }

    /**
     *moves the player on the papal path, and, immediately after that, checks whether a meeting with the pope is in place or if the papal path is completed.
     */
    public int moveForward(){
        this.faithPosition+=1;
        if (papalPath.get(faithPosition).isPopeSpace() &&
                cards[papalPath.get(faithPosition-1).getNumOfReportSection()-1].getCondition().equals(CardCondition.Inactive)){
            popeMeeting(papalPath.get(faithPosition).getNumOfReportSection()-1);
            return papalPath.get(faithPosition).getNumOfReportSection()-1;
        }
        return -1;
    }

    public int getFaithPositionLorenzo() {
        return faithPositionLorenzo;
    }

    /**
     * @param faithGain: number of times the base move forward method gets called
     */
    public void moveForward(int faithGain){
        for(int i=0;i<faithGain;i++)    moveForward();
    }

    //gets called after previous controls, sets the card as active
    public void popeMeeting(int cardID){
        cards[cardID].setCondition(CardCondition.Active);
    }


    /**
     * each player, except the one who is actually playing the turn, activates this method. All cards with the same index get checked, and get set to either 'active'
     *or 'discarded' so nobody can call a pope meeting that has already been called
     * @param cardID: it indicates whether the player has done a vatican report for the 1st, snd or 3rd time
     */
    public int checkPosition(int cardID){
        if (papalPath.get(faithPosition).getNumOfReportSection()-1==cardID && //essential check, we don't want discarded cards to get activated by error
                cards[papalPath.get(faithPosition).getNumOfReportSection()-1].getCondition().equals(CardCondition.Inactive)){
            popeMeeting(cardID);
            return cardID+1;
        }
        else    this.cards[cardID].setCondition(CardCondition.Discarded);
        return 0;
    }


    public int getFaithPosition(){
        return faithPosition;
    }

    /**
     * @return the victory points related to the papal path, considering both the track and the cards
     */
    public int getVictoryPoints(){
        int VP=0;
        //sum of VP gained from papal favor cards
        for(int i=0;i<3;i++){
            if (cards[i].getCondition().equals(CardCondition.Active)){
                VP+=cards[i].getVictoryPoints();
            }
        }
        return VP+ papalPath.get(faithPosition).getVictoryPoints();
    }

    public PapalFavorCard[] getCards() {
        return this.cards;
    }

    public PapalFavorCard getCards(int i) {
        return this.cards[i];
    }

    /**
     * these two methods are used only by Lorenzo, to avoid calling the actions related to the papal favor cards and the normal game ending method
     * this method proceeds to call moveForwardLorenzo() a number of times equal to faithGain
     * @param faithGain: 1 or 2, it depends on wht lorenzo draws
     */

    public void moveForwardLorenzo(int faithGain) throws LorenzoWonTheMatch {
        for(int i=0;i<faithGain;i++)    moveForwardLorenzo();
    }

    /**
     * if Lorenzo reached position 24 he wins, if he gets to a pope meeting before the player the cards gets activated/discarded following the standard method
     */
    public void moveForwardLorenzo() throws LorenzoWonTheMatch {
        faithPositionLorenzo++;
        if(faithPositionLorenzo>=24)  throw new LorenzoWonTheMatch();
        if (papalPath.get(faithPositionLorenzo).isPopeSpace() &&
                cards[papalPath.get(faithPosition).getNumOfReportSection()].getCondition().equals(CardCondition.Inactive)){
            cards[papalPath.get(faithPosition).getNumOfReportSection()].setCondition(CardCondition.Discarded);
        }
    }

    public void lorenzoPapalWin(){
        //should notify the gameHandler that Lorenzo won the game, specifically via papal path
    }

    public ArrayList<Integer> cardsActivated(){
        ArrayList<Integer> cardsActivated= new ArrayList<>();
        for(int i=0;i<3;i++){
            if (cards[i].getCondition()==CardCondition.Active)  cardsActivated.add(i);
        }
        if (cardsActivated!=null && cardsActivated.size()>0) return cardsActivated;
        else return null;
    }

    public int getNextCardToActivatePosition(){
        for(int i=0;i<3;i++){
            if(cards[i].getCondition()==CardCondition.Inactive)
                return cards[i].getFaithPosition();
        }
        return 0;
    }

    @Override
    public String toString() {
        return "PapalPath{" +
                "faithPosition=" + faithPosition +
                ", faithPositionLorenzo=" + faithPositionLorenzo +
                ", cards=" + Arrays.toString(cards) +
                ", papalPath=" + papalPath +
                '}';
    }
}