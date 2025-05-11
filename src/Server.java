import java.io.*;
import java.net.*;

/**
 * Server class that listens for incoming client connections on port 8000.
 * For each client, it starts a new thread that runs a text-based adventure game session.
 */
public class Server {
    public static void main(String[] args) throws IOException {
        int port = 8000;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server läuft auf Port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client verbunden: " + clientSocket);

            // Thread für jeden Client
            new Thread(() -> {
                try {
                    InputStream input = clientSocket.getInputStream();
                    OutputStream output = clientSocket.getOutputStream();

                    Game game = new Game(input, output);
                    game.play();

                    clientSocket.close();
                    System.out.println("Client getrennt.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
