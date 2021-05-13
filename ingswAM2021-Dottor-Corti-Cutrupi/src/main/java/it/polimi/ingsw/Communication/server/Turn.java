package it.polimi.ingsw.Communication.server;

/**
 * Keeps track of the actions and productions performed this round by the player, to avoid illegal operations (such as activating hte same production twice
 *  or performing two main actions).
 */
public class Turn {
    private int actionPerformed;
    private boolean[] productions = new boolean[6];

    /**
     *
     */
    public Turn() {
        this.actionPerformed = 0;
        for(int i=0; i<6;i++)   productions[i]=false;
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
     * @param trueFalse true when the production has been successfully performed, false otherwise
     */
    public void setProductions(int index, boolean trueFalse) {
        productions[index]= trueFalse;
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
        return productions;
    }
}
