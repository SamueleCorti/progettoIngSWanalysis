package it.polimi.ingsw.papalpath;

public class PapalPath {

    private int faithPosition;
    private PapalFavorCards[] cards=new PapalFavorCards[3];

    //constructor, inizializes 0 as the position, then inizializes the three papal favor cards
    public PapalPath() {
        this.faithPosition = 0;
        this.cards[0]= new PapalFavorCards(8,2);
        this.cards[1]= new PapalFavorCards(16,3);
        this.cards[2]= new PapalFavorCards(24,4);
    }

    public void endGame (){
        //chiamo l'end game del game handler

    }
    //immediatly after moving the method checks wheter a meeting with the pope is in place or the papal path is completed.
    public void moveForward(){
        faithPosition+=1;
        int i=faithPosition/8 -1;
        if (faithPosition%8 == 0 && cards[i].getCondition().equals(PapalCardCondition.Inactive))   {
                                        this.popeMeeting (i);
                                        //chiamo la funzione checkPosition(faithPosition, i=pos/8 +3) per gli altri giocatori
                                    }
        if (faithPosition == 24)      this.endGame();
    }
    //gets called after previous controls, sets the card as active
    public void popeMeeting(int cardID){
        cards[cardID].setCondition(PapalCardCondition.Active);
    }


    //each player, except the one who is actually playing the turn, activates this method. All cards with the same number become either active or discarded, so nobody can call a pope meeting
    //  with a card of the same ID
    public void checkPosition(int pos, int delta){
        if (faithPosition>pos-delta) this.popeMeeting(pos/8 -1);
        else this.cards[pos/8 -1].setCondition(PapalCardCondition.Discarded);
    }


    public int getFaithPosition(){
        return faithPosition;
    }


    public int getVictoryPoints(){
        int VP=0;
        //sum of VP gained from papal favor cards
        for(int i=0;i<3;i++){
            if (cards[i].getCondition().equals(PapalCardCondition.Active)){
                VP+=cards[i].getVictoryPoints();
            }
        }
        //sum of VP gained from the advancement on the papal path
        if (faithPosition<3) return VP;
        else if(faithPosition<6) return VP+1;
        else if (faithPosition<9) return VP+2;
        else if (faithPosition<12) return VP+4;
        else if (faithPosition<15) return VP+6;
        else if (faithPosition<18) return VP+9;
        else if (faithPosition<21) return VP+12;
        else if (faithPosition<24) return VP+16;
        else  return VP+20;
    }

    public PapalFavorCards[] getCards() {
        return this.cards;
    }

    public PapalFavorCards getCards(int i) {
        return this.cards[i];
    }
}