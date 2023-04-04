import java.io.*;
import java.net.*;

public class MultiThreadServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        boolean listening = true;

        try {
            serverSocket = new ServerSocket(6007);
            System.out.println("Echo server started.");
        } catch (IOException e) {
            System.err.println("Could not listen on port: 6007.");
            System.exit(-1);
        }

        while (listening) {
            // Wait for a client connection
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket);

            // Start a new thread to handle this client connection
            Thread thread = new Thread(new ClientHandler(clientSocket));
            thread.start();
        }

        serverSocket.close();
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received message: " + inputLine);
                out.println(inputLine);

                if (inputLine.equalsIgnoreCase("bye")) {
                    break;
                }
            }

            out.close();
            in.close();
            clientSocket.close();
            System.out.println("Client disconnected: " + clientSocket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
