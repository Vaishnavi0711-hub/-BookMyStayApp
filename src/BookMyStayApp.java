import java.util.HashMap;
import java.util.Map;

/**
 * BookMyStayApp
 *
 * Demonstrates room search functionality with centralized
 * inventory management and read-only access to availability.
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

    public String getRoomType() {
        return roomType;
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
 * Stores and exposes room availability using HashMap.
 */
class RoomInventory {

    private HashMap<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single Room", 5);
        availability.put("Double Room", 3);
        availability.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllAvailability() {
        return availability;
    }
}

/**
 * RoomSearchService
 * Handles read-only search operations.
 */
class RoomSearchService {

    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms(Room[] rooms) {

        System.out.println("\nAvailable Rooms:");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Filter rooms with zero availability
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available);
                System.out.println("----------------------------");
            }
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

        Room[] rooms = {single, doubleRoom, suite};

        // Inventory
        RoomInventory inventory = new RoomInventory();

        // Search service
        RoomSearchService searchService = new RoomSearchService(inventory);

        // Guest searches available rooms
        searchService.searchAvailableRooms(rooms);
    }
}