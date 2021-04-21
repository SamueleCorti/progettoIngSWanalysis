package it.polimi.ingsw.LorenzoIlMagnifico;

import it.polimi.ingsw.GameBoard;
import it.polimi.ingsw.papalpath.PapalPath;

public interface Token {
    /**
     * Lorenzo's token, each representing a different action
     */
    public void tokenEffect(PapalPath papalPath, LorenzoIlMagnifico lorenzoIlMagnifico, GameBoard gameBoard);
}
