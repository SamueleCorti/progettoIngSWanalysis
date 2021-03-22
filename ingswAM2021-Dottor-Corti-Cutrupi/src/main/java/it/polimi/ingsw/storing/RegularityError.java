package it.polimi.ingsw.storing;

public class RegularityError extends Exception {

    public RegularityError()
    {
        super("Regularity error, ");
    }

    @Override
    public String toString() {
        return getMessage() + "your warehouse is not regular";
    }
}