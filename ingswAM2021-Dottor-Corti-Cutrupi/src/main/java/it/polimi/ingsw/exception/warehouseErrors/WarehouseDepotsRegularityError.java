package it.polimi.ingsw.exception.warehouseErrors;

/**
 * Generic exception that warns the player that his warehouse has some problems
 */
public class WarehouseDepotsRegularityError extends Exception {

    public WarehouseDepotsRegularityError()
    {
        super("Regularity error, ");
    }

    @Override
    public String toString() {
        return getMessage() + "your warehouse is not regular";
    }
}