import java.io.*;
import java.net.*;


/**
 * The Client class connects to a server via TCP socket on localhost:8000.
 * It reads user input from the console, sends it to the server,
 * and displays responses from the server in real time.
 */
public class Client {

    /**
     * Main method that sets up the client, connects to the server,
     * and handles input/output communication.
     * @param args Not used.
     * @throws IOException If an I/O error occurs during socket communication.
     */
    public static void main (String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8000);

        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Empfang in eigenem Thread
        new Thread(() -> {
            String msgFromServer;
            try {
                while ((msgFromServer = in.readLine()) != null) {
                    System.out.println("Server: " + msgFromServer);
                }
            } catch (IOException e) {
                System.out.println("Verbindung zum Server verloren.");
            }
        }).start();

        // Senden
        String userInput;
        while ((userInput = console.readLine()) != null) {
            out.println(userInput);
        }

        socket.close();
    }
}
