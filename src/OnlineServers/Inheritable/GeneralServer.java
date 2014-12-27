package OnlineServers.Inheritable;

import ConsolePackage.Console;
import Global.InternetProtocol;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class GeneralServer<T> extends InheritableServer {

    private int _Port;
    private String _ServerIp;
    private String _ServerName;

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

    protected void initialize(String serverName, int port, String ip) {
        this._ServerName = serverName + " Server";
        this._Port = port;
        this._ServerIp = ip;
    }

    @Override
    public void run() {

        System.out.println(getServerName() + " connected through own ip(" + InternetProtocol.getIp() + ") or localhost with port : " + getPort());

        System.out.println("Now you can run your client app.");
        ServerSocket severSocket = null;
        try {
            severSocket = new ServerSocket(getPort());
            if (severSocket != null) {
                System.out.println(getServerName() + " connected through :" + this._Port);
            }
        } catch (IOException ex) {
            Logger.getLogger(InheritableServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (_serverConfig.IsActive && _shouldThreadRun) {
            Console.writeLine(getServerName() + " running...." + _serverConfig.IsActive);
            serverRun(severSocket);
            _serverHitCounter++;
            System.err.println("Counter : " + _serverHitCounter);
            if (_serverHitCounter >= _serverRefershAfterHits) {
                _serverHitCounter = 0;
                //refeshes port and is active state
                reReadDataFromServer();
            }

        }

    }

    @SuppressWarnings("unchecked")
    private void serverRun(ServerSocket severSocket) {


        try (Socket socket  = severSocket.accept()) {

            // for taking input from sendUserOnlineRequestToServer
            ObjectInputStream inputFromClient = super.getInputObjectStream(socket);
            T objectReturnFromClient = (T) inputFromClient.readObject();
            if (objectReturnFromClient != null) {
                doProcessInServer(objectReturnFromClient);
            } else {
                System.out.println("Server got an empty object.");
            }

            // send data to sendUserOnlineRequestToServer
            System.out.println("Writing to client : " + objectReturnFromClient.toString());

            super.getOutputObjectStream(socket).writeObject(objectReturnFromClient);

        } catch (IOException ex) {
            Logger.getLogger(InheritableServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GeneralServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public T clientSendingMethod(T sendingObject) {

        int port = this.getPort();
        String ip = this.getServerIp();
        tryagain:
        try (Socket socket = new Socket(ip, port)) {
            // send request to server
            super.getOutputObjectStream(socket).writeObject(sendingObject);
            @SuppressWarnings("unchecked")
            T gotResponseObjectFromServer = (T) super.getInputObjectStream(socket).readObject();
            if (gotResponseObjectFromServer != null) {
                System.out.println(gotResponseObjectFromServer.toString());
                return gotResponseObjectFromServer;

            } else {
                System.out.println("object response null.");
            }
        } catch (IOException ex) {
            Logger.getLogger(GeneralServer.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Sorry can't connect with " + ip + ":" + port);
            System.out.println("trying again ...");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GeneralServer.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Object not found :  " + ip + ":" + port);
        }
        return null;
    }

    /**
     * Do the server processing
     *
     * @param clientObject
     * @return
     */
    public abstract boolean doProcessInServer(T clientObject);

    /**
     * @return the _Port
     */
    public int getPort() {
        return _Port;
    }

    /**
     * @param _Port the _Port to set
     */
    public void setPort(int _Port) {
        this._Port = _Port;
    }

    /**
     * @return the _ServerIp
     */
    public String getServerIp() {
        return _ServerIp;
    }

    /**
     * @param _ServerIp the _ServerIp to set
     */
    public void setServerIp(String _ServerIp) {
        this._ServerIp = _ServerIp;
    }

    /**
     * @return the _ServerName
     */
    public String getServerName() {
        return _ServerName;
    }

    /**
     * @param _ServerName the _ServerName to set
     */
    public void setServerName(String _ServerName) {
        this._ServerName = _ServerName;
    }

}
