import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * BookMyStayApp
 *
 * Demonstrates booking request intake using a Queue.
 * Requests are stored in arrival order and processed later.
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
 * Maintains centralized room availability.
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
 * Reservation
 * Represents a guest booking request.
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

    public void displayRequest() {
        System.out.println("Guest: " + guestName + " requested " + roomType);
    }
}

/**
 * BookingRequestQueue
 * Manages incoming booking requests.
 */
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add request to queue
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Display queued requests
    public void displayRequests() {
        System.out.println("\nCurrent Booking Request Queue:");

        for (Reservation r : requestQueue) {
            r.displayRequest();
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("==== Book My Stay - Hotel Booking System ====");

        // Inventory initialization
        RoomInventory inventory = new RoomInventory();

        // Booking request queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Guests submit booking requests
        Reservation r1 = new Reservation("Amit", "Single Room");
        Reservation r2 = new Reservation("Priya", "Suite Room");
        Reservation r3 = new Reservation("Rahul", "Double Room");

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display queue (FIFO order)
        bookingQueue.displayRequests();
    }
}