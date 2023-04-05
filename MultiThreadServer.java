import java.io.*;
import java.net.*;

public class MultiThreadServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        boolean listening = true;

        try {
            serverSocket = new ServerSocket(8000);
            System.out.println("Waiting for Connection...");
        } catch (IOException e) {
            System.err.println("Could not listen on port: 6007.");
            System.exit(-1);
        }

        while (listening) {
            try {
                // Wait for a client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getPort() + "\nListening for additional connections....");

                // Start a new thread to handle this client connection
                Thread thread = new Thread(new ClientHandler(clientSocket));
                thread.start();
            } catch (SocketException se) {
                System.err.println("SocketException: Connection Reset");
            }
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
                    System.out.println("Echo Client " + clientSocket.getPort() + ": " + inputLine);
                    out.println(inputLine);

                    if (inputLine.equalsIgnoreCase("Done")) {
                        break;
                    }
                }

                out.close();
                in.close();
                clientSocket.close();
                System.out.println("Client disconnected: " + clientSocket.getPort());
            } catch (SocketException se) {
                System.err.println("Connection reset");
                System.out.println("Waiting for Connection");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
