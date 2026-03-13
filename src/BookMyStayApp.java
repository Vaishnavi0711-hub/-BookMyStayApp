import java.util.HashMap;
import java.util.Map;

/**
 * BookMyStayApp
 *
 * Demonstrates centralized room inventory management
 * using a HashMap to maintain availability of room types.
 *
 * @author Lakshmi M
 * @version 1.0
 */

abstract class Room {

    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price per night: ₹" + price);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 6000);
    }
}

/**
 * RoomInventory
 *
 * Responsible for managing room availability using HashMap.
 */
class RoomInventory {

    private HashMap<String, Integer> availability;

    // Constructor initializes inventory
    public RoomInventory() {
        availability = new HashMap<>();

        availability.put("Single Room", 5);
        availability.put("Double Room", 3);
        availability.put("Suite Room", 2);
    }

    // Retrieve availability
    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int newCount) {
        availability.put(roomType, newCount);
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");

        for (Map.Entry<String, Integer> entry : availability.entrySet()) {
            System.out.println(entry.getKey() + " Available: " + entry.getValue());
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("==== Book My Stay - Hotel Booking System ====");

        // Room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Inventory manager
        RoomInventory inventory = new RoomInventory();

        System.out.println("\nRoom Details:");

        single.displayDetails();
        System.out.println("Available: " + inventory.getAvailability("Single Room"));

        System.out.println();

        doubleRoom.displayDetails();
        System.out.println("Available: " + inventory.getAvailability("Double Room"));

        System.out.println();

        suite.displayDetails();
        System.out.println("Available: " + inventory.getAvailability("Suite Room"));

        // Display centralized inventory
        inventory.displayInventory();
    }
}