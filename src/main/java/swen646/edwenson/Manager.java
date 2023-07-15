package swen646.edwenson;

import com.fasterxml.jackson.databind.ObjectMapper;
import swen646.edwenson.exception.DuplicateObjectException;
import swen646.edwenson.exception.IllegalLoadException;
import swen646.edwenson.exception.IllegalOperationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Manager {

    private ArrayList<Account> account;
    private final ObjectMapper mapper = new ObjectMapper();

    public Manager() {
        this.account = new ArrayList<>();
    }

    public Manager(ArrayList<Account> account) {
        this.account = account;
    }

    private Account convertAcc(File accountFilePath) throws IOException {
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

    private String dynamicMenuStringEntry(List<String> menuOptions) {
        Scanner userInput = new Scanner(System.in);
        menuOptions.forEach(System.out::println);
        return userInput.nextLine();
    }

    private int dynamicMenuIntEntry(List<String> menuOptions, int... expected) {
        Scanner userInput = new Scanner(System.in);
        int userEntry = -1;
        boolean validEntry = false;
        do {
            menuOptions.forEach(System.out::println);
            userEntry = userInput.nextInt();
            int finalUserEntry = userEntry;
            if (expected.length > 0) {
                validEntry = Arrays.stream(expected).anyMatch(e -> e == finalUserEntry);
            } else {
                validEntry = true;
            }
        } while (!validEntry);
        return userEntry;
    }

    /**
     * This method will load and create objects from data stored in files
     */
    public void loadAccAndResv() throws IOException {
        String dataLocation = dynamicMenuStringEntry(
                List.of("Please provide Account Data path")
        );

        loadDataFromFile(dataLocation);
    }

//    /home/eraphael/study/data
//    A123456789

    private void loadDataFromFile(String dataLocation) throws IOException {
        Path mainPath = Path.of(dataLocation);
        System.out.println("dataLocation = " + mainPath);
        if(!Files.exists(mainPath, LinkOption.NOFOLLOW_LINKS)) {
            throw new IllegalLoadException("Data Location: " + dataLocation + " not found");
        }
        List<Path> dataFolders = Files.list(mainPath)
                .filter(p -> p.getFileName().toString().startsWith("A"))
                .collect(Collectors.toList());
        if (dataFolders.isEmpty()) {
            System.out.println("No Account data folder found at:-> "+ dataLocation);
        } else {
            int option = dynamicMenuIntEntry(Arrays.asList(
                    "Load Account Data Sub-menu",
                    "1 - Load One Specific Account",
                    "2 - Load All Accounts")
            );

            if (option == 1) {
                String accountNum = dynamicMenuStringEntry(
                        List.of("To Load One Specific Account provide Account number below")
                );

                loadSingleAccount(mainPath, accountNum);
            } else if (option == 2) {
                loadAllAccount(dataLocation, dataFolders);
            } else {
                throw new IllegalArgumentException("Wrong entry for this level");
            }
        }
    }

    private void loadAllAccount(String dataLocation, List<Path> dataFolders) {
        System.out.println("Loading data from:-> "+ dataLocation);
        dataFolders.forEach(p -> {
            if (p.getFileName().toString().startsWith("A")) {
                try {
                    Account acc = convertAcc(new File(p.toString(), "acc-" + p.getFileName()+ ".json"));
                    System.out.println(acc.getAccNum());
                    // TODO need to complete account serialization into list of String for Reser or Jackson Mapper
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

    private void loadSingleAccount(Path dataHome, String accNumber) {
        try {
            Account acc = convertAcc(new File(Path.of(dataHome.toString(), accNumber).toString(), "acc-" + accNumber + ".json"));
            System.out.println(acc);
            // TODO need to complete account serialization into list of String for Reser or Jackson Mapper
            addAccount(acc);
            addReservation(retrieveReservation(dataHome));
        } catch (FileNotFoundException fnfe) {
            throw new IllegalLoadException("Account file " + dataHome + " not found in the specified location", fnfe);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createAccount(){
        String accAddress = dynamicMenuStringEntry(
                List.of("Please type account owner address")
        );

        String email = "";
        do {
            email = dynamicMenuStringEntry(
                List.of("Please type email address")
            );
            if(!email.matches(".*@.*\\..*")){
                System.out.println("Please provide email format like: email@domain.com");
            }
        } while(!email.matches(".*@.*\\..*"));

        String phone = "";
        do {
            phone = dynamicMenuStringEntry(
                    List.of("Please type phone number\nTYPE 10 DIGITS ONLY")
            );
        } while (phone.length() != 10);

        String dataLocation = dynamicMenuStringEntry(
                List.of("Please provide Data path to save account")
        );

        Optional<String> accountNumber = Optional.empty();
        do {
            try {
                String accNumber = "A" + String.valueOf(Math.random()).substring(4, 13);
                System.out.println("Generated account Number :> " + accNumber);
                accountNumber = checkAccNumberAndLocation(accNumber, dataLocation);
            } catch (RuntimeException re) {
                re.printStackTrace();
            }
        } while (accountNumber.isEmpty());
        Path newAccountFolder = Path.of(dataLocation, accountNumber.get());

        Account account = new Account(accountNumber.get(), accAddress, email, phone);

        try {
            newAccountFolder = Files.createDirectories(newAccountFolder);
            addAccount(account);
            saveAccountToFile(account, newAccountFolder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private Optional<String> checkAccNumberAndLocation(String accNumber, String dataLocation) {
        Path mainPath = Path.of(dataLocation);
        Path newAccountFolder = Path.of(dataLocation, accNumber);
        System.out.println("dataLocation = " + mainPath);
        if(!Files.exists(mainPath, LinkOption.NOFOLLOW_LINKS)) {
            throw new IllegalLoadException("Data Location: " + dataLocation + " not found");
        }
        if(Files.exists(newAccountFolder, LinkOption.NOFOLLOW_LINKS)) {
            throw new DuplicateObjectException("Account Location: " + newAccountFolder + " already exists");
        }
        return Optional.of(accNumber);
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

    public void saveAccountToFile(Account acc, Path directory) throws IOException {
        mapper.writeValue(new File(directory.toString(), "acc-" + acc.getAccNum() + ".json"), acc);
    }

    public void createReservation() {
        String dataLocation = dynamicMenuStringEntry(
                List.of("Please provide Data path to save reservation")
        );

        String accNum = dynamicMenuStringEntry(
                List.of("Please type account number to add this reservation to")
        );
        Path mainPath = Path.of(dataLocation);
        Path newAccountFolder = Path.of(dataLocation, accNum);
        System.out.println("dataLocation = " + mainPath);
        if(!Files.exists(mainPath, LinkOption.NOFOLLOW_LINKS)) {
            throw new IllegalLoadException("Data Location: " + dataLocation + " not found");
        }
        if(!Files.exists(newAccountFolder, LinkOption.NOFOLLOW_LINKS)) {
            throw new IllegalLoadException("Account Location: " + newAccountFolder + " not found");
        }

        String lodgingAddress = dynamicMenuStringEntry(
                List.of("Please type lodging physical address")
        );
        int sameAddrOption = dynamicMenuIntEntry(Arrays.asList(
                "Is the physical address of the lodging the same as its mailing address",
                "1 => Yes",
                "2 => No"), 1,2
        );
        String lodgingMailingAddress = "";
        if (sameAddrOption == 1) {
            lodgingMailingAddress = lodgingAddress;
        } else {
            lodgingMailingAddress = dynamicMenuStringEntry(
                    List.of("Please type lodging physical address")
            );
        }

        Date checkIn = null;
        do {
            String checkInDate = dynamicMenuStringEntry(
                    Arrays.asList("Please enter check-in Date", "Format: MM/DD/YY")
            );
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                checkIn = sdf.parse(checkInDate);
                System.out.println("Check-In date valid -> " + checkIn.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (Objects.isNull(checkIn) && checkIn.after(new Date())){
                System.out.println("Remember check-in date should be later than today.");
            }
        } while(Objects.isNull(checkIn) && checkIn.after(new Date()));

        int lengthOfStay = dynamicMenuIntEntry(List.of("Please enter the number of nights"));
        int beds = dynamicMenuIntEntry(List.of("Please enter the number of beds"));
        int bedrooms = dynamicMenuIntEntry(List.of("Please enter the number of bedrooms"));
        int baths = dynamicMenuIntEntry(List.of("Please enter the number of baths"));
        int sqFeet = dynamicMenuIntEntry(List.of("Please enter the lodging size in square feet"));

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
