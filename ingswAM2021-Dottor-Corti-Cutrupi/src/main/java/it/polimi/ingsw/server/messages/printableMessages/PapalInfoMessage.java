package it.polimi.ingsw.server.messages.printableMessages;

import it.polimi.ingsw.client.shared.ClientSideSocket;
import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.model.boardsAndPlayer.Player;
import it.polimi.ingsw.server.messages.showingMessages.PapalPathMessage;

/**
 * Self explanatory name
 */
public class PapalInfoMessage implements PrintableMessage{
    private StringBuilder info;

    public PapalInfoMessage(GameHandler gameHandler) {
        info= new StringBuilder("Here are some infos about the papal path in this exact  moment: \n");
        for (Player player: gameHandler.getGame().playersInGame()){
            if(player!= gameHandler.activePlayer()){
                info.append(player.getNickname()).append(" is in position ").append(player.getFaithPosition());
                if(!player.noPapalCardActivated()) {
                    info.append(" and has activated papal favor card number ");
                    for(int i=0;i<player.numberOfActivatedPapalCards();i++)
                        info.append(i + 1).append(", ");
                    info.append(" \n");
                }
                else info.append(" and hasn't activated any papal favor card yet, \n");
            }
        }
        info.append("Your position is ").append(gameHandler.activePlayer().getFaithPosition());
        if(!gameHandler.activePlayer().noPapalCardActivated()) {
            info.append(" and you've activated papal favor card number ");
            for(int i=0;i<gameHandler.activePlayer().numberOfActivatedPapalCards();i++)
                info.append(i + 1).append(", ");
            info.append(" \n");
        }
        else info.append(" and you haven't activated any papal favor card yet, \n");
        info.append("The next papal favor card still to be activated by anyone is in position ").append(gameHandler.activePlayer().nextPapalCardToActivateInfo());
        gameHandler.sendMessageToActivePlayer(new PrintAString(info.toString()));
        gameHandler.sendMessageToActivePlayer(new PapalPathMessage(gameHandler.activePlayer().getPapalPath()));
    }

    @Override
    public String getString() {
        return info.toString();
    }

    @Override
    public void execute(ClientSideSocket socket, boolean isGui) {
        if(!isGui) System.out.println(info);
    }
}
