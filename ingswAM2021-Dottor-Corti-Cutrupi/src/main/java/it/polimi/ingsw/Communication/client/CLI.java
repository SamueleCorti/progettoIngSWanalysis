package it.polimi.ingsw.Communication.client;


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
    private String ip;
    private int port;

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
        System.out.println(">Insert the server IP address");
        System.out.print(">");
        String ip = scanner.nextLine();
        System.out.println(">Insert the server port");
        System.out.print(">");
        int port = scanner.nextInt();
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
        else{

        }
    }

    /**
     * Method loop keeps running and executing all actions client side, if the input has been toggled (through the
     * appropriate method) it calls the action method and parses the player's input.
     */
    public void loop() {
        input.reset();
        String cmd = input.nextLine();
        listeners.firePropertyChange("action", null, cmd);
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

    /**
     * Method choosePlayerNumber lets the first-connected user decide the match capacity.
     * Terminates the client if the player inserts an incorrect type of input.
     */
    public void choosePlayerNumber() {
        int selection;
        while (true) {
            try {
                System.out.print(">");
                String cmd = input.nextLine();
                selection = Integer.parseInt(cmd);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid parameter, it must be a numeric value." +
                        nameMapColor.get("RST"));
            }
        }
        //clientConnectionSocket.send(new NumberOfPlayers(selection));
    }

}
