import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
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
