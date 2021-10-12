package javasocketclient;

/**
 *
 * @author 30039802 Caspian Maclean
 * 
 * 
 */

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class JavaSocketClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String host = "localhost";
        int port = 1234;
        // Socket socket;
        try (Socket socket = new Socket(host, port)) {
            
        } catch (IOException e) {
            // UnknownHostException is a subclass of  IOException
            System.err.println("Exception: " + e);
        }
    }
    
}
