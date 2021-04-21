package it.polimi.ingsw.papalpath;

public class PapalPath {

    private int faithPosition;
    private int faithPositionLorenzo=0;
    private PapalFavorCards[] cards=new PapalFavorCards[3];

    /**
     * @param playerOrder: give the third and fourth 1 as the starting faith position, 0 to the other two players
     */

    public PapalPath(int playerOrder) {
        if (playerOrder <3)     this.faithPosition = 0;
        else                    this.faithPosition = 1;
        this.cards[0]= new PapalFavorCards(8,2);
        this.cards[1]= new PapalFavorCards(16,3);
        this.cards[2]= new PapalFavorCards(24,4);
    }

    public void endGame (){
        //chiamo l'end game del game handler

    }

    /**
     *moves the player on the papal path, and, immediately after that, checks whether a meeting with the pope is in place or if the papal path is completed.
     */
    public void moveForward(){
        if(faithPosition<24)    faithPosition+=1;
        else                    return;
        int i=faithPosition/8 -1;
        if (faithPosition%8 == 0 && cards[i].getCondition().equals(CardCondition.Inactive))   {
                                        this.popeMeeting (i);
                                        //chiamo la funzione checkPosition(faithPosition, i=pos/8 +3) per gli altri giocatori
                                    }
        if (faithPosition == 24)      this.endGame();
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
    public void checkPosition(int cardID){
        if (faithPosition>(cardID+1)*8-4-cardID) this.popeMeeting(cardID);
        else this.cards[cardID].setCondition(CardCondition.Discarded);
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
        return VP+ this.getPositionVP();
    }

    /**
     * returns the victoryPoints related to the position in the papalPath
     */
    public int getPositionVP (){
        if (faithPosition<3) return 0;
        else if(faithPosition<6) return +1;
        else if (faithPosition<9) return +2;
        else if (faithPosition<12) return +4;
        else if (faithPosition<15) return +6;
        else if (faithPosition<18) return +9;
        else if (faithPosition<21) return +12;
        else if (faithPosition<24) return +16;
        else  return +20;
    }

    public PapalFavorCards[] getCards() {
        return this.cards;
    }

    public PapalFavorCards getCards(int i) {
        return this.cards[i];
    }

    /**
     * these two methods are used only by Lorenzo, to avoid calling the actions related to the papal favor cards and the normal game ending method
     * this method proceeds to call moveForwardLorenzo() a number of times equal to faithGain
     * @param faithGain: 1 or 2, it depends on wht lorenzo draws
     */

    public void moveForwardLorenzo(int faithGain){
        for(int i=0;i<faithGain;i++)    moveForwardLorenzo();
    }

    /**
     * if Lorenzo reached position 24 he wins, if he gets to a pope meeting before the player the cards gets activated/discarded following the standard method
     */
    public void moveForwardLorenzo(){
        if(faithPositionLorenzo<24)      faithPositionLorenzo++;
        if (faithPositionLorenzo==24)  lorenzoPapalWin();
        int i=faithPositionLorenzo/8 -1;
        if (faithPositionLorenzo%8 == 0 && cards[i].getCondition().equals(CardCondition.Inactive)){
            checkPosition(i);
        }
    }

    public void lorenzoPapalWin(){
        //should notify the gameHandler that Lorenzo won the game, specifically via papal path
    }
}