package swen646.edwenson;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Account {
    private String accNum;
    private String mailingAddr;
    private String email;
    private String phone;
    @JsonIgnore
    private ArrayList<Reservation> reservation;
    private Set<String> reservations = new HashSet<>();


    public Account(String accNum, String mailingAddr, String email, String phone, ArrayList<Reservation> reservation) {
        this.accNum = accNum;
        this.mailingAddr = mailingAddr;
        this.email = email;
        this.phone = phone;
        this.reservation = reservation;
    }

    public Account() {
    }

    public Account(String accNum, String mailingAddr, String email, String phone) {
        this.accNum = accNum;
        this.mailingAddr = mailingAddr;
        this.email = email;
        this.phone = phone;
    }

    public void addReservation(ArrayList<Reservation> reservs) {
        reservation = Objects.isNull(reservation) ? new ArrayList<>() : reservation;
        reservation.addAll(reservs);
        reservations.addAll(reservs.stream().map(Reservation::getResvNum).collect(Collectors.toSet()));
    }

    public void addReservation(Reservation resv) {
        reservation = Objects.isNull(reservation) ? new ArrayList<>() : reservation;
        reservation.add(resv);
        reservations.add(resv.getResvNum());
    }

    public String getAccNum() {
        return accNum;
    }

    public String getMailingAddr() {
        return mailingAddr;
    }

    public void setMailingAddr(String mailingAddr) {
        this.mailingAddr = mailingAddr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<Reservation> getReservation() {
        return reservation;
    }

    public Set<String> getReservations() {
        return reservations;
    }

    public void setReservations(Set<String> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "{" +
                "\"accNum\":\"" + accNum + "\"" +
                ",\"mailingAddr\":\"" + mailingAddr + "\"" +
                ",\"email\":\"" + email + "\"" +
                ",\"phone\":\"" + phone + "\"" +
                ",\"reservation\":\"" + reservations + "\"}";
    }
}
