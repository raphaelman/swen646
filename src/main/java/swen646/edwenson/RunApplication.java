package swen646.edwenson;

import java.util.Arrays;
import java.util.Scanner;

public class RunApplication {

    /**
     * This method will start up the program
     * Now it will Display couple options and terminate after a user entry
     * @param args runtime arguments
     */
    public static void main(String[] args) throws Exception{
        Manager manager = new Manager();
        System.out.println("\nWelcome to the Lodging Reservation System for House, Hotel, Cabin reservations");
        System.out.println("Choose from the following options\nby typing the number in front of the line and hit enter");
        mainMenu();
        Scanner userInput = new Scanner(System.in);
        while (true) {
            int entry = userInput.nextInt();
            switch (entry) {
                case 0:
                    mainMenu();
                    break;
                case 1:
                    manager.loadAccAndResv();
                    break;
                case 2:
                    manager.createAccount();
                    break;
                case 3:
                    manager.createReservation();
                    break;
                case 4:
                    manager.updateAccToFile();
                    break;
                case 5:
                    manager.createAccount();
                    break;
                case 6:
                    manager.createAccount();
                    break;
                case 9:
                    System.exit(0);
                default:
                    System.out.println("Entry not in the list of options");
                    mainMenu();
            }
        }
    }

    public static void mainMenu() {
        System.out.println("\n\nOPTIONS");
        System.out.println("0 - Display the main Menu\nWhen you are done with a function");
        System.out.println("1 - Load Data from file");
        System.out.println("2 - Create a new Account");
        System.out.println("3 - Make a reservation");
        System.out.println("4 - Update Account to file");
        System.out.println("5 - Update a reservation");
        System.out.println(" - Request a reservation price per night");
        System.out.println(" - Request total reservation price");
        System.out.println("6 - Display Reservation");
        System.out.println("9 - Terminate Program");
    }
}
