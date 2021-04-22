package it.polimi.ingsw.papalpath;

public class PapalPathTile {
    private int victoryPoints;
    //this variable shows if this spot is part of the first (1), second (2) or third vatican report (3); 0 if it doesnt belong to any
    private int numOfReportSection;
    private boolean isPopeSpace;

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public int getNumOfReportSection() {
        return numOfReportSection;
    }

    public boolean isPopeSpace() {
        return isPopeSpace;
    }
}
