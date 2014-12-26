/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestingCode;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

class ExampleObject implements Serializable {

    private static final long serialVersionUID = -113123132123L;
    public String str = "hello";
    public int num = 23;

    public void print() {
        System.out.println("str :" + str);
        System.out.println("num :" + num);
    }
}

/**
 *
 * @author Alim
 */
public class Client {


    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Socket socket = new Socket(Server.server, Server.port);
        ObjectOutputStream writeToServer = new ObjectOutputStream(socket.getOutputStream());

        ExampleObject obj = new ExampleObject();

        writeToServer.writeObject(obj);
        ObjectInputStream readFromServer = new ObjectInputStream(socket.getInputStream());

        // now get the processed data from server
        obj = (ExampleObject) readFromServer.readObject();
        System.out.println("Got processed from server:");
        obj.print();

    }
}
