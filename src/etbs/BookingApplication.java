package etbs;

import java.util.*;

public class BookingApplication {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        FileManager fm = new FileManager();

        // Create user
        User user = new User("Balu", 12345, 19);

        // Create event list
        ArrayList<Event> events = new ArrayList<>();
        events.add(new Event(1, "Concert", "10-10-2026", "Hyderabad", 5));
        events.add(new Event(2, "Seminar", "15-10-2026", "Chennai", 3));

        // Display events
        System.out.println("Available Events:");
        for (int i = 0; i < events.size(); i++) {
            System.out.println(i + ". " + events.get(i).getEventName()
                    + " (Seats: " + events.get(i).getSeats() + ")");
        }

        // Select event
        System.out.print("Select event index: ");
        int choice = sc.nextInt();

        if (choice < 0 || choice >= events.size()) {
            System.out.println("Invalid choice");
            sc.close();
            return;
        }

        Event selectedEvent = events.get(choice);

        // Book ticket
        System.out.print("Enter seat number: ");
        int seat = sc.nextInt();

        Booking booking = new Booking(user, selectedEvent, seat, 500);

        booking.bookTicket();
        fm.saveBooking(booking);

        // ✅ DISPLAY BOOKING DETAILS (NEW ADDITION)
        System.out.println("\n--- Booking Details ---");
        System.out.println("Booking ID: " + booking.getBookingId());
        System.out.println("User: " + booking.getUser().getName());
        System.out.println("Event: " + booking.getEvent().getEventName());
        System.out.println("Seat: " + booking.getTicket().getSeatNumber());

        // Show remaining seats
        System.out.println("Remaining seats: " + selectedEvent.getSeats());

        // Cancel booking (demo)
        booking.cancelBooking();

        sc.close();
    }
}