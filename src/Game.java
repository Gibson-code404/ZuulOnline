import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;

public class Game {
    private Parser parser;
    private Room currentRoom;
    private HashMap<String, Items> inventory;
    private PrintWriter out;



    /**
     * Constructs a Game instance with input and output streams.
     * @param input  Input stream from the user (typically System.in).
     * @param output Output stream to display messages (typically System.out).
     */
    public Game(InputStream input, OutputStream output) {
        this.out = new PrintWriter(output, true);
        this.parser = new Parser(input, output);
        this.createRooms();
        this.inventory = new HashMap<>();
    }


     /**
     * Initializes all rooms and their connections, adds items to each room,
     * and sets the starting room.
     */
    private void createRooms() {
        Room outside, commonRoom, pub, redRoom, office, toilette;

        outside = new Room("outside the main entrance of the university");
        commonRoom = new Room("in a common room that everyone can chill");
        pub = new Room("in the pub");
        redRoom = new Room("in a room with red light");
        office = new Room("in the manager's office");
        toilette = new Room("no description needed it's the wc");

        outside.setExit("east", commonRoom);
        outside.setExit("south", redRoom);
        outside.setExit("west", pub);

        commonRoom.setExit("west", outside);
        commonRoom.setExit("south", office);

        pub.setExit("east", outside);
        pub.setExit("south", toilette);

        redRoom.setExit("north", outside);
        redRoom.setExit("east", office);
        redRoom.setExit("west", toilette);

        office.setExit("west", redRoom);
        office.setExit("north", commonRoom);

        toilette.setExit("north", pub);
        toilette.setExit("east", redRoom);

        currentRoom = outside;

        outside.setItem("keys", "A keyset lying on the ground.");
        commonRoom.setItem("Glasses", "A pair of glasses on the seat.");
        pub.setItem("Bottle", "A bottle of water on the table.");
        redRoom.setItem("Phone", "A phone on the desk.");
        office.setItem("Pen", "A pen on the desk.");
        toilette.setItem("Towel", "A towel hanging on the wall.");

        out.println(currentRoom.getLongDescription());
    }

    public void play() {
        printWelcome();

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        out.println("Thank you for playing.  Good bye.");
    }

    private void printWelcome() {
        out.println();
        out.println("You just woke up and have no idea what's going on.");
        out.println("Looks like you've lost all your items ... maybe it's a good idea to go look for them.");
        out.println("Type 'help' if you need help.");
        out.println();
        out.println(currentRoom.getLongDescription());
    }

    private void look() {
        out.println(currentRoom.getLongDescription());
    }

    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        } else if (commandWord.equals("go")) {
            goRoom(command);
        } else if (commandWord.equals("check")) {
            checkRoom(command);
        } else if (commandWord.equals("items")) {
            showItems();
        } else if (commandWord.equals("pick")) {
            pickItem(command);
        } else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        } else if (commandWord.equals("look")) {
            look();
        }

        return wantToQuit;
    }

    private void printHelp() {
        out.println("You were drunk last night. Lost so many things.");
        out.println("U need to find them.");
        out.println();
        out.println("Your command words are:");
        out.println(parser.getCommandList());
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            out.println("There is no door!");
        } else {
            currentRoom = nextRoom;
            out.println(currentRoom.getLongDescription());
        }
    }

    private void checkRoom(Command command) {
        if (command.hasSecondWord()) {
            out.println("Check what?");
            return;
        }

        String itemsInRoom = currentRoom.getItems();

        if (itemsInRoom.equals("No items in this room.")) {
            out.println("There are no items in this room.");
        } else {
            out.println(itemsInRoom);
        }
    }

    private void showItems() {
        if (inventory.isEmpty()) {
            out.println("You have no items.");
        } else {
            out.println("Your items:");
            for (String key : inventory.keySet()) {
                Items item = inventory.get(key);
                out.println(item.getName() + ": " + item.getDescription());
            }
        }
    }

    private void pickItem(Command command) {
        if (!command.hasSecondWord()) {
            out.println("Pick up what?");
            return;
        }

        String itemName = command.getSecondWord();
        String itemDescription = currentRoom.getItemDescription(itemName);
        boolean hasAllItems = false;

        if (itemDescription == null) {
            out.println("There is no such item in the room.");
        } else {
            Items item = new Items(itemName, itemDescription);
            inventory.put(itemName, item);
            currentRoom.removeItem(itemName);
            out.println("You picked up: " + itemName);
            if (inventory.size() == 6) {
                hasAllItems = true;
            }
        }

        if (hasAllItems) {
            out.println("Congratulations! With that you have gathered all your belongings!");
            out.println("Your memories slowly come back...");
            out.println("Seems like you partied a little too hard.");
            out.println("Better be careful next time!");
        }
    }

    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            out.println("Quit what?");
            return false;
        } else {
            return true;
        }
    }
}
