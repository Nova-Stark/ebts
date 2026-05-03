package etbs;

public class Ticket {
    private int ticketId;   // ✅ THIS WAS MISSING
    private int seatNumber;
    private double price;

    public Ticket(int ticketId, int seatNumber, double price) {
        this.ticketId = ticketId;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    public int getTicketId() {
        return ticketId;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public double getPrice() {
        return price;
    }
}