package it.polimi.ingsw.adapters;

import it.polimi.ingsw.model.resource.*;
import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.server.messages.jsonMessages.DepotMessage;
import it.polimi.ingsw.server.messages.jsonMessages.MarketMessage;
import it.polimi.ingsw.server.messages.jsonMessages.PapalPathMessage;
import it.polimi.ingsw.server.messages.jsonMessages.SerializationConverter;

public class Parser {
    public String parseIntToSpecialPower(int i){
        if(i==0){
            return "discount";
        }else if(i==1){
            return "extraDeposit";
        }else if(i==2){
            return "extraProd";
        }else if(i==3){
            return "whiteToColor";
        }else {
            return "error";
        }
    }


    public String parseIntArrayToStringOfResources(int[] resources){
        String string = new String();
        if(resources[0]!=0) {
            string += resources[0];
            string += " coins, \t";
        }
        if(resources[1]!=0) {
            string += resources[1];
            string += " stones, \t";
        }
        if(resources[2]!=0) {
            string += resources[2];
            string += " servants, \t";
        }
        if(resources[3]!=0) {
            string += resources[3];
            string += " shields, \t";
        }
        if(resources[4]!=0) {
            string += resources[4];
            string += " faith, \t";
        }
        return string;
    }

    public String parseIntToColorString(int i){
        if(i==0){
            return "blue";
        }else if(i==1){
            return "green";
        }else if(i==2){
            return "yellow";
        }else if(i==3){
            return "purple";
        }else {
            return "error";
        }
    }

    public String parseIntToDevCardRequirement(int[] decks){
        String string = new String();
        if(decks[0]!=0) {
            string += decks[0];
            string += " Blue development cards level 1, \t";
        }
        if(decks[1]!=0) {
            string += decks[1];
            string += " Blue development cards level 2, \t";
        }
        if(decks[2]!=0) {
            string += decks[2];
            string += " Blue development cards level 3, \t";
        }
        if(decks[3]!=0) {
            string += decks[3];
            string += " Green development cards level 1, \t";
        }
        if(decks[4]!=0) {
            string += decks[4];
            string += " Green development cards level 2, \t";
        }
        if(decks[5]!=0) {
            string += decks[5];
            string += " Green development cards level 3, \t";
        }
        if(decks[6]!=0) {
            string += decks[6];
            string += " Yellow development cards level 1, \t";
        }
        if(decks[7]!=0) {
            string += decks[7];
            string += " Yellow development cards level 2, \t";
        }if(decks[8]!=0) {
            string += decks[8];
            string += " Yellow development cards level 3, \t";
        }
        if(decks[9]!=0) {
            string += decks[9];
            string += " Purple development cards level 1, \t";
        }
        if(decks[10]!=0) {
            string += decks[10];
            string += " Purple development cards level 2, \t";
        }
        if(decks[11]!=0) {
            string += decks[11];
            string += " Purple development cards level 3, \t";
        }
        return string;
    }

    public String decipherMarket(Message message) {
        SerializationConverter serializationConverter = new SerializationConverter();
        String string= new String("Here's the market:\n");
        MarketMessage marketMessage= (MarketMessage) message;
        Resource floatingMarble= serializationConverter.intToResource(((MarketMessage) message).getFloatingMarbleRepresentation());
        Resource[][] fakeMarket= new Resource[3][4];
        for(int row=0;row<3;row++){
            for(int column=0;column<4;column++){
                switch (marketMessage.getRepresentation()[row][column]){
                    case 0:
                        fakeMarket[row][column]= new CoinResource();
                        break;
                    case 1:
                        fakeMarket[row][column]= new StoneResource();
                        break;
                    case 2:
                        fakeMarket[row][column]= new ServantResource();
                        break;
                    case 3:
                        fakeMarket[row][column]= new ShieldResource();
                        break;
                    case 4:
                        fakeMarket[row][column]= new FaithResource();
                        break;
                    case 5:
                        fakeMarket[row][column]= new BlankResource();
                        break;
                }
            }
        }
        for(int row=0; row<3; row++){
            for(int column=0;column<4;column++){
                string+=(fakeMarket[row][column].getResourceType())+"\t";
            }
            string+="\n";
        }
        string+="\t\t\t\t\t\t\t\t"+floatingMarble.getResourceType();
        return string;
    }

    public String decipherPapalPath(Message message) {
        PapalPathMessage pathMessage= (PapalPathMessage) message;
        StringBuilder string= new StringBuilder("Here's your papal path:  (x=papal card zone, X=papal card, o=your position normally, O=your position when you're on a papal path card (or zone))\n ");
        string.append("|");
        int popeSpaceNum=0;
        for(int i=0;i<=24;i++){
            if((pathMessage.getPlayerFaithPos()!=i)){
                if(pathMessage.getPopeSpaces()[popeSpaceNum]==i) {
                    string.append("X|");
                    if(popeSpaceNum<2)  popeSpaceNum++;
                }
                else if(pathMessage.getTiles()[i]>0) string.append("x|");
                else string.append(" |");
            }
            else if(pathMessage.getPopeSpaces()[popeSpaceNum]==i) {
                string.append("O|");
                if(popeSpaceNum<2)  popeSpaceNum++;
            }
            else if(pathMessage.getTiles()[i]>0) string.append("O|");
            else string.append("o|");
        }
        string.append("\n");
        return String.valueOf(string);
    }

    public String decipherDepot(Message depotMessage){
        SerializationConverter serializationConverter = new SerializationConverter();
        DepotMessage message= (DepotMessage) depotMessage;
        StringBuilder string= new StringBuilder("Here are your depots: \n");
        if(message.getSizeOfWarehouse()==4){
            for(int i=0;i<4;i++){
                string.append(i+1).append(": ");
                if(message.getSizeOfWarehouse()>4-i){
                    for(int j=0;j<message.getDepots()[4-i-1][1];j++){
                        string.append("\t").append(serializationConverter.intToResource(message.getDepots()[4-i-1][0]).getResourceType());
                    }
                }
            }
        }
        else{
            for(int i=0;i<3;i++){
                string.append(i+1).append(": ");
                if(message.getSizeOfWarehouse()>=3-i){
                    for(int j=0;j<message.getDepots()[3-i-1][1];j++){
                        string.append("\t").append(serializationConverter.intToResource(message.getDepots()[3-i-1][0]).getResourceType());
                    }
                }
                string.append("\n");
            }
        }
        if(message.getSizeOfExtraDepots()!=0){
            string.append("You also have the following extra depots: \n");
            for(int i=0; i<message.getSizeOfExtraDepots(); i++){
                for(int j=0; j<message.getDepots()[message.getSizeOfWarehouse()+1+i][1];j++)
                    string.append("\t").append(serializationConverter.intToResource(message.getDepots()[message.getSizeOfWarehouse()+1+i][1]).getResourceType());
                string.append("\n");
            }
        }
        return string.toString();
    }
}
