package it.polimi.ingsw.papalpath;

public class PapalPathTile {
    //this variable shows how many victory points the tile gives; if it doesnt give any, it will be set to 0
    private int victoryPoints;
    //this variable shows if this spot is part of the first (1), second (2) or third vatican report (3); 0 if it doesnt belong to any
    private int numOfReportSection;
    private boolean isPopeSpace;

    public PapalPathTile(int victoryPoints, int numOfReportSection, boolean isPopeSpace) {
        this.victoryPoints = victoryPoints;
        this.numOfReportSection = numOfReportSection;
        this.isPopeSpace = isPopeSpace;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public int getNumOfReportSection() {
        return numOfReportSection;
    }

    public boolean isPopeSpace() {
        return isPopeSpace;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public void setNumOfReportSection(int numOfReportSection) {
        this.numOfReportSection = numOfReportSection;
    }

    public void setPopeSpace(boolean popeSpace) {
        isPopeSpace = popeSpace;
    }

}
