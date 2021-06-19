package it.polimi.ingsw.model.papalpath;

/**
 * Represents each single tile in the papal path
 */
public class PapalPathTile {
    //this variable shows how many victory points the tile gives; if it doesnt give any, it will be set to 0
    private int victoryPoints;
    //this variable shows if this spot is part of the first (1), second (2) or third vatican report (3); 0 if it doesnt belong to any
    private int numOfReportSection;
    private boolean isPopeSpace;

    /**
     * @param victoryPoints: how many points a player will get when finishing on this tile
     * @param numOfReportSection: what card can be activated from this tile, 0 if none
     * @param isPopeSpace: true if the tile can directly activate a papal favor card, false if not
     */
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
