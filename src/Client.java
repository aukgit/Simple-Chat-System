
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static Scanner in;

    public static void main(String[] args) throws IOException {
        in = new Scanner(System.in);
        System.out.println("(Client) Enter Server Ip to Connect to(Empty will give localhost):");
        String ip = in.nextLine();
        System.out.println("(Client) Enter your server port no:");
        int port = in.nextInt();
        if (ip == null || ip.length() == 0) {
            ip = "localhost";
        }
        System.out.println("Connecting to " + ip + ":" + port);
        // for taking input from client
        // InputStream inputStream = connectionSocket.getInputStream();
        try (Socket socket = new Socket(ip, port)) {
            // for taking input from client
            // InputStream inputStream = connectionSocket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
            BufferedReader inputFromClient = new BufferedReader(inputStreamReader);
            // for giving output to the client.
            OutputStream outputStream = socket.getOutputStream();
            // output to client, to send data to the server
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            // get output from server
            InputStream serverInputStream = socket.getInputStream();
            InputStreamReader inputStreamReaderFromServer = new InputStreamReader(
                    serverInputStream);
            BufferedReader bufferReader = new BufferedReader(
                    inputStreamReaderFromServer);
            System.out.println("(Client) Give input:");
            String readingLineFromUser = inputFromClient.readLine();
            // sending data to server
            dataOutputStream.writeBytes(readingLineFromUser + "\n");

            String getStringFromServer = bufferReader.readLine();

            System.out.println("Got input from server (in client):"
                    + getStringFromServer);
        }

    }
}
