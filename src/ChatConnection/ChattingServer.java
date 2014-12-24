package ChatConnection;

import ConsolePackage.Console;
import CurrentDb.*;
import CurrentDb.Tables.ServerSettingTable;
import CurrentDb.Tables.UserTable;
import Database.DatabaseQuery;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChattingServer implements Runnable {

    private static Scanner in;
    private final int serverRefershAfterHits;
    private int serverHitCounter = 0;
    DatabaseQuery db;
    ServerSettingTable serverConfig = new ServerSettingTable();

    public ArrayList<UserTable> UsersOnline;

    public static Thread serverThread;

    boolean isActive = false;
    boolean shouldThreadRun = false;

    public boolean isUserAlreadyOnlineList(UserTable userGotOnline) {
        for (UserTable alreadyOnline : UsersOnline) {
            if (alreadyOnline.UserID == userGotOnline.UserID) {
                return true;
            }
        }
        return false;
    }

    public void addUsertoOnlineList(UserTable userGotOnline) {
        if (!isUserAlreadyOnlineList(userGotOnline)) {
            UsersOnline.add(userGotOnline);
        } else {
            System.out.println("user already online.");
        }
    }

    public void removeUserfromOnlineList(UserTable userGotOnline) {
        if (isUserAlreadyOnlineList(userGotOnline)) {
            UsersOnline.remove(userGotOnline);
        } else {
            System.out.println("user is not online.");
        }
    }

    public ChattingServer() {
        this.serverRefershAfterHits = 100;
        initalizeQuery();
    }

    /**
     * checks if not null and alive
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public Thread stopThread() {
        if (serverThread == null) {
            serverThread = new Thread(this);
        }
        if (serverThread.isAlive()) {
            shouldThreadRun = false;
            serverThread.interrupt();
            serverThread.stop();

            System.out.println("Server stopped.");

        } else {
            System.out.println("Sorry thread is not running can't stop. Already in stop mode.");
        }
        return serverThread;
    }

    /**
     * checks if not null and alive
     *
     * @return
     */
    public Thread startThread() {
        if (serverThread == null) {
            serverThread = new Thread(this);
        }
        if (!serverThread.isAlive()) {
            shouldThreadRun = true;
            serverThread.start();
            UsersOnline = new ArrayList<>(1000);
            System.out.println("Server started.");
        } else {
            System.out.println("Thread is already running.");

        }
        return serverThread;
    }

    private void initalizeQuery() {
        db = new DatabaseQuery();
        // setting table name
        db.setTableName(TableNames.SERVERSETTING);
    }

    /**
     * Refreshing Server ResultSet refresh port
     *
     */
    public void reReadDataFromServer() {
        // executing the db
        db.readData();
        db.getResultsAsObject(serverConfig);
    }

    @Override
    public void run() {
        in = new Scanner(System.in);

        //refeshes port and is active state
        reReadDataFromServer();

        System.out.println("Server Estabilsh Connection On Localhost or own ip with port : "
                + serverConfig.ServerPort);

        System.out.println("Now you can run your client app.");

        while (serverConfig.IsActive && shouldThreadRun) {
            Console.writeLine("Server running...." + serverConfig.IsActive);

            serverHitCounter++;
            System.err.println("Counter : " + serverHitCounter);
            if (serverHitCounter >= serverRefershAfterHits) {
                serverHitCounter = 0;
                //refeshes port and is active state
                reReadDataFromServer();
            }

        }

    }

    private void serverForAddingUser() {
        ServerSocket severSocket = null;
        try {
            severSocket = new ServerSocket(serverConfig.ServerPort);
        } catch (IOException ex) {
            Logger.getLogger(ChattingServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        Socket connectionSocket = null;

        try {
            connectionSocket = severSocket.accept();
        } catch (IOException ex) {
            Logger.getLogger(ChattingServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        // for taking input from client
        InputStream inputStream = null;

        try {
            inputStream = connectionSocket.getInputStream();
        } catch (IOException ex) {
            Logger.getLogger(ChattingServer.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ChattingServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        // output to client, to send data to the server
        DataOutputStream dataOutputStream = new DataOutputStream(
                outputStream);
        // get output from server

        String readingLineFromClientSocket = null;

        try {
            readingLineFromClientSocket = inputFromClient.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ChattingServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        // sending data to client
        String modified = doOperation(readingLineFromClientSocket);

        try {
            // send data to client
            dataOutputStream.writeBytes(modified + "\n");
        } catch (IOException ex) {
            Logger.getLogger(ChattingServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void serverForChatting() {
        Socket connectionSocket = null;
        try {
            connectionSocket = severSocket.accept();

        } catch (IOException ex) {
            Logger.getLogger(ChattingServer.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        // for taking input from client
        InputStream inputStream = null;
        try {
            inputStream = connectionSocket.getInputStream();

        } catch (IOException ex) {
            Logger.getLogger(ChattingServer.class
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
            Logger.getLogger(ChattingServer.class
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
            Logger.getLogger(ChattingServer.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        // sending data to client
        String modified = doOperation(readingLineFromClientSocket);
        try {
            // send data to client
            dataOutputStream.writeBytes(modified + "\n");

        } catch (IOException ex) {
            Logger.getLogger(ChattingServer.class
                    .getName()).log(Level.SEVERE, null, ex);
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

    public static void main(String[] args) {
        Thread server = new Thread(new ChattingServer());
        server.start();
    }
}
