package it.polimi.ingsw.Communication;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

public class EchoServerClientHandler implements Runnable {
    private Socket socket;
    public EchoServerClientHandler(Socket socket) {
        this.socket = socket;
    }
    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
// Leggo e scrivo nella connessione finche' non ricevo "quit"
            while (true) {
                String line = in.nextLine();
                if (processCmd(line, out)){
                    break;
                }
            }
// Chiudo gli stream e il socket
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private Boolean processCmd(String cmd, PrintWriter out){
        switch (cmd.toLowerCase()){
            case "quit" :
                return true;
        }
        System.out.println("got:\n" + cmd + "\n");
        out.println("Answer: " + cmd);
        Gson gson = new Gson();
        Map map = gson.fromJson(cmd, Map.class);
        // Assert.assertEquals(1, map.size());
        Map m = (Map) map.get("moveto");
        double x = (double)m.get("x");
        out.flush();
        return false;
    }
}