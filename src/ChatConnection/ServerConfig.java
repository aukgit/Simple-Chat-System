package ChatConnection;

import ChatConnection.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerConfig implements Runnable {

    private static ServerSocket severSocket;
    private static Scanner in;
    private int serverRefershAfterHits;
    private int serverHitCounter = 0;

    public ServerConfig() {
        this.serverRefershAfterHits = 20000;
    }

    @Override
    public void run() {
        in = new Scanner(System.in);
        
        System.out.println("(Server) Enter your server port no:");
        int port = in.nextInt();
        System.out.println("Server Estabilsh Connection On Localhost or own ip with port : "
                + port);
        try {
            severSocket = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(ServerConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Now you can run your client app.");

        while (true) {
            Socket connectionSocket = null;
            try {
                connectionSocket = severSocket.accept();
            } catch (IOException ex) {
                Logger.getLogger(ServerConfig.class.getName()).log(Level.SEVERE, null, ex);
            }

            // for taking input from client
            InputStream inputStream = null;
            try {
                inputStream = connectionSocket.getInputStream();
            } catch (IOException ex) {
                Logger.getLogger(ServerConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
            InputStreamReader inputStreamReader = new InputStreamReader(
                    inputStream);
            BufferedReader inputFromClient = new BufferedReader(
                    inputStreamReader);
            // for giving output to the client.
            OutputStream outputStream = null;
            try {
                outputStream = connectionSocket.getOutputStream();
            } catch (IOException ex) {
                Logger.getLogger(ServerConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
            // output to client, to send data to the server
            DataOutputStream dataOutputStream = new DataOutputStream(
                    outputStream);
			// get output from server

            String readingLineFromClientSocket = null;
            try {
                readingLineFromClientSocket = inputFromClient.readLine();
            } catch (IOException ex) {
                Logger.getLogger(ServerConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
            // sending data to client
            String modified = doOperation(readingLineFromClientSocket);
            try {
                // send data to client
                dataOutputStream.writeBytes(modified + "\n");
            } catch (IOException ex) {
                Logger.getLogger(ServerConfig.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private static String doOperation(String readingLineFromClientSocket) {
//		String[] array = readingLineFromClientSocket.split(" ");
//		StringBuilder strBuilder = new StringBuilder(array.length);
//		
//		
//		return strBuilder.toString();
        return readingLineFromClientSocket;

    }

}
