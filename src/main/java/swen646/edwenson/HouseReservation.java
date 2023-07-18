package swen646.edwenson;

import java.util.Date;

public class HouseReservation extends Reservation {
    private Integer floorQty;

    public HouseReservation() {
        super();
    }

    public HouseReservation(String accNum, String resvNum, String physicalAddr, String mailingAddr, Date checkInDate, Integer lengthOfStay, Integer bedQty, Integer bedroomQty, Integer bathQty, Integer sqFeet, String status, Integer floorQty) {
        super(accNum, resvNum, physicalAddr, mailingAddr, checkInDate, lengthOfStay, bedQty, bedroomQty, bathQty, sqFeet, status);
        this.floorQty = floorQty;
    }

    /**
     * This method will be used to set price for the HouseReservation
     */
    public void updatePrice() {
        super.basePrice();
    }

    public Integer getFloorQty() {
        return floorQty;
    }

    public void setFloorQty(Integer floorQty) {
        this.floorQty = floorQty;
    }

    @Override
    public String toString() {
        return "{\"Reservation\":" + super.toString() +
                ",\"floorQty\":\"" + this.floorQty + "\"}";
    }
}
