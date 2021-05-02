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

            // get the outputStream of client
            out = new PrintWriter(
                    clientSocket.getOutputStream(), true);

            // get the inputStream of client
            in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));

            String line;

            while (nickname==null){
                out.println("Insert username: ");
                nickname=in.readLine();
            }
            out.println("Your username is "+ nickname);
            while ((line = in.readLine()) != null) {

                // writing the received message from
                // client
                System.out.printf(
                        " Sent from the client: %s\n",
                        line);
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
