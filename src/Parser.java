import java.io.*;
import java.util.Scanner;

public class Parser {
    private CommandWords commands;
    private Scanner reader;
    private PrintWriter out;

    /**
     * Standard-Konstruktor (Konsole)
     */
    public Parser() {
        this(System.in, System.out);
    }

    /**
     * Konstruktor für Streams (z. B. über Socket)
     */
    public Parser(InputStream in, OutputStream outStream) {
        commands = new CommandWords();
        reader = new Scanner(in);
        out = new PrintWriter(outStream, true);
    }

    /**
     * Liest den nächsten Befehl
     */
    public Command getCommand() {
        String word1 = null;
        String word2 = null;

        out.print("> ");
        out.flush();

        if (!reader.hasNextLine()) {
            return new Command(null, null);
        }

        String inputLine = reader.nextLine();
        Scanner tokenizer = new Scanner(inputLine);

        if (tokenizer.hasNext()) {
            word1 = tokenizer.next();
            if (tokenizer.hasNext()) {
                word2 = tokenizer.next();
            }
        }

        if (commands.isCommand(word1)) {
            return new Command(word1, word2);
        } else {
            return new Command(null, word2);
        }
    }

    /**
     * Gibt alle gültigen Kommandos aus
     */
    public void showCommands() {
        out.println(commands.getCommandList());
    }

    public String getCommandList() {
        return commands.getCommandList();
    }
}
