package it.polimi.ingsw.exception.warehouseErrors;

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