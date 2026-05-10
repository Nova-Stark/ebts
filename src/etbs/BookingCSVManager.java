package etbs;

import java.io.*;

public class BookingCSVManager {
    private String filePath;

    public BookingCSVManager(String filePath) {
        this.filePath = filePath;
    }

    public void addBooking(String bookingId, String userId, int eventId, int seatNumber, double price) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath, true))) {
            pw.printf("%s,%s,%d,%d,%.2f%n", bookingId, userId, eventId, seatNumber, price);
        } catch (IOException e) {
            System.err.println("Error adding booking: " + e.getMessage());
        }
    }
}
