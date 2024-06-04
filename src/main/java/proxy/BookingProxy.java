package proxy;
import java.util.Scanner;

public class BookingProxy implements Book{

    private Scanner userInput = new Scanner(System.in);
    private final Booking booking = new Booking();
    @Override
    public void book() {
        System.out.println("Please inter the booking type");
        String bookingType = userInput.nextLine();
        if(booking.checkAvailability(bookingType)) {
            booking.book();
        } else {
            System.out.println("***Your booking cannot be process at the moment");
        }
    }

    private void bookStay() {
        System.out.println("+++Collecting type of stay and size preference");
        System.out.println("+++Adding extra amenities");
        this.book();
    }

    private void bookFlight() {
        System.out.println("+++Collecting depart, destination");
        System.out.println("+++Collecting class");
        System.out.println("+++Adding bags");
        System.out.println("+++Adding extra amenities");
        this.book();
    }
    private void bookCars() {
        System.out.println("+++Collecting pickup, drop-off, locations and dates.");
        System.out.println("+++Collecting type of car");
        System.out.println("+++Adding insurance");
        System.out.println("+++Adding extra amenities");
        this.book();
    }
}
