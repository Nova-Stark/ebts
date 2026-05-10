package etbs;

public abstract class Event {
    private int eventId;
    private String eventName;
    private String eventDate;
    private String venue;
    private int seats;
    private double price;

    public Event(int eventId, String eventName, String eventDate, String venue, int seats, double price) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.venue = venue;
        this.seats = seats;
        this.price = price;
    }

    public int getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getVenue() {
        return venue;
    }

    public int getSeats() {
        return seats;
    }

    public double getPrice() {
        return price;
    }

    public void decreaseSeats() {
        if (seats > 0) seats--;
    }

    public void increaseSeats() {
        seats++;
    }

    public abstract String getType();
}
