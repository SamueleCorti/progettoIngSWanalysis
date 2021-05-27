package it.polimi.ingsw;

import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.resource.*;
import it.polimi.ingsw.server.messages.jsonMessages.SerializationConverter;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    SerializationConverter serializationConverter = new SerializationConverter();


    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        int floatMarble;
        int representation[][] = new int[3][4];
        Market market = new Market();
        market.printMarket();
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 4; column++) {
                if (market.reresourceTypeInMarket(row, column).equals(ResourceType.Coin)) {
                    representation[row][column] = 0;
                } else if (market.reresourceTypeInMarket(row, column).equals(ResourceType.Stone)) {
                    representation[row][column] = 1;
                } else if (market.reresourceTypeInMarket(row, column).equals(ResourceType.Servant)) {
                    representation[row][column] = 2;
                } else if (market.reresourceTypeInMarket(row, column).equals(ResourceType.Shield)) {
                    representation[row][column] = 3;
                } else if (market.reresourceTypeInMarket(row, column).equals(ResourceType.Faith)) {
                    representation[row][column] = 4;
                } else if (market.reresourceTypeInMarket(row, column).equals(ResourceType.Blank)) {
                    representation[row][column] = 5;
                }
                else representation[row][column] = 25;
            }
        }
        floatMarble= serializationConverter.converter(market.getFloatingMarble());

        Resource[][] fakeMarket = new Resource[3][4];
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 4; column++) {
                switch (representation[row][column]) {
                    case 0:
                        fakeMarket[row][column] = new CoinResource();
                        break;
                    case 1:
                        fakeMarket[row][column] = new StoneResource();
                        break;
                    case 2:
                        fakeMarket[row][column] = new ServantResource();
                        break;
                    case 3:
                        fakeMarket[row][column] = new ShieldResource();
                        break;
                    case 4:
                        fakeMarket[row][column] = new FaithResource();
                        break;
                    case 5:
                        fakeMarket[row][column] = new BlankResource();
                        break;
                }
            }
        }
        Resource floatingMarble= serializationConverter.intToResource(floatMarble);

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 4; column++) {
                System.out.print(fakeMarket[row][column].getResourceType() + "\t");
            }
            System.out.println();
        }
        System.out.println("\t\t\t\t\t\t\t\t" + floatingMarble.getResourceType());
        System.out.println();
    }


}
