package swen646.edwenson;

import com.fasterxml.jackson.databind.ObjectMapper;
import swen646.edwenson.exception.DuplicateObjectException;
import swen646.edwenson.exception.IllegalLoadException;
import swen646.edwenson.exception.IllegalOperationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Manager {

    private ArrayList<Account> account;
    private ObjectMapper mapper = new ObjectMapper();

    public Manager() {
    }

    public Manager(ArrayList<Account> account) {
        this.account = account;
    }

    private Account loadAcc(File accountFilePath) throws IOException {
        return mapper.readValue(accountFilePath, Account.class);
    }

    private List<Reservation> retrieveReservation(Path path) throws IOException {
        List<Reservation> resv = new ArrayList<>();
        List<Path> reservData = Files.list(path).filter(p -> p.getFileName().toString().startsWith("res-") &&
                        p.toString().endsWith(".json"))
                .collect(Collectors.toList());
        reservData.forEach(p -> {
            try {
                if (p.getFileName().toString().startsWith("res-C")) {
                    resv.add(mapper.readValue(p.toFile(), CabinReservation.class));
                } else if (p.getFileName().toString().startsWith("res-H")) {
                    resv.add(mapper.readValue(p.toFile(), HotelReservation.class));
                } else if (p.getFileName().toString().startsWith("res-O")) {
                    resv.add(mapper.readValue(p.toFile(), HouseReservation.class));
                }
            } catch (FileNotFoundException fnfe) {
                throw new IllegalLoadException("Reservation file " + p + "not found in the specified location", fnfe);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        return resv;
    }

    /**
     * This method will load and create objects from data stored in files
     */
    public void loadAccAndResv() throws IOException {
        List<Path> dataFolders = Files.list(Path.of(Manager.DATA_LOCATION))
                .filter(p -> p.getFileName().toString().startsWith("A"))
                .collect(Collectors.toList());
        if (dataFolders.isEmpty()) {
            System.out.println("No Account data folder found at:-> "+ Manager.DATA_LOCATION);
        } else {
            dataFolders.forEach(p -> {
                System.out.println("Found account folder: " + p);
                if (!p.getFileName().toString().startsWith("A")) {
                    try {
                        Account acc = loadAcc(new File(p.toString(), "acc-" + p.getFileName() + ".json"));
                        System.out.println(acc);
                        // TODO need to complete account serialization into list of String
                        addAccount(acc);
                        addReservation(retrieveReservation(p));
                    } catch (FileNotFoundException fnfe) {
                        throw new IllegalLoadException("Account file " + p + "not found in the specified location", fnfe);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    /**
     * This method will Get the list of loaded accounts from Manager
     *
     * @return ArrayList<Account>
     */
    public ArrayList<Account> getAccount() {
        return this.account;
    }

    /**
     * This method is allowing user to retrieve account objects by account number.
     *
     * @param accNum the number of the account to be retrieved
     * @return Optional<Account>
     */
    public Optional<Account> getAccountByNum(String accNum) {
        return this.account.stream().filter(acc -> acc.getAccNum().equalsIgnoreCase(accNum)).findFirst();
    }

    /**
     * This method handles adding a new account to the list managed by the Manager
     * It will check if the account in the is already stored in the list.
     *
     * @param account Account object to be added
     * @throws DuplicateObjectException when account with the same accNumber exists in the system
     */
    public void addAccount(Account account) throws DuplicateObjectException, IllegalStateException {
        if (Objects.nonNull(account)) {
            if (getAccountByNum(account.getAccNum()).isEmpty()) {
                this.account.add(account);
            } else {
                throw new DuplicateObjectException("Account with number " + account.getAccNum() + " already existed in " +
                        "Manager.\nWe can duplicate account in the system.\nAction failed");
            }
        } else {
            throw new IllegalStateException("Null Account Object provided");
        }
    }

    /**
     * This method will allow to save or update account data into files using account numbers
     *
     * @param accNum
     */
    public void saveToFile(String accNum) {

    }

    private Optional<Reservation> getReservation(String resvNumber) {
        // TODO need to return specific Reservation either Cabin, House, Hotel
        return this.account.stream()
                .flatMap(a ->
                        a.getReservation().stream()
                                .filter(r -> r.getResvNum().equalsIgnoreCase(resvNumber))
                ).findFirst();
    }

    /**
     * This method will add a new reservation to the list in the related account object
     *
     * @param reservation to be added
     */
    public void addReservation(Reservation reservation) {
        if (Objects.nonNull(reservation)) {
            if (getReservation(reservation.getResvNum()).isEmpty()) {
                getAccountByNum(reservation.getAccNum())
                        .orElseThrow(() -> new IllegalArgumentException("Account not existed - Invalid parameter"))
                        .addReservation(reservation);
            } else {
                throw new DuplicateObjectException("Reservation with number" + reservation.getResvNum() + "already existed in " +
                        "Manager.\nWe can duplicate Reservation in the system.\nAction failed");
            }
        }
    }

    public void addReservation(List<Reservation> reservation) {
        if (Objects.nonNull(reservation)) {
            reservation.forEach(this::addReservation);
        } else {
            throw new IllegalStateException("Null Reservation List Object provided");
        }
    }

    /**
     * This method will calculate and return price for a reservation per night
     *
     * @param reservation to be evaluated
     * @return Double
     */
    public Double getPricePerNight(Reservation reservation) {
        return 0.0;
    }

    /**
     * Get total price for the length of stay of the reservation
     *
     * @param reservation to be evaluated
     * @return Double
     */
    public Double getTotalPrice(Reservation reservation) {
        return 0.0;
    }

    /**
     * This method will help to update a reservation like complete it or cancel it
     * The reservation will be also updated on the list
     *
     * @param reservation provided reservation to update reservation found in the list in the account
     * @throws IllegalOperationException on unauthorized action on existed reservation found from accounts in manager
     * @throws IllegalArgumentException when reservation not existed from accounts in Manager
     */
    public void updateReservation(Reservation reservation) throws IllegalOperationException, IllegalArgumentException{
        Reservation resv = getReservation(reservation.getResvNum())
                .orElseThrow(() -> new IllegalArgumentException("Reservation not existed - Invalid Reservation parameter"));
        if (reservation.getStatus().equalsIgnoreCase(Reservation.COMPLETED)) {
            throw new IllegalOperationException("Error - Completed reservation cannot be updated");
        } else if (reservation.getStatus().equalsIgnoreCase(Reservation.CANCELLED)) {
            throw new IllegalOperationException("Error - Cancelled reservation cannot be updated");
        } else if (reservation.getCheckInDate().before(new Date())) {
            throw new IllegalOperationException("Error - Reservation with past date cannot be updated");
        }
        // TODO - need to complete update actions
    }
}
