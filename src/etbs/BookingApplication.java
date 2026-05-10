package etbs;

import java.util.*;

public class BookingApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EventCSVManager eventManager = new EventCSVManager("events.csv");
        BookingCSVManager bookingManager = new BookingCSVManager("bookings.csv");

        User user = new User("Balu", 12345, 19);
        List<Event> events = eventManager.loadEvents();

        System.out.println("Available Events:");
        for (int i = 0; i < events.size(); i++) {
            Event e = events.get(i);
            System.out.println(i + ". [" + e.getType() + "] " + e.getEventName() + 
                               " at " + e.getVenue() + " (Seats: " + e.getSeats() + ", Price: $" + e.getPrice() + ")");
        }

        System.out.print("Select event index: ");
        int choice = sc.nextInt();
        if (choice < 0 || choice >= events.size()) {
            System.out.println("Invalid choice");
            sc.close();
            return;
        }

        Event selectedEvent = events.get(choice);
        System.out.print("Enter seat number: ");
        int seat = sc.nextInt();

        String bookingId = String.valueOf(System.currentTimeMillis() % 100000);
        Booking booking = new Booking(bookingId, user, selectedEvent, seat, selectedEvent.getPrice());

        if (selectedEvent.getSeats() > 0) {
            booking.bookTicket();
            bookingManager.addBooking(bookingId, user.getName(), selectedEvent.getEventId(), seat, selectedEvent.getPrice());
            eventManager.saveEvents(events);

            System.out.println("\n--- Booking Details ---");
            System.out.println("Booking ID: " + booking.getBookingId());
            System.out.println("User: " + booking.getUser().getName());
            System.out.println("Event: " + booking.getEvent().getEventName());
            System.out.println("Seat: " + booking.getTicket().getSeatNumber());
            System.out.println("Remaining seats for " + selectedEvent.getEventName() + ": " + selectedEvent.getSeats());
        } else {
            System.out.println("No seats available!");
        }

        sc.close();
    }
}
