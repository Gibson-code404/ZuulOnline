import java.util.HashMap;
import java.util.Set;

public class Items {
    private String name;
    private String description;

    public Items() {
    }

    public Items(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return name + ": " + description;
    }
}
