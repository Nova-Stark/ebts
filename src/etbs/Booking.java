package etbs;

public class Booking implements Bookable {
    private String bookingId;
    private User user;
    private Event event;
    private Ticket ticket;

    public Booking(String bookingId, User user, Event event, int seatNumber, double price) {
        this.bookingId = bookingId;
        this.user = user;
        this.event = event;
        this.ticket = new Ticket(Integer.parseInt(bookingId), seatNumber, price);
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

    public String getBookingId() {
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
