package it.polimi.ingsw.Model.LorenzoIlMagnifico;

import it.polimi.ingsw.Exceptions.BothPlayerAndLorenzoActivatePapalCardException;
import it.polimi.ingsw.Exceptions.LorenzoActivatesPapalCardException;
import it.polimi.ingsw.Exceptions.LorenzoWonTheMatch;
import it.polimi.ingsw.Model.boardsAndPlayer.GameBoard;
import it.polimi.ingsw.Model.papalpath.PapalPath;

public interface Token {
    /**
     * Lorenzo's token, each representing a different action
     */
    public void tokenEffect(PapalPath papalPath, LorenzoIlMagnifico lorenzoIlMagnifico, GameBoard gameBoard) throws LorenzoWonTheMatch, LorenzoActivatesPapalCardException, BothPlayerAndLorenzoActivatePapalCardException;
}
