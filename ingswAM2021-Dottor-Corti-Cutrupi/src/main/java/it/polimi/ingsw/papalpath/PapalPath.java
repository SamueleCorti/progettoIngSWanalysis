package it.polimi.ingsw.papalpath;

public class PapalPath {

    private int faithPosition;
    private PapalFavorCards[] cards=new PapalFavorCards[3];

    //constructor, initializes 0 as the position, then initializes the three papal favor cards. Could later implement the player's order to the method, to give the third and fourth
    // 1 as the starting faith position
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

    //immediately after moving the method checks whether a meeting with the pope is in place or the papal path is completed.
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

    public void moveForward(int faithGain){
        for(int i=0;i<faithGain;i++)    moveForward();
    }

    //gets called after previous controls, sets the card as active
    public void popeMeeting(int cardID){
        cards[cardID].setCondition(CardCondition.Active);
    }


    //each player, except the one who is actually playing the turn, activates this method. All cards with the same number become either active or discarded, so nobody can call a pope meeting
    //  with a card of the same ID
    public void checkPosition(int cardID){
        if (faithPosition>(cardID+1)*8-4-cardID) this.popeMeeting(cardID);
        else this.cards[cardID].setCondition(CardCondition.Discarded);
    }


    public int getFaithPosition(){
        return faithPosition;
    }


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

    //return the VP related to the position in the path
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

    //these two methods are used only by Lorenzo, to avoid calling the actions related to the papal favor cards and the normal game ending method
    public void moveForwardLorenzo(int faithGain){
        for(int i=0;i<faithGain;i++)    moveForwardLorenzo();
    }

    public void moveForwardLorenzo(){
        if(faithPosition<24)      faithPosition++;
        if (faithPosition==24)  lorenzoPapalWin();
    }

    public void lorenzoPapalWin(){
        //should notify the gameHandler that Lorenzo won the game, specifically via papal path
    }
}