package etbs;

import java.io.*;
import java.util.*;

public class EventCSVManager {
    private String filePath;

    public EventCSVManager(String filePath) {
        this.filePath = filePath;
    }

    public List<Event> loadEvents() {
        List<Event> events = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String type = data[1];
                String name = data[2];
                String date = data[3];
                String venue = data[4];
                int seats = Integer.parseInt(data[5]);
                double price = Double.parseDouble(data[6]);

                if (type.equalsIgnoreCase("Movie")) {
                    events.add(new MovieEvent(id, name, date, venue, seats, price));
                } else if (type.equalsIgnoreCase("Tour")) {
                    events.add(new TourEvent(id, name, date, venue, seats, price));
                } else if (type.equalsIgnoreCase("Concert")) {
                    events.add(new ConcertEvent(id, name, date, venue, seats, price));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading events: " + e.getMessage());
        }
        return events;
    }

    public void saveEvents(List<Event> events) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println("id,type,name,date,venue,seats,price");
            for (Event e : events) {
                pw.printf("%d,%s,%s,%s,%s,%d,%.2f%n",
                    e.getEventId(), e.getType(), e.getEventName(),
                    e.getEventDate(), e.getVenue(), e.getSeats(), e.getPrice());
            }
        } catch (IOException e) {
            System.err.println("Error saving events: " + e.getMessage());
        }
    }
}
