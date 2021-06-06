package it.polimi.ingsw;

import it.polimi.ingsw.model.boardsAndPlayer.Dashboard;
import it.polimi.ingsw.model.leadercard.leaderpowers.ExtraProd;
import it.polimi.ingsw.model.leadercard.leaderpowers.PowerType;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.StoneResource;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class ExtraProdTest {

    @Test
    public void extraProdTest(){
        ArrayList<Resource> resources= new ArrayList<>();
        StoneResource stone= new StoneResource();
        resources.add(stone);
        Dashboard dashboard= new Dashboard(1);
        ExtraProd extraProd= new ExtraProd(resources);
        extraProd.activateLeaderPower(dashboard);
        assertEquals(extraProd.returnPowerType(), PowerType.ExtraProd);
        assertEquals(extraProd.returnRelatedResourcesCopy().get(0).getResourceType(), resources.get(0).getResourceType());
        String string= new String();
        for(Resource resource:resources)    string+=resource.getResourceType()+"\t";
        assertEquals(extraProd.toString(),
                "allows you to produce "+ resources.size()+ " resources of your choice after discarding these resources:\n" +string+
                        " \nAlso allows you to move forward by "+ resources.size()+" in the papal path");
    }
}
