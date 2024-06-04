package proxy;


public class Booking implements Book{

    @Override
    public void book(){
        System.out.println("+++Collecting booking details+++");
        System.out.println("+++Processing booking+++");
        System.out.println("+++Booking complete+++");
    }

    public boolean checkAvailability(String bookingType) {
        System.out.println("Checking if booking type is available");
        return true;
    }
}
