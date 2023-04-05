import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {

        String serverHostname = "localhost";

        System.out.println ("Attempting to connect to host " +
                serverHostname + " on port 6007.");
        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            //echoSocket = new Socket("taranis", 7);
            echoSocket = new Socket("localhost", 8000);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            System.out.println("Successfully Connected to: " + serverHostname);
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + serverHostname);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        System.out.print ("input: ");
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            if (userInput.equals("Done")){//if it is "Done", close current connection
                break;
            }
            System.out.println("Echo: " + in.readLine());
            System.out.print ("Input: ");
        }
        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
}
