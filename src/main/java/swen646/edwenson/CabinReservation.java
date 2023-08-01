package swen646.edwenson;

import java.util.Date;

public class CabinReservation extends Reservation {
    private Boolean hasFullKitchen;
    private Boolean hasLoft;

    private static final double KITCHEN_PRICE = 20.0;
    private static final double EXTRA_ROOM_PRICE = 5.0;


    public CabinReservation() {
        super();
    }

    public CabinReservation(String accNum, String resvNum, String physicalAddr, String mailingAddr, Date checkInDate, Integer lengthOfStay, Integer bedQty, Integer bedroomQty, Integer bathQty, Integer sqFeet, String status, Boolean hasFullKitchen, Boolean hasLoft) {
        super(accNum, resvNum, physicalAddr, mailingAddr, checkInDate, lengthOfStay, bedQty, bedroomQty, bathQty, sqFeet, status);
        this.hasFullKitchen = hasFullKitchen;
        this.hasLoft = hasLoft;
    }

    /**
     * This method will set price for CabinReservation
     * It will use the base price and process price calculation for a Cabin
     * by adding a fee of $20 or full kitchen and $5 for each additional bathroom
     */
    public void updatePrice() {
        super.basePrice();
        double addRoomPrice = (super.getBedroomQty() -1) * EXTRA_ROOM_PRICE;
        super.setPrice(super.getPrice() + KITCHEN_PRICE + addRoomPrice);
    }

    public Boolean getHasLoft() {
        return hasLoft;
    }

    public void setHasLoft(Boolean hasLoft) {
        this.hasLoft = hasLoft;
    }

    public Boolean getHasFullKitchen() {
        return hasFullKitchen;
    }

    public void setHasFullKitchen(Boolean hasFullKitchen) {
        this.hasFullKitchen = hasFullKitchen;
    }

    @Override
    public String toString() {
        return "{\"Reservation\":" + super.toString() +
                ",\"hasFullKitchen\":\"" + this.hasFullKitchen + "\"" +
                ",\"hasLoft\":\"" + this.hasLoft + "\"}";
    }
}
