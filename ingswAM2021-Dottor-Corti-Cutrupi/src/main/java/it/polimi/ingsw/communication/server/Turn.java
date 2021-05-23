package it.polimi.ingsw.communication.server;

public class Turn {
    private int actionPerformed=0;
    private boolean[] productions;

    /**
     *Constructor, sets actionPerformed to 0, and the whole of the productions array to false.
     */
    public Turn(int numOfLeaderCardsKept) {
        this.productions = new boolean[4+numOfLeaderCardsKept];
        this.actionPerformed = 0;
        for(int i=0; i<productions.length;i++)   productions[i]=false;
    }

    /**
     * Keeps track of the fact that a main action has been performed
     * @param actionPerformed: 1 if the action was either developing or getting resources from market, 2 if it was a production
     */
    public void setActionPerformed(int actionPerformed) {
        this.actionPerformed = actionPerformed;
    }

    /**
     * Keeps track of the productions performed this turn.
     * @param index [ 0:basic | 1-2: leader cards production | 3-5 dev cards ]
     */
    public void setProductionPerformed(int index) {
        productions[index]= true;
    }


    /**
     * @return : 0 if no main actions have been performed by the player this turn;
     * 1 if the action was either developing or getting resources from market;
     *  2 if it was a production
     */
    public int getActionPerformed() {
        return actionPerformed;
    }

    /**
     * @return: The array representing the action performed this turn. [ 0:basic production| 1-2: leader cards production | 3-5 dev cards production]
     */
    public boolean[] getProductions() {
        boolean[] temp = productions;
        return temp;
    }

    /**
     * Sets all production to false.
     */
    public void resetProductions(){
        for(int i=0;i<productions.length;i++)
            productions[i]=false;
    }
}