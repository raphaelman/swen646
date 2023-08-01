package swen646.edwenson;

import java.util.Scanner;

public class RunApplication {

    /**
     * This method will start up the program
     * Now it will Display couple options and terminate after a user entry
     * @param args runtime arguments
     */
    public static void main(String[] args) {
        Manager manager = new Manager();
        System.out.println("\nWelcome to the Lodging Reservation System for House, Hotel, Cabin reservations");
        System.out.println("Choose from the following options\nby typing the number in front of the line and hit enter");
        Manager.mainMenu();
        Scanner userInput = new Scanner(System.in);
        while (true) {
            int entry = userInput.nextInt();
            try {
                switch (entry) {
                    case 0:
                        Manager.mainMenu();
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
                        manager.updateReservation();
                        break;
                    case 6:
                        manager.getPricePerNight();
                        break;
                    case 7:
                        manager.displayReservation();
                        break;
                    case 9:
                        userInput.close();
                        System.exit(0);
                    default:
                        System.out.println("Entry not in the list of options");
                        Manager.mainMenu();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
