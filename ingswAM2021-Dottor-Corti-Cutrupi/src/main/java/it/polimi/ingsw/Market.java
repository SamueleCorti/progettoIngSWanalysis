package it.polimi.ingsw;

import it.polimi.ingsw.papalpath.PapalCardCondition;
import it.polimi.ingsw.papalpath.PapalFavorCards;
import it.polimi.ingsw.resource.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Market {

    Resource[][] market= new Resource[3][4];
    Resource floatingMarble;

    public Market() {
        RNG rng = new RNG(13);
        List<Integer> list = rng.returnRNG();
        Resource resource;
        int i = 0;

        for (int riga = 0; riga < 3; riga++) {
            for (int colonna = 0; colonna < 4; colonna++) {
                int a = list.get(i);
                if (a == 1 || a == 2)   market[riga][colonna] = new CoinResource();
                if (a == 3 || a == 4)   market[riga][colonna] = new StoneResource();
                if (a == 5 || a == 6)   market[riga][colonna] = new ServantResource();
                if (a == 7 || a == 8)   market[riga][colonna] = new ShieldResource();
                if (a == 9)             market[riga][colonna] = new FaithResource();
                else if(a>9)            market[riga][colonna] = new BlankResource();
                i++;
            }
        }
        int a = list.get(i);
        if (a == 1 || a == 2) floatingMarble = new CoinResource();
        if (a == 3 || a == 4) floatingMarble = new StoneResource();
        if (a == 5 || a == 6) floatingMarble = new ServantResource();
        if (a == 7 || a == 8) floatingMarble = new ShieldResource();
        if (a == 9)           floatingMarble = new FaithResource();
        else                  floatingMarble = new BlankResource();


    }

    public void printMarket(){
        for(int colonna=0; colonna<4; colonna++){
            for(int riga=0;riga<3;riga++){
                System.out.print(market[riga][colonna].getResourceType()+"\t");
            }
            System.out.println();
        }
    }


}