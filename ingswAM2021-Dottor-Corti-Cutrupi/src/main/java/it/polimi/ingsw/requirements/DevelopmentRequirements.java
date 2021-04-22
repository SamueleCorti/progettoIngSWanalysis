package it.polimi.ingsw.requirements;

import it.polimi.ingsw.Dashboard;
import it.polimi.ingsw.developmentcard.Color;
import it.polimi.ingsw.developmentcard.DevelopmentCard;
import it.polimi.ingsw.developmentcard.DevelopmentCardZone;
import it.polimi.ingsw.requirements.Requirements;
import org.javatuples.Pair;

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
        return "DevelopmentRequirements{" +
                "amountOfDevelopmentRequired=" + amountOfDevelopmentRequired +
                ", levelRequired=" + levelRequired +
                ", colorRequired=" + colorRequired +
                '}';
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
                i = i++;
            }
        }
        /**if the counter equals the number of required cards, we return true
         *
         */
        if(i==this.amountOfDevelopmentRequired) {
            return true;
        }else{
            return false;
        }
    }
}
