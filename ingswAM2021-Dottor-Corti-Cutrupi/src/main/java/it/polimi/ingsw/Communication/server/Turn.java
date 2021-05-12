package it.polimi.ingsw.Communication.server;

public class Turn {
    private int actionPerformed;
    private boolean[] productions;

    public Turn() {
        this.actionPerformed = 0;
        for(int i=0; i<6;i++)   productions[i]=false;
    }

    public void setActionPerformed(int actionPerformed) {
        this.actionPerformed = actionPerformed;
    }

    public void setProductions(boolean[] productions) {
        this.productions = productions;
    }

    public int getActionPerformed() {
        return actionPerformed;
    }

    public boolean[] getProductions() {
        return productions;
    }
}
