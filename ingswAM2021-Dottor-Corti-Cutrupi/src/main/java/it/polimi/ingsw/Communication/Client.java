package it.polimi.ingsw.Communication;


import java.io.*;
import java.net.*;
import java.util.*;

// Client class
class Client {
    public static void main(String[] args)
    {
        try (Socket socket = new Socket("localhost", 1234)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner sc = new Scanner(System.in);

            //Interaction for nickname insertion
            System.out.println(in.readLine());
            String line = sc.nextLine();
            out.println(line);
            System.out.println(in.readLine());

            while (!"exit".equalsIgnoreCase(line)) {
                line = sc.nextLine();
                out.println(line);
                out.flush();

                // displaying server reply
                System.out.println("Server replied " + in.readLine());
            }
            sc.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }



}