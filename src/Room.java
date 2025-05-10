import java.util.HashMap;
import java.util.Set;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Room {
    private String description;
    private HashMap<String, Room> exits;
    private HashMap<String, String> items;

    public Room(String description) {
        this.description = description;
        exits = new HashMap<>();
        items = new HashMap<>();
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public void setItem(String itemName, String itemDescription) {
        items.put(itemName, itemDescription);
    }

    public String getShortDescription() {
        return description;
    }

    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString();
    }

    public String getExitString() {
    if (exits.isEmpty()) {
        return "No exits available.";
    }
    
    String returnString = "Exits:";
    Set<String> keys = exits.keySet();
    for (String exit : keys) {
        returnString += " " + exit;
    }
    return returnString;
        }


    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public String getItemDescription(String itemName) {
        return items.get(itemName);
    }

    public void removeItem(String itemName) {
        items.remove(itemName);
    }
    
    public String getItems() {
        if (items.isEmpty()) {
            return "No items in this room.";
        }
    
        StringBuilder itemsList = new StringBuilder("Items in this room:");
        for (String itemName : items.keySet()) {
            itemsList.append("\n- ").append(itemName).append(": ").append(items.get(itemName));
        }
        return itemsList.toString();
    }

}
