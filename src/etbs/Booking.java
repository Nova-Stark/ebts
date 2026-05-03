package etbs;

public class Booking implements Bookable {

    private static int counter = 1;

    private int bookingId;
    private User user;
    private Event event;
    private Ticket ticket;

    public Booking(User user, Event event, int seatNumber, double price) {
        this.bookingId = counter++;
        this.user = user;
        this.event = event;
        this.ticket = new Ticket(bookingId, seatNumber, price);
    }

    @Override
    public void bookTicket() {
        if (event.getSeats() > 0) {
            event.decreaseSeats();
            System.out.println("Ticket booked successfully!");
        } else {
            System.out.println("No seats available!");
        }
    }

    public void cancelBooking() {
        event.increaseSeats();
        System.out.println("Booking cancelled");
    }

    public int getBookingId() {
        return bookingId;
    }

    public User getUser() {
        return user;
    }

    public Event getEvent() {
        return event;
    }

    public Ticket getTicket() {
        return ticket;
    }
}