package swen646.edwenson;

import java.util.Scanner;

public class RunApplication {

    /**
     * This method will start up the program
     * Now it will Display couple options and terminate after a user entry
     * @param args
     */
    public static void main(String[] args) throws Exception{
        Manager manager = new Manager();
        manager.loadAccAndResv();
        menu();
        Scanner userInput = new Scanner(System.in);
        int entry = userInput.nextInt();
        System.exit(0);
    }

    public static void menu() {
        System.out.println("\nWelcome to the Lodging Reservation System for House, Hotel, Cabin reservations");
        System.out.println("Choose from the following options\nby typing the number in front of the line and hit enter");
        System.out.println("\n\nOPTIONS");
        System.out.println("0 - Display the Menu at anytime");
        System.out.println("1 - Create a new Account");
        System.out.println("2 - Make a reservation");
        System.out.println("3 - Update a reservation");
        System.out.println("4 - Request a reservation price per night");
        System.out.println("5 - Request total reservation price");
        System.out.println("9 - Terminate Program");
    }
}
