package swen646.edwenson;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class HotelReservation extends Reservation {
    private Boolean hasKitchenette;

    public HotelReservation() { super();}
    
    public HotelReservation(String accNum, String resvNum, String physicalAddr, String mailingAddr, Date checkInDate, Integer lengthOfStay, Integer bedQty, Integer bedroomQty, Integer bathQty, Integer sqFeet, String status, Boolean hasKitchenette) {
        super(accNum, resvNum, physicalAddr, mailingAddr, checkInDate, lengthOfStay, bedQty, bedroomQty, bathQty, sqFeet, status);
        this.hasKitchenette = hasKitchenette;
    }

    /**
     * This method will set price for HotelReservation
     * It will use the base price and process price calculation for a Hotel
     * by adding a flat fee of $50 and possible kitchenette charge
     */
    public void updatePrice() {
        super.basePrice();
        double kitchenettePrice = hasKitchenette ? 10.0 : 0.0;
        super.setPrice(super.getPrice() + 50.0 + kitchenettePrice);
    }

    public Boolean getHasKitchenette() {
        return hasKitchenette;
    }

    public void setHasKitchenette(Boolean hasKitchenette) {
        this.hasKitchenette = hasKitchenette;
    }

    @Override
    public String toString() {
        return "{\"Reservation\":" + super.toString() +
                ",\"hasKitchenette\":\"" + this.hasKitchenette + "\"}";
    }
}