package it.polimi.ingsw.parametersEditor.leaderCardsTools;

import java.util.ArrayList;

public class SerializationConverter {


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

    public ArrayList<String> parseResourcesIntArrayToArrayOfStrings(int resources[]){
        ArrayList <String> stringArray = new ArrayList<String>();
        int i;
        for( i = 0 ; i<resources[0] ; i++){
            stringArray.add("coin");
        }
        for( i = 0 ; i<resources[1] ; i++){
            stringArray.add("stone");
        }
        for( i = 0 ; i<resources[2] ; i++){
            stringArray.add("servant");
        }
        for( i = 0 ; i<resources[3] ; i++){
            stringArray.add("shield");
        }
        return stringArray;
    }

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


    public String parseIntToDevCardRequirementPretty(int[] decks){
        String string = new String();
        if(decks[0]!=0) {
            string += decks[0];
            string += " Blue lvl 1 \t";
        }
        if(decks[1]!=0) {
            string += decks[1];
            string += " Blue lvl 2 \t";
        }
        if(decks[2]!=0) {
            string += decks[2];
            string += " Blue lvl 3 \t";
        }
        if(decks[3]!=0) {
            string += decks[3];
            string += " Green lvl 1 \t";
        }
        if(decks[4]!=0) {
            string += decks[4];
            string += " Green lvl 2 \t";
        }
        if(decks[5]!=0) {
            string += decks[5];
            string += " Green lvl 3 \t";
        }
        if(decks[6]!=0) {
            string += decks[6];
            string += " Yellow lvl 1 \t";
        }
        if(decks[7]!=0) {
            string += decks[7];
            string += " Yellow lvl 2 \t";
        }if(decks[8]!=0) {
            string += decks[8];
            string += " Yellow lvl 3 \t";
        }
        if(decks[9]!=0) {
            string += decks[9];
            string += " Purple lvl 1 \t";
        }
        if(decks[10]!=0) {
            string += decks[10];
            string += " Purple lvl 2 \t";
        }
        if(decks[11]!=0) {
            string += decks[11];
            string += " Purple lvl 3 \t";
        }
        return string;
    }

    public String parseIntToSpecialPowerPretty(int i, int[] resources){
        if(i==0){
            return "discount of "+ parseIntArrayToStringOfResources(resources);
        }else if(i==1){
            return "extraDeposit for "+ parseIntArrayToStringOfResources(resources);
        }else if(i==2){
            return "extraProd using "+ parseIntArrayToStringOfResources(resources);
        }else if(i==3){
            return "white to "+ parseIntArrayToStringOfResources(resources);
        }else {
            return "error";
        }
    }


    public String parseIntArrayToStringOfResourcesPretty(int[] resources) {
        String string = new String();
        if(resources[0]!=0) {
            string += resources[0];
            string += " coins\t";
        }
        if(resources[1]!=0) {
            string += resources[1];
            string += " stones\t";
        }
        if(resources[2]!=0) {
            string += resources[2];
            string += " servants\t";
        }
        if(resources[3]!=0) {
            string += resources[3];
            string += " shields\t";
        }
        if(resources[4]!=0) {
            string += resources[4];
            string += " faith\t";
        }
        return string;
    }





    public int getResourceRelatedFromArray(int[] array) {
        for(int i=0; i<array.length; i++){
            if(array[i]>0) return i;
        }
        return -1;
    }

    public int getQuantityOfResourceFromPowerResources(int[] array){
        for(int i=0; i<array.length; i++){
            if(array[i]>0) return array[i];
        }
        return -1;
    }
}
