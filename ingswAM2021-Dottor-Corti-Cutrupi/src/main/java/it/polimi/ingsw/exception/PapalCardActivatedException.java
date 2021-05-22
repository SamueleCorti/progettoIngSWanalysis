package it.polimi.ingsw.exception;

public class PapalCardActivatedException extends Exception{
    private int index;

    public PapalCardActivatedException(int index){
        this.index=index;
    }

    public int getIndex() {
        return index;
    }
}
