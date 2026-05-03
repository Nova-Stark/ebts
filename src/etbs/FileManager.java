package etbs;

import java.io.FileWriter;

import java.io.IOException;

public class FileManager {

    public void saveBooking(Booking booking) {
        try {
            FileWriter fw = new FileWriter("bookings.txt", true);

            fw.write("Booking ID: " + booking.getBookingId()
                    + ", User: " + booking.getUser().getName()
                    + ", Event: " + booking.getEvent().getEventName()
                    + ", Seat: " + booking.getTicket().getSeatNumber() + "\n");

            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving booking");
        }
    }
}