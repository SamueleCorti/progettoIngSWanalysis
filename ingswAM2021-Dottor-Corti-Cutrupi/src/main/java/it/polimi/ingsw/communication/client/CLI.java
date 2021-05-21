package it.polimi.ingsw.communication.client;


import java.beans.PropertyChangeSupport;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

public class CLI implements Runnable {
    private static final HashMap<String, String> nameMapColor = new HashMap<>();
    private final PrintStream output;
    private final Scanner input;
    private final PropertyChangeSupport listeners = new PropertyChangeSupport(this);
    private boolean activeGame;
    private ClientSideSocket clientSideSocket;
    private int maxSideIndex;
    private final String ip;
    private final int port;

    /**
     * Constructor CLI creates a new CLI instance.
     */
    public CLI(String ip, int port) {
        input = new Scanner(System.in);
        output = new PrintStream(System.out);
        activeGame = false;
        this.ip = ip;
        this. port = port;
    }

    /**
     * The main class of CLI client. It instantiates a new CLI class, running it.
     *
     * @param args of type String[] - the standard java main parameters.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //TODO: remove the comments
        /*
        System.out.println(">Insert the server IP address");
        System.out.print(">");
        String ip = scanner.nextLine();
        System.out.println(">Insert the server port");
        System.out.print(">");
        int port = scanner.nextInt();*/
        String ip= "127.0.0.1";
        int port=4321;
        CLI cli = new CLI(ip,port);
        cli.run();
    }

    /**
     * Method toggleActiveGame changes the value of the parameter activeGame, which states if the game is active or if
     * it has finished.
     *
     * @param activeGame of type boolean - the value based on the status of the game.
     */
    public void toggleActiveGame(boolean activeGame) {
        this.activeGame = activeGame;
    }

    /**
     * Method setup called when a client instance has started. It asks player's nickname and tries to establish a
     * connection to the remote server through the socket interface. If the connection is active, displays a message on
     * the CLI.
     */
    public void setup() {
        clientSideSocket = new ClientSideSocket(ip, port);
        if(!clientSideSocket.setup()) {
            System.err.println("The entered IP/port doesn't match any active server or the server is not " +
                    "running. Please try again!");
            CLI.main(null);
        }
    }

    /**
     * Method isActiveGame returns the activeGame of this CLI object.
     *
     * @return - the activeGame (type boolean) of this CLI object.
     */
    public synchronized boolean isActiveGame() {
        return activeGame;
    }

    /**
     * Method run loops waiting for a message.
     */
    @Override
    public void run() {
        setup();
        input.close();
        output.close();
    }
}
