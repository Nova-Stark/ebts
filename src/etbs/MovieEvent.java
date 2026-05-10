package etbs;

public class MovieEvent extends Event {
    public MovieEvent(int eventId, String eventName, String eventDate, String venue, int seats, double price) {
        super(eventId, eventName, eventDate, venue, seats, price);
    }

    @Override
    public String getType() {
        return "Movie";
    }
}
