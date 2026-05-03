package etbs;

public class Event {
    private int eventId;
    private String eventName;
    private String eventDate;
    private String venue;
    private int seats;

    public Event(int eventId, String eventName, String eventDate, String venue, int seats) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.venue = venue;
        this.seats = seats;
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

    public void decreaseSeats() {
        if (seats > 0) seats--;
    }

    public void increaseSeats() {
        seats++;
    }
}