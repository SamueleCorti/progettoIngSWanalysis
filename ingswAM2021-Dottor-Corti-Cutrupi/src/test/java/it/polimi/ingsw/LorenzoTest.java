package it.polimi.ingsw;

import it.polimi.ingsw.LorenzoIlMagnifico.*;
import org.junit.jupiter.api.Test;

public class LorenzoTest {

    LorenzoIlMagnifico lorenzoIlMagnifico= new LorenzoIlMagnifico();

    @Test
    public void lorenzoPapalPathTest(){
        lorenzoIlMagnifico.printDeck();
        System.out.println();
        for(int i=0;i<50;i++)       {
            System.out.println();
            System.out.println("The token activated this turn is "+lorenzoIlMagnifico.nextToken());
            System.out.println();
            lorenzoIlMagnifico.playTurn();
            lorenzoIlMagnifico.printDeck();
            System.out.println("Lorenzo's papal path position is:  "+ lorenzoIlMagnifico.getFaithPosition());
            System.out.println();
        }
        System.out.println("Lorenzo's papal path position is:  "+ lorenzoIlMagnifico.getFaithPosition());
    }
}
