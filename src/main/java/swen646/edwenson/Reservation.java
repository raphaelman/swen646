package swen646.edwenson;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class Reservation {
    public static final String COMPLETED = "completed";
    public static final String DRAFT = "draft";
    public static final String CANCELLED = "cancelled";
    private String accNum;
    private String resvNum;
    private String physicalAddr;
    private String mailingAddr;
    private Date checkInDate;
    private Integer lengthOfStay;
    private Integer bedQty;
    private Integer bedroomQty;
    private Integer bathQty;
    private Integer sqFeet;
    private Double price;
    private String status;

    public Reservation(String accNum, String resvNum, String physicalAddr, String mailingAddr, Date checkInDate, Integer lengthOfStay, Integer bedQty, Integer bedroomQty, Integer bathQty, Integer sqFeet, String status) {
        this.accNum = accNum;
        this.resvNum = resvNum;
        this.physicalAddr = physicalAddr;
        this.mailingAddr = mailingAddr;
        this.checkInDate = checkInDate;
        this.lengthOfStay = lengthOfStay;
        this.bedQty = bedQty < 2 ? 2 : bedQty;
        this.bedroomQty = bedroomQty < 1 ? 1: bedroomQty;
        this.bathQty = bathQty < 1 ? 1 : bathQty;
        this.sqFeet = sqFeet;
        this.status = status;
    }

    /**
     * This method will set price for Reservation
     * It will use the base price of $120  and possible charge for larger than 900 sqFeet lodging
     */
    public void basePrice() {
        double basePrice = 120.0;
        this.price = sqFeet > 900 ? (basePrice + 15.0) * lengthOfStay : basePrice * lengthOfStay;
    }

    public String getAccNum() {
        return accNum;
    }

    public void setAccNum(String accNum) {
        this.accNum = accNum;
    }

    public String getResvNum() {
        return resvNum;
    }

    public void setResvNum(String resvNum) {
        this.resvNum = resvNum;
    }

    public String getPhysicalAddr() {
        return physicalAddr;
    }

    public void setPhysicalAddr(String physicalAddr) {
        this.physicalAddr = physicalAddr;
    }

    public String getMailingAddr() {
        return mailingAddr;
    }

    public void setMailingAddr(String mailingAddr) {
        this.mailingAddr = mailingAddr;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Integer getLengthOfStay() {
        return lengthOfStay;
    }

    public void setLengthOfStay(Integer lengthOfStay) {
        this.lengthOfStay = lengthOfStay;
    }

    public Integer getBedQty() {
        return bedQty;
    }

    public void setBedQty(Integer bedQty) {
        this.bedQty = bedQty;
    }

    public Integer getBedroomQty() {
        return bedroomQty;
    }

    public void setBedroomQty(Integer bedroomQty) {
        this.bedroomQty = bedroomQty;
    }

    public Integer getBathQty() {
        return bathQty;
    }

    public void setBathQty(Integer bathQty) {
        this.bathQty = bathQty;
    }

    public Integer getSqFeet() {
        return sqFeet;
    }

    public void setSqFeet(Integer sqFeet) {
        this.sqFeet = sqFeet;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{" +
                "\"accNum\":\"" + this.accNum + '\"' +
                ",\"resvNum\":\"" + this.resvNum + '\"' +
                ",\"physicalAddr\":\"" + this.physicalAddr + '\"' +
                ",\"mailingAddr\":\"" + this.mailingAddr + '\"' +
                ",\"checkInDate\":\"" + this.checkInDate + '\"' +
                ",\"lengthOfStay\":" + this.lengthOfStay +
                ",\"bedQty\":" + this.bedQty +
                ",\"bedroomQty\":" + this.bedroomQty +
                ",\"bathQty\":" + this.bathQty +
                ",\"sqFeet\":" + this.sqFeet +
                ",\"price\":" + this.price +
                ",\"status\":\"" + this.status + '\"' +
                '}';
    }

}
