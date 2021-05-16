package it.polimi.ingsw.Model.LorenzoIlMagnifico;

import it.polimi.ingsw.Model.boardsAndPlayer.GameBoard;
import it.polimi.ingsw.Model.papalpath.PapalPath;

public interface Token {
    /**
     * Lorenzo's token, each representing a different action
     */
    public void tokenEffect(PapalPath papalPath, LorenzoIlMagnifico lorenzoIlMagnifico, GameBoard gameBoard);
}
