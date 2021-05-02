package it.polimi.ingsw.Communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// ClientHandler class
class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private String nickname=null;

    // Constructor
    public ClientHandler(Socket socket)
    {
        this.clientSocket = socket;
    }

    public void run()
    {
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            out = new PrintWriter( clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


            //interaction for username insertion
            while (nickname==null){
                out.println("Insert username: ");
                nickname=in.readLine();
            }
            out.println("Your username is "+ nickname);


            String line;
            while (!(line = in.readLine()).equals("quit")) {
                switch (line){
                    case "CreateMatch":
                        int numOfPlayers;
                        do{
                            out.println("Insert number of players (between 1 and 4): ");
                            numOfPlayers = Integer.parseInt(in.readLine());
                        }while (numOfPlayers<1 || numOfPlayers>4);
                        out.println("Number of players for this game = "+ numOfPlayers);
                        if((line = in.readLine()).equals("private")){
                            new Match(clientSocket,true,nickname,numOfPlayers);
                        }
                }


                System.out.printf(" Sent from the client: "+ line);
                out.println(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                    clientSocket.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
