package it.polimi.ingsw.market;

public class OutOfBoundException extends Exception{

    public OutOfBoundException()    {super("Out of bound exception, ");}

    @Override
    public String toString() {
        //return super.toString();
        return getMessage()+ "you required a line that doesn't exists";
    }
}
