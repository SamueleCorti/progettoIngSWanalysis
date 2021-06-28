package it.polimi.ingsw.parametersEditor.papalPathTools;

/**
 * this is similar to the PapalPathTile class, adapted to be used in GUI
 */
public class PapalPathTileFA {
    //this variable shows how many victory points the tile gives; if it doesnt give any, it will be set to 0
    private int victoryPoints;
    //this variable shows if this spot is part of the first (1), second (2) or third vatican report (3); 0 if it doesnt belong to any
    private int numOfReportSection;
    private boolean isPopeSpace;

    public PapalPathTileFA(int victoryPoints, int numOfReportSection, boolean isPopeSpace) {
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

    @Override
    public String toString() {
        return "PapalPathTile{" +
                "victoryPoints=" + victoryPoints +
                ", numOfReportSection=" + numOfReportSection +
                ", isPopeSpace=" + isPopeSpace +
                '}';
    }
}
