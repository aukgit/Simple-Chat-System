package OnlineServers;

import ConsolePackage.Console;
import CurrentDb.TableColumns.User;
import CurrentDb.TableNames;
import CurrentDb.Tables.UserTable;
import Database.DatabaseQuery;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserOnlineServer extends InheritableServer {

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
        
        while (_serverConfig.IsActive && _shouldThreadRun) {
            Console.writeLine("Server running...." + _serverConfig.IsActive);
            serverForAddingUser();
            _serverHitCounter++;
            System.err.println("Counter : " + _serverHitCounter);
            if (_serverHitCounter >= _serverRefershAfterHits) {
                _serverHitCounter = 0;
                //refeshes port and is active state
                reReadDataFromServer();
            }
            
        }
        
    }
    
    private void serverForAddingUser() {
        ServerSocket severSocket = null;
        try {
            severSocket = new ServerSocket(_serverConfig.UserOnlinePort);
            if (severSocket != null) {
                System.out.println("Useronline Conntected through port :" + _serverConfig.UserOnlinePort);
            }
        } catch (IOException ex) {
            Logger.getLogger(InheritableServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        Socket connectionSocket = null;
        
        try {
            connectionSocket = severSocket.accept();
        } catch (IOException ex) {
            Logger.getLogger(InheritableServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        // for taking input from client
        ObjectInputStream inputFromClient = super.getInputObjectStream(connectionSocket);
        
        UserTable userReturnedfromClient = null;
        UserTable _u = new UserTable();
        DatabaseQuery db2 = new DatabaseQuery(TableNames.USER);
        
        try {
            int id = inputFromClient.readInt();
            db2.getResultsFirstAsObject(User.UserID, id + "", _u);
            super.addUsertoOnlineList(userReturnedfromClient);
            
        } catch (Exception ex) {
            Logger.getLogger(InheritableServer.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        // sending data to client

        // for giving output to the client.
        // output to client, to send data to the server
        ObjectOutputStream objectOutputStream = super.getOutputObjectStream(connectionSocket);
        // get output from server
        try {
            // send data to client
            objectOutputStream.writeBoolean(true);
        } catch (IOException ex) {
            Logger.getLogger(InheritableServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void client() {
        
        int port = _serverConfig.UserOnlinePort;
        String ip = _serverConfig.ServerIP;
        System.out.println("User id to load : ");
        String idStr = "";
        try {
            idStr = super.getBufferedReader().readLine();
        } catch (IOException ex) {
            Logger.getLogger(UserOnlineServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        int id = Integer.parseInt(idStr);
        
        tryagain:
        try (Socket socket = new Socket(ip, port)) {
            
            super.getOutputObjectStream(socket).writeInt(id);
            boolean b = super.getInputObjectStream(socket).readBoolean();
            System.out.println(b);
        } catch (IOException ex) {
            Logger.getLogger(UserOnlineServer.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Sorry can't connect with " + ip + ":" + port);
            System.out.println("trying again ...");
            
        }
        
    }
    
    public static void main(String[] args) {
        
        UserOnlineServer online = new UserOnlineServer();
        online.reReadDataFromServer();
        online.startThread();
        
    }
    
}
