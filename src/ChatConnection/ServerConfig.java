package ChatConnection;

import CurrentDb.TableColumns.ServerSetting;
import CurrentDb.*;
import Database.DatabaseQuery;
import Database.DbData;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerConfig implements Runnable {

    private static ServerSocket severSocket;
    private static Scanner in;
    private final int serverRefershAfterHits;
    private int serverHitCounter = 0;
    private ResultSet server;
    DatabaseQuery query;
    DbData serverData;
    int port;
    boolean isActive = false;

    public ServerConfig() {
        this.serverRefershAfterHits = 20000;
        serverData = new DbData();
    }

    private void initalizeQuery() {
        query = new DatabaseQuery();
        // setting table name
        query.setTableName(TableNames.SERVERSETTING);
    }
    
    /**
     * Refreshing Server ResultSet
     * refresh port
     * 
     */

    public void reReadDataFromServer() {
        // executing the query
        server = query.ExecuteReadQuery(query.getSelectSQL());
        serverData.intialize(server, query.getColumnsNames());
        port = Integer.parseInt(serverData.getRowValue(0, ServerSetting.ServerPort));
        isActive = serverData.getRowValue(0, ServerSetting.IsActive).equals("1");
    }

    @Override
    public void run() {
        in = new Scanner(System.in);

        //refeshes port and is active state
        reReadDataFromServer();

        System.out.println("Server Estabilsh Connection On Localhost or own ip with port : "
                + port);
        try {
            severSocket = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(ServerConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Now you can run your client app.");

        while (isActive) {
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
            serverHitCounter++;
            System.err.println("Counter : " + serverHitCounter);
            if (serverHitCounter >= serverRefershAfterHits) {
                serverHitCounter = 0;
                //refeshes port and is active state
                reReadDataFromServer();
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
