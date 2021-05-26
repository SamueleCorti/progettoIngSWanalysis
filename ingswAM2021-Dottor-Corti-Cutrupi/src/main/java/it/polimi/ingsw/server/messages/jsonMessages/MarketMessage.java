package it.polimi.ingsw.server.messages.jsonMessages;

import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.server.messages.Message;

public class MarketMessage implements Message{
    int[][] representation=new int[3][4];

    public MarketMessage(Market market) {
        for(int row=0;row<3;row++){
            for(int column=0; column<4;column++){
                if(market.reresourceTypeInMarket(row,column).equals(ResourceType.Coin)){
                    representation[row][column]=0;
                }else if(market.reresourceTypeInMarket(row,column).equals(ResourceType.Stone)){
                    representation[row][column]=1;
                }else if(market.reresourceTypeInMarket(row,column).equals(ResourceType.Servant)){
                    representation[row][column]=2;
                }else if(market.reresourceTypeInMarket(row,column).equals(ResourceType.Shield)){
                    representation[row][column]=3;
                }else if(market.reresourceTypeInMarket(row,column).equals(ResourceType.Faith)){
                    representation[row][column]=4;
                }
            }
        }
    }

    public int[][] getRepresentation() {
        return representation;
    }
}
