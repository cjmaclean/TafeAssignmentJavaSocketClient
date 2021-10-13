package javasocketclient;

/**
 *
 * @author 30039802 Caspian Maclean
 *
 * Question 4 – JMC wishes to have a standard login functionality for all their
 * terminals around the ship, this should be accomplished via logging into a
 * central server to test user and password combinations (you must have at least
 * one administrator password setup) You must create a two Server Client
 * program; each must use two different IPC mechanisms to communicate. Your
 * program must have a login that uses standard hashing techniques
 *
 * This is the client program for the system using sockets.
 *
 * Socket connection code and user input-reading code based on provided example
 * "ClientServerDemo"
 *
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class JavaSocketClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String host = "localhost";
        int port = 1234;
        // Socket socket;
        try ( Socket socket = new Socket(host, port)) {
            DataInputStream inStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());

            session(inStream, outStream);

        } catch (IOException e) {
            // UnknownHostException is a subclass of  IOException
            System.err.println("Exception: " + e);
        }
    }

    private static void session(DataInputStream inStream, DataOutputStream outStream) {
        // protocol format.
        // client -> server - send lines from user input.
        // server -> client.
        // message will have a number, then that number of strings.
        // command strings in protocol:
        String ready = "ready";
        String message = "message";
        String query = "query";

        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                loopUntilReady:
                do {
                    int inCount = inStream.readInt();
                    String command, inString2;
                    //System.out.print("count: " + inCount + ". ");
                    switch (inCount) {
                        case 1:
                            command = inStream.readUTF();
                            if (command.equalsIgnoreCase(ready)) {
                                System.out.print("> ");
                                break loopUntilReady;
                            } else {
                                System.err.println("Unknown response from server: " + command);
                            }
                        case 2:
                            command = inStream.readUTF();
                            inString2 = inStream.readUTF();
                            if (command.equalsIgnoreCase(message)) {
                                System.out.println("Server >>> " + inString2);
                            } else if (command.equalsIgnoreCase(query)) {
                                // Queries are printed without a new line
                                System.out.print(inString2 + "> ");
                                break loopUntilReady;

                            } else {
                                System.err.println("Unknown response from server: " + command);
                            }
                    }
                } while (true);

                String userInputLine = sc.nextLine();
                if (userInputLine.equalsIgnoreCase("quit")) {
                    System.exit(0); // stop client chatting as well.
                } else {
                    outStream.writeUTF(userInputLine);
                }

            } catch (IOException ex) {

            }
        }
    }

}
