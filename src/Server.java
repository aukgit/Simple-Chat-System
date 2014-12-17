
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    private static ServerSocket severSocket;
    private static Scanner in;

    public static void main(String[] args) throws IOException {
        in = new Scanner(System.in);
        System.out.println("(Server) Enter your server port no:");
        int port = in.nextInt();
        System.out
                .println("Server Estabilsh Connection On Localhost or own ip with port : "
                        + port);
        severSocket = new ServerSocket(port);

        System.out.println("Now you can run your client app.");

        while (true) {
            Socket connectionSocket = severSocket.accept();

            // for taking input from client
            InputStream inputStream = connectionSocket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(
                    inputStream);
            BufferedReader inputFromClient = new BufferedReader(
                    inputStreamReader);
            // for giving output to the client.
            OutputStream outputStream = connectionSocket.getOutputStream();
            // output to client, to send data to the server
            DataOutputStream dataOutputStream = new DataOutputStream(
                    outputStream);
			// get output from server

            String readingLineFromClientSocket = inputFromClient.readLine();
            // sending data to client
            String modified = doOperation(readingLineFromClientSocket);
            // send data to client
            dataOutputStream.writeBytes(modified + "\n");

        }

    }

    private static String doOperation(String readingLineFromClientSocket) {
        String[] array = readingLineFromClientSocket.split(" ");
        StringBuilder strBuilder = new StringBuilder(array.length);

        for (int i = array.length - 1; i >= 0; i--) {
            String s = charReverse(array[i]);
            strBuilder.append(s);
            strBuilder.append(" ");
        }

        return strBuilder.toString();

    }

    private static String charReverse(String str) {
        return new StringBuilder(str).reverse().toString();
    }
}
