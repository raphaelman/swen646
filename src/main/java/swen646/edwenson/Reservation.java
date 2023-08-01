package swen646.edwenson;


import java.util.Date;

public class Reservation {
    public static final String COMPLETED = "completed";
    public static final String DRAFT = "draft";
    public static final String CANCELLED = "cancelled";
    private static final Integer DEF_BED_QTY = 2;
    private static final Integer DEF_BEDROOM_QTY = 1;
    private static final Integer DEF_BATH_QTY = 1;
    private static final Double BASE_PRICE = 120.0;
    private static final Double ADDTNL_LODG_PRICE = 15.0;
    private static final Integer MIN_SQ_FEET = 900;
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

    public Reservation(){}

    public Reservation(String accNum, String resvNum, String physicalAddr, String mailingAddr, Date checkInDate, Integer lengthOfStay, Integer bedQty, Integer bedroomQty, Integer bathQty, Integer sqFeet, String status) {
        this.accNum = accNum;
        this.resvNum = resvNum;
        this.physicalAddr = physicalAddr;
        this.mailingAddr = mailingAddr;
        this.checkInDate = checkInDate;
        this.lengthOfStay = lengthOfStay;
        this.bedQty = bedQty < DEF_BED_QTY ? DEF_BED_QTY : bedQty;
        this.bedroomQty = bedroomQty < DEF_BEDROOM_QTY ? DEF_BEDROOM_QTY: bedroomQty;
        this.bathQty = bathQty < DEF_BATH_QTY ? DEF_BATH_QTY : bathQty;
        this.sqFeet = sqFeet;
        this.status = status;
    }

    /**
     * This method will set price for Reservation
     * It will use the base price of $120  and possible charge for larger than 900 sqFeet lodging
     */
    public void basePrice() {
        this.price = sqFeet > MIN_SQ_FEET ? (BASE_PRICE + ADDTNL_LODG_PRICE) * lengthOfStay : BASE_PRICE * lengthOfStay;
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
