package OnlineServers;

import OnlineServers.Inheritable.InheritableServer;
import ConsolePackage.Console;
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

public class ChatServer extends InheritableServer {

    /**
     * checks if not null and alive
     *
     * @param server
     * @return
     */
    @SuppressWarnings("deprecation")
    public Thread stopThread() {
        return super.stopThread(this);
    }

    /**
     * checks if not null and alive
     *
     * @param server
     * @return
     */
    public Thread startThread() {
        return super.startThread(this);
    }

    @Override
    public void run() {
        _scanner = new Scanner(System.in);

        //refeshes port and is active state
        reReadDataFromServer();

        System.out.println("Server Estabilsh Connection On Localhost or own ip with port : "
                + _serverConfig.ServerPort);

        System.out.println("Now you can run your client app.");

        while (_serverConfig.IsActive && _shouldThreadRun) {
            Console.writeLine("Server running...." + _serverConfig.IsActive);
            serverForChatting();
            _serverHitCounter++;
            System.err.println("Counter : " + _serverHitCounter);
            if (_serverHitCounter >= _serverRefershAfterHits) {
                _serverHitCounter = 0;
                //refeshes port and is active state
                reReadDataFromServer();
            }

        }

    }



    private void serverForChatting() {
        ServerSocket severSocket = null;
        Socket connectionSocket = null;

        try {
            connectionSocket = severSocket.accept();
        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        // for taking input from client
        InputStream inputStream = null;
        try {
            inputStream = connectionSocket.getInputStream();

        } catch (IOException ex) {
            Logger.getLogger(InheritableServer.class
                    .getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(InheritableServer.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        // output to client, to send data to the server
        DataOutputStream dataOutputStream = new DataOutputStream(
                outputStream);
        // get output from server

        String readingLineFromClientSocket = null;
        try {
            readingLineFromClientSocket = inputFromClient.readLine();

        } catch (IOException ex) {
            Logger.getLogger(InheritableServer.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        // sending data to client
        String modified = doOperation(readingLineFromClientSocket);
        try {
            // send data to client
            dataOutputStream.writeBytes(modified + "\n");

        } catch (IOException ex) {
            Logger.getLogger(InheritableServer.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String doOperation(String readingLineFromClientSocket) {
//		String[] array = readingLineFromClientSocket.split(" ");
//		StringBuilder strBuilder = new StringBuilder(array.length);
//		
//		
//		return strBuilder.toString();
        return readingLineFromClientSocket;

    }

}
