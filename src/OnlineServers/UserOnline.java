package OnlineServers;

import OnlineServers.Inheritable.InheritableServer;
import ConsolePackage.Console;
import CurrentDb.Tables.UserTable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserOnline extends InheritableServer {

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
                + _serverConfig.UserOnlinePort);

        System.out.println("Now you can run your client app.");
        ServerSocket severSocket = null;
        try {
            severSocket = new ServerSocket(_serverConfig.UserOnlinePort);
            if (severSocket != null) {
                System.out.println("Useronline Conntected through port :" + _serverConfig.UserOnlinePort);
            }
        } catch (IOException ex) {
            Logger.getLogger(InheritableServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (_serverConfig.IsActive && _shouldThreadRun) {
            Console.writeLine("Server running...." + _serverConfig.IsActive);
            serverForAddingUser(severSocket);
            _serverHitCounter++;
            System.err.println("Counter : " + _serverHitCounter);
            if (_serverHitCounter >= _serverRefershAfterHits) {
                _serverHitCounter = 0;
                //refeshes port and is active state
                reReadDataFromServer();
            }

        }

    }

    private void serverForAddingUser(ServerSocket severSocket) {

        Socket socket = null;

        try {
            socket = severSocket.accept();
        } catch (IOException ex) {
            Logger.getLogger(InheritableServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        // for taking input from sendUserOnlineRequestToServer
        ObjectInputStream inputFromClient = super.getObjectInputStream(socket);
        boolean returnResult = false;
        UserTable userReturnedfromClient = null;
        try {
            userReturnedfromClient = (UserTable) inputFromClient.readObject();

            returnResult = super.addUsertoOnlineList(userReturnedfromClient);

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(InheritableServer.class.getName()).log(Level.SEVERE, null, ex);

        }
        // sending data to sendUserOnlineRequestToServer

        // for giving output to the sendUserOnlineRequestToServer.
        // output to sendUserOnlineRequestToServer, to send data to the server
        // get output from server
        try {
            // send data to sendUserOnlineRequestToServer
            System.out.println("Writing to client : " + returnResult);
            if (returnResult) {
                super.getOutputObjectStream(socket).writeObject(userReturnedfromClient);
            } else {
                super.getOutputObjectStream(socket).writeObject(null);
            }
        } catch (IOException ex) {
            Logger.getLogger(InheritableServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendUserOnlineRequestToServer(UserTable sendingUser) {

        int port = _serverConfig.UserOnlinePort;
        String ip = _serverConfig.ServerIP;       
        tryagain:
        try (Socket socket = new Socket(ip, port)) {
            super.getOutputObjectStream(socket).writeObject(sendingUser);
            UserTable gotUser = (UserTable)super.getObjectInputStream(socket).readObject();
            if(gotUser != null){
                gotUser.print();
            } else {
                System.out.println("user already exist.");
            }
        } catch (IOException ex) {
            Logger.getLogger(UserOnline.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Sorry can't connect with " + ip + ":" + port);
            System.out.println("trying again ...");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserOnline.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Object not found :  " + ip + ":" + port);
        }

    }

    public static void main(String[] args) {

        UserOnline online = new UserOnline();
        online.reReadDataFromServer();
        online.startThread();

    }

}
