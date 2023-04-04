import java.io.*;
import java.net.*;

public class Client2 {
    public static void main(String[] args) throws IOException {

        String serverHostname = new String ("localhost");

        System.out.println ("Attemping to connect to host " +
                serverHostname + " on port 6007.");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            //echoSocket = new Socket("taranis", 7);
            echoSocket = new Socket("localhost", 6007);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (Exception e) {
            System.err.println("Unknown host: " + serverHostname);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        System.out.print ("input: ");
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            if (userInput.equalsIgnoreCase("Done")){//if it is "Done", close current connection
                break;
            }
            System.out.println("echo: " + in.readLine());
            System.out.print ("input: ");
        }

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
}
