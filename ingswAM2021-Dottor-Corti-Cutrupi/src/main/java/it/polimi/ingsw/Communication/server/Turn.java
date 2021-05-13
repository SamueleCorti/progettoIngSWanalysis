package it.polimi.ingsw.Communication.server;

public class Turn {
    private int actionPerformed;
    private boolean[] productions = new boolean[6];

    public Turn() {
        this.actionPerformed = 0;
        for(int i=0; i<6;i++)   productions[i]=false;
    }

    public void setActionPerformed(int actionPerformed) {
        this.actionPerformed = actionPerformed;
    }

    public void setProductions(int index, boolean trueFalse) {
        productions[index]= trueFalse;
    }

    public int getActionPerformed() {
        return actionPerformed;
    }

    public boolean[] getProductions() {
        return productions;
    }
}
