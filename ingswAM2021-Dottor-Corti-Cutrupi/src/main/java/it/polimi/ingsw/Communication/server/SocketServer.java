package it.polimi.ingsw.Communication.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer implements Runnable {
    private final int port;
    private final ExecutorService executorService;
    private final Server server;
    private volatile boolean active;

    /**
     * Constructor SocketServer creates a new SocketServer instance.
     *
     * @param port of type int - the port on which server will listen.
     * @param server of type Server - the main server object.
     */
    public SocketServer(int port, Server server) {
        this.server = server;
        this.port = port;
        executorService = Executors.newCachedThreadPool();
        active = true;
    }

    /**
     * Method setActive sets the active connection field of this SocketServer object.
     *
     * @param value the active connection value of the socket.
     */
    public void setActive(boolean value) {
        active = value;
    }

    /**
     * Method acceptConnections accepts connections from clients and create a new thread, one for each
     * connection. Each thread lasts until client disconnection.
     *
     * @param serverSocket of type ServerSocket - the server socket, which accepts connections.
     */
    public void acceptingConnections(ServerSocket serverSocket) {
        while (active) {
            try {
                SingleConnection socketClient = new SingleConnection(serverSocket.accept(), server);
                executorService.submit(socketClient);
            } catch (IOException e) {
                System.err.println("Error! " + e.getMessage());
            }
        }
    }

    /**
     * Method run is the runnable method which instantiates a new socket on server side.
     *
     * @see Runnable#run()
     */
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Socket Server started; listening on port "
                            + port
                            + ". Type "
                            + "\"quit\" to exit");
            acceptingConnections(serverSocket);
        } catch (IOException e) {
            System.err.println("Error during Socket initialization, quitting...");
            System.exit(0);
        }
    }
}
