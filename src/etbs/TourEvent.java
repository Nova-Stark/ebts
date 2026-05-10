package etbs;

public class TourEvent extends Event {
    public TourEvent(int eventId, String eventName, String eventDate, String venue, int seats, double price) {
        super(eventId, eventName, eventDate, venue, seats, price);
    }

    @Override
    public String getType() {
        return "Tour";
    }
}
