package it.polimi.ingsw.server.messages.showingMessages;

import it.polimi.ingsw.model.developmentcard.Color;
import it.polimi.ingsw.model.resource.*;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Used to serialize/deserialize messages
 */
public class SerializationConverter {

    /**
     * Return an integer that serializes the given resource
     */
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
        }
        else{
            System.out.println("There was an error in parsing the resource!");
            return 150;
        }
    }

    /**
     * Return the int serializing the resource type given
     */
    public int resourceTypeToInt(ResourceType resourceType){
        if(resourceType.equals(ResourceType.Coin)){
            return 0;
        }else if(resourceType.equals(ResourceType.Stone)){
            return 1;
        }else if(resourceType.equals(ResourceType.Servant)){
            return 2;
        }else if(resourceType.equals(ResourceType.Shield)){
            return 3;
        }else if(resourceType.equals(ResourceType.Faith)){
            return 4;
        }else if(resourceType.equals(ResourceType.Blank)){
            return 5;
        }
        else{
            System.out.println("There was an error in parsing the resource!");
            return 150;
        }
    }

    /**
     * Return the integer that serializes the resource given
     */
    public int resourceTypeToInt(Resource resource){
        return resourceTypeToInt((resource.getResourceType()));
    }

    /**
     * Return a resource depending on the int given
     */
    public Resource intToResource(int i){
        switch (i){
            case 0:
                return new CoinResource();
            case 1:
                return new StoneResource();
            case 2:
                return new ServantResource();
            case 3:
                return new ShieldResource();
            case 4:
                return new FaithResource();
            case 5:
                return new BlankResource();
            default:
                return null;
        }
    }

    /**
     * Return the color (as a string) serialized by the int
     */
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

    /**
     * Return the path to access the image of the marble serialized by the integer given
     */
    public String intToMarbleStringMarket(int i){
        switch (i){
            case 0:     return "/images/general/Marbles/yellow.png";
            case 1:     return "/images/general/Marbles/grey.png";
            case 2:     return "/images/general/Marbles/purple.png";
            case 3:     return "/images/general/Marbles/blue.png";
            case 4:     return "/images/general/Marbles/red.png";
            case 5:     return "/images/general/Marbles/blank.png";
            default:
                return null;
        }
    }

    /**
     * Return the path to access the image of the resource serialized by the integer given
     */
    public String intToResourceStringMarket(int i){
        switch (i){
            case 0:     return "/images/general/coin.png";
            case 1:     return "/images/general/stone.png";
            case 2:     return "/images/general/servant.png";
            case 3:     return "/images/general/shield.png";
            case 4:     return "/images/general/faith.png";
            default:
                return null;
        }
    }

    /**
     * @param resources array of integers representing some resources
     * @return a string that contains those resources (e.g. coin coin coin stone shield)
     */
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

    /**
     * Return a string containing the special power serialized by an integer
     */
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

    /**
     * @param decks: development cards necessary to activate the leader card
     * @return: a string containing all the deserialized requirements to activate the card
     */
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

    /**
     * @return a string that describes the power of the card selected
     */
    public String parseIntToSpecialPowerPretty(int i, int[] resources){
        if(i==0){
            return "discount of "+ parseIntArrayToStringOfResourcesPretty(resources);
        }else if(i==1){
            return "extraDeposit for "+ parseIntArrayToStringOfResourcesPretty(resources);
        }else if(i==2){
            return "extraProd using "+ parseIntArrayToStringOfResourcesPretty(resources);
        }else if(i==3){
            return "white to "+ parseIntArrayToStringOfResourcesPretty(resources);
        }else {
            return "error";
        }
    }

    /**
     * Return the string of resources serialized by the array of int given
     */
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

    /**
     * @return the resource type related to the string given
     */
    public ResourceType parseStringToResourceType(String string){
        if (string.equals("coin")){
            return ResourceType.Coin;
        }else if(string.equals("stone")){
            return ResourceType.Stone;
        }else if(string.equals("shield")){
            return ResourceType.Shield;
        }else if(string.equals("servant")){
            return ResourceType.Servant;
        }else{
            return ResourceType.Blank;
        }
    }

    /**
     * @return the color related to the string given
     */
    public Color stringToColor(String string){
        switch (string.toLowerCase(Locale.ROOT)){
            case "blue":    return Color.Blue;
            case "yellow":  return Color.Yellow;
            case "purple":    return Color.Purple;
            case "green":  return Color.Green;
        }
        return null;
    }

    /**
     * @return the color serialized by the integer given
     */
    public Color intToColor(int index){
        return stringToColor(parseIntToColorString(index));
    }


    /**
     * @return the resource contained in the array (e.g. for 0300 returns 1; that means that the resource contained is stone)
     */
    public int getResourceRelatedFromArray(int[] array) {
        for(int i=0; i<array.length; i++){
            if(array[i]>0) return i;
        }
        return -1;
    }

    /**
     * @return the quantity of the resource contained in the array (e.g. for 0300 returns 3; that means that there are 3 stones serialized in the array given)
     */
    public int getQuantityOfResourceFromPowerResources(int[] array){
        for(int i=0; i<array.length; i++){
            if(array[i]>0) return array[i];
        }
        return -1;
    }
}