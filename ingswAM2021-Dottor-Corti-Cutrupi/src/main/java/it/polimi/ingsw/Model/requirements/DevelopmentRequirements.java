package it.polimi.ingsw.Model.requirements;

import it.polimi.ingsw.Model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.Model.developmentcard.Color;
import it.polimi.ingsw.Model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.Model.developmentcard.DevelopmentCardZone;

import java.util.ArrayList;
import java.util.List;

/**
*   this class represents the requirements of leader cards that require active development cards in the dashboard
 */

public class DevelopmentRequirements implements Requirements {
    /** number of cards with the specified level and color that i need to have
     *
     */
    private Integer amountOfDevelopmentRequired;
    private Integer levelRequired;
    private Color colorRequired;

    @Override
    public String toString() {
        int amount=amountOfDevelopmentRequired;
        String string= amount + " "+colorRequired+ " development card";
        if(amount>1)    string+="s";
        string+=" level " + levelRequired+"\t";
        return string;
    }

    public DevelopmentRequirements(Integer amountOfDevelopmentRequired, Integer levelRequired, Color colorRequired) {
        this.amountOfDevelopmentRequired = amountOfDevelopmentRequired;
        this.levelRequired = levelRequired;
        this.colorRequired = colorRequired;
    }

    public Integer getLevelRequired() {
        return levelRequired;
    }

    public Color getColorRequired() {
        return colorRequired;
    }

    public Integer getAmountOfDevelopmentRequired() {
        return amountOfDevelopmentRequired;
    }

    public boolean checkRequirement(Dashboard dashboard){
        /**we create a temporary list of all the cards in the dashboard where we check the requirements
         *
         */
        List <DevelopmentCard> developmentCardsInDashboard = new ArrayList<DevelopmentCard>();
        /**here we populate the list
         *
         */
            for (DevelopmentCardZone developmentCardZone : dashboard.getDevelopmentCardZones()) {
                for(DevelopmentCard developmentCardToAdd : developmentCardZone.getCards()){
                    developmentCardsInDashboard.add(developmentCardToAdd);
                }
            }
        /** we now check if there's a number of cards of the required level and color;
        *   we use the temporary int i as a counter of their occurrences in the list
           */
        int i=0;
        for(DevelopmentCard developmentCard: developmentCardsInDashboard){
            if(developmentCard.getCardStats().getValue1()==colorRequired && developmentCard.getCardStats().getValue0()>=levelRequired){
                i++;
            }
        }
        /**if the counter equals the number of required cards, we return true
         *
         */
        if(i>=this.amountOfDevelopmentRequired) {
            return true;
        }else{
            return false;
        }
    }
}
