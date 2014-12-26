/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestingCode;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alim
 */
public class Server {

    public static int port = 5622;
    public static String server = "localhost";

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        new Server().runServer();

    }

    public void runServer() throws IOException {

        ServerSocket serverSocket;
        serverSocket = new ServerSocket(port);
        System.out.println("Connecting client through port :" + port);
        while (true) {
            try (Socket socket = serverSocket.accept()) {

                System.out.println("Connected client through port :" + port);

                ObjectInputStream inputFromClient = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream outputToClient = new ObjectOutputStream(socket.getOutputStream());

                ExampleObject ex = (ExampleObject) inputFromClient.readObject();
                System.out.println("Server object got:");
                ex.print();
                doProcessing(ex);
                outputToClient.writeObject(ex);
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     *
     * @param ex
     */
    public void doProcessing(ExampleObject ex) {
        ex.num = 8882;
        ex.str = "Processed";
    }
}
