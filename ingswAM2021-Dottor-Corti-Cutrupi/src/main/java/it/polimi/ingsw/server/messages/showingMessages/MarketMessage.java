package it.polimi.ingsw.server.messages.showingMessages;

import it.polimi.ingsw.adapters.Parser;
import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.server.messages.Message;

public class MarketMessage implements Message{
    int[][] representation=new int[3][4];
    int floatingMarbleRepresentation;

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
                }else if(market.reresourceTypeInMarket(row,column).equals(ResourceType.Blank)){
                    representation[row][column]=5;
                }
            }
        }
        floatingMarbleRepresentation= parseResourceToInt(market.getFloatingMarble());
    }

    public int[][] getRepresentation() {
        return representation;
    }

    public int parseResourceToInt(Resource resource){
        if(resource.getResourceType().equals(ResourceType.Coin)){
            return 0;
        }else if(resource.getResourceType().equals(ResourceType.Stone)){
            return 1;
        }else if(resource.getResourceType().equals(ResourceType.Servant)){
            return 2;
        }else if(resource.getResourceType().equals(ResourceType.Shield)){
            return 3;
        }else if(resource.getResourceType().equals(ResourceType.Faith)){
            return 4;
        }else if(resource.getResourceType().equals(ResourceType.Blank)){
            return 5;
        }

        else{
            System.out.println("There was an error in parsing the resource!");
            return 150;
        }
    }

    public int getFloatingMarbleRepresentation() {
        return floatingMarbleRepresentation;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui){
            socket.refreshMarket(this);
        }
        else {
            Parser parser = new Parser();
            System.out.println(parser.decipherMarket(this));
        }
    }
}
