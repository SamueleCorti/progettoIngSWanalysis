package it.polimi.ingsw.server.messages.jsonMessages;

import it.polimi.ingsw.server.messages.Message;
import it.polimi.ingsw.model.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.requirements.ResourcesRequirements;
import it.polimi.ingsw.model.requirements.ResourcesRequirementsForAcquisition;
import it.polimi.ingsw.model.resource.Resource;

public class DevelopmentCardMessage implements Message {
    private String leaderCardJson;
    public DevelopmentCardMessage(DevelopmentCard developmentCard){
        if(developmentCard != null) {
            this.leaderCardJson = printDevCards(developmentCard);
        }
        else leaderCardJson = "null (there are no more cards in the deck of the specified color and level)";
    }

    public String getLeaderCardJson() {
        return leaderCardJson;
    }

    private String printDevCards(DevelopmentCard card) {
        if(card==null)  return "This deck is finished!\n";
        String string="\nHere is the new development card: \n";
        string+="Color: "+ card.getCardStats().getValue1()+"\tlevel: "+card.getCardStats().getValue0()+" \tvictory points: "+card.getVictoryPoints();
        string+="\nCard cost: \t";
        for(ResourcesRequirementsForAcquisition requirements: card.getCardPrice())
            string+=requirements.getResourcesRequired().getValue0() +" "+ requirements.getResourcesRequired().getValue1().getResourceType()+"s\t";
        string+="\nProduction cost: \n";
        for(ResourcesRequirements resourcesRequirements: card.getProdRequirements()){
            string+= resourcesRequirements.getResourcesRequired().getValue0()+" "+ resourcesRequirements.getResourcesRequired().getValue1().getResourceType()+"s\t";
        }
        string+="\n";
        string+="Resources produced: \n";
        for(Resource resource: card.getProdResults())
            string+= resource.getResourceType()+"\t";
        string+="\n";
        return string;
    }
}