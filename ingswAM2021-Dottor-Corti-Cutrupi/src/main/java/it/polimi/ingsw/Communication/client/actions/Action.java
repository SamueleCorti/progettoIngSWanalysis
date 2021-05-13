package it.polimi.ingsw.Communication.client.actions;

import java.io.Serializable;

/**
 * Most basic type of message the client can send to the server to communicate. All other 'action' messages the client has, implement this interface.
 */
public interface Action extends Serializable {
}
