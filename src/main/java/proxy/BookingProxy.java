package proxy;
import java.util.Scanner;

public class BookingProxy implements Book{

    private Scanner userInput = new Scanner(System.in);
    private final Booking booking = new Booking();
    @Override
    public void book() {
        System.out.println("Please enter the booking type");
        String bookingType = userInput.nextLine();
        if(booking.checkAvailability(bookingType)) {
            if (bookingType.equalsIgnoreCase("flight")) {
                booking.bookFlight();
            } else if (bookingType.equalsIgnoreCase("cars")) {
                booking.bookCars();
            } else if (bookingType.equalsIgnoreCase("stay")) {
                booking.bookStay();
            } else {
                System.out.println("***Please provide a valid type booking");
            }
        } else {
            System.out.println("***Booking cannot be processed at the moment");
        }
    }
}
