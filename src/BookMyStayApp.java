import java.util.*;

/**
 * BookMyStayApp
 *
 * Demonstrates reservation confirmation and safe room allocation.
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
 * Inventory Service
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

    public void decrementRoom(String roomType) {
        int count = availability.get(roomType);
        availability.put(roomType, count - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : availability.entrySet()) {
            System.out.println(entry.getKey() + " Available: " + entry.getValue());
        }
    }
}

/**
 * Reservation Request
 */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/**
 * Booking Request Queue (FIFO)
 */
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean hasRequests() {
        return !queue.isEmpty();
    }
}

/**
 * Booking Service
 * Handles room allocation and confirmation
 */
class BookingService {

    private RoomInventory inventory;

    // Set ensures unique room IDs
    private Set<String> allocatedRoomIds;

    // Map room type -> allocated room IDs
    private Map<String, Set<String>> roomAllocations;

    private int roomCounter = 100;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        allocatedRoomIds = new HashSet<>();
        roomAllocations = new HashMap<>();
    }

    public void processRequest(Reservation reservation) {

        String roomType = reservation.getRoomType();

        if (inventory.getAvailability(roomType) > 0) {

            // Generate unique room ID
            String roomId = roomType.replace(" ", "").substring(0,2).toUpperCase() + roomCounter++;

            // Ensure uniqueness
            if (!allocatedRoomIds.contains(roomId)) {

                allocatedRoomIds.add(roomId);

                roomAllocations.putIfAbsent(roomType, new HashSet<>());
                roomAllocations.get(roomType).add(roomId);

                // Update inventory
                inventory.decrementRoom(roomType);

                System.out.println("Reservation Confirmed!");
                System.out.println("Guest: " + reservation.getGuestName());
                System.out.println("Room Type: " + roomType);
                System.out.println("Room ID: " + roomId);
                System.out.println("-----------------------");

            }

        } else {
            System.out.println("Sorry " + reservation.getGuestName() +
                    ", no rooms available for " + roomType);
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("==== Book My Stay - Hotel Booking System ====");

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue requestQueue = new BookingRequestQueue();
        BookingService bookingService = new BookingService(inventory);

        // Guest booking requests
        requestQueue.addRequest(new Reservation("Amit", "Single Room"));
        requestQueue.addRequest(new Reservation("Priya", "Suite Room"));
        requestQueue.addRequest(new Reservation("Rahul", "Double Room"));

        // Process requests FIFO
        while (requestQueue.hasRequests()) {

            Reservation request = requestQueue.getNextRequest();
            bookingService.processRequest(request);

        }

        inventory.displayInventory();
    }
}