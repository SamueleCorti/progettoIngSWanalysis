package it.polimi.ingsw.exception;
/**
 * Thrown when the player activate a papal favor card
 */
public class PapalCardActivatedException extends Exception{
    private int index;

    public PapalCardActivatedException(int index){
        this.index=index;
    }

    public int getIndex() {
        return index;
    }
}
