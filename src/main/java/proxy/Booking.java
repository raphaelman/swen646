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
    void bookStay() {
        System.out.println("+++Collecting type of stay and size preference");
        System.out.println("+++Adding extra amenities");
        this.book();
    }
    void bookFlight() {
        System.out.println("+++Collecting depart, destination");
        System.out.println("+++Collecting class");
        System.out.println("+++Adding bags");
        System.out.println("+++Adding extra amenities");
        this.book();
    }
    void bookCars() {
        System.out.println("+++Collecting pickup, drop-off, locations and dates.");
        System.out.println("+++Collecting type of car");
        System.out.println("+++Adding insurance");
        System.out.println("+++Adding extra amenities");
        this.book();
    }
}
