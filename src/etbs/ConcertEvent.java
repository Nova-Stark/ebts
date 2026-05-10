package etbs;

public class ConcertEvent extends Event {
    public ConcertEvent(int eventId, String eventName, String eventDate, String venue, int seats, double price) {
        super(eventId, eventName, eventDate, venue, seats, price);
    }

    @Override
    public String getType() {
        return "Concert";
    }
}
