package it.polimi.ingsw.server.messages.gameplayMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.model.boardsAndPlayer.Player;
import it.polimi.ingsw.server.messages.Message;

public class AvailableResourcesForDevMessage implements Message {
    private int[] resources;

    public AvailableResourcesForDevMessage(Player player) {
        this.resources = player.availableResourcesForDevelopment();
    }

    public int[] getResources() {
        return resources;
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(isGui){
            socket.refreshResourcesForDevelopment(resources);
        }
    }
}
