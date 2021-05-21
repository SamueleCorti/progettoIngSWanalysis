package it.polimi.ingsw.communication.server.messages.jsonMessages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.communication.server.messages.Message;
import it.polimi.ingsw.model.lorenzoIlMagnifico.LorenzoIlMagnifico;

public class LorenzoIlMagnificoMessage implements Message {
    private String lorenzoJson;

    public LorenzoIlMagnificoMessage(LorenzoIlMagnifico lorenzo) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String lorenzoJson = gson.toJson(lorenzo);
        this.lorenzoJson = lorenzoJson;
    }

    public String getLorenzoJson() {
        return lorenzoJson;
    }
}
