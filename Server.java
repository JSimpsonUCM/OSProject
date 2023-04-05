import java.net.*;
import java.io.*;

public class Server
{
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        serverSocket = new ServerSocket(8000);

        Socket clientSocket = null;
        System.out.println("Waiting for connection.....");

        while (true) {
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Connection successful");
                System.out.println("Waiting for input.....");

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Server: " + inputLine);
                    out.println(inputLine);

                    if (inputLine.equals("Done")){//if it is "Done",close current connection
                        break;
                    }
                }
                out.close();
                in.close();
                clientSocket.close();
                serverSocket.close();

                if (inputLine != null && inputLine.equals("Done")) {
                    break;
                }
            }catch (SocketException se) {
                System.err.println("SocketException: Connection Reset");
                System.out.println("Waiting for Connection");
            }
        }
    }
}