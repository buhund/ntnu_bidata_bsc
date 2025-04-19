package Oppgave01;
import java.util.Scanner;

public class Main {
    public static Conversion[] currency = new Conversion[3];

    // Lager liste over valuta ved bruk av Conversion.
    // Hver currency[n] lager en ny instans av klassen(?) Conversion.
    // Tilordner verdier til double excRate og String curName
    public static void main(String[] args) {
        currency[0] = new Conversion(10.25d, "USD");
        currency[1] = new Conversion(10.20d, "EUR");
        currency[2] = new Conversion(0.95d, "SEK");

        System.out.println("Welcome to Weyland-Yutani Corporation Interplanetary Currency Exchange");
        System.out.println("Please select currency to convert to.");

        userMenu();

    }
    // Print out the user input interface.
    private static void userMenu(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Press 1 for USD");
        System.out.println("Press 2 for EUR");
        System.out.println("Press 3 for SEK");
        System.out.println("Press 4 to EXIT");
        int userInput = scanner.nextInt();

        // Validate input and print unnecessarily long messages.
        if(userInput == 4){
            System.out.println("EXIT\n" +
                    "---\n" +
                    "---\n" +
                    "Thank you for choosing Weyland-Yutani Corporation Interplanetary Currency Exchange.\n" +
                    "Have a nice day.");
            System.exit(0);
        }else if (userInput != 1 && userInput != 2 && userInput != 3){
            System.out.println("ERROR. Invalid menu option.\n" +
                    "Please try again.\n");
            userMenu();
        }else{
            conversionCalculator(userInput);
            // If input is valid and not EXIT, continue to method conversionCalculator
            // Passing userInput to conversionCalculator
            // conversionCalculator passes userInput to currencyName
        }
    }
    // Open new scanner to receive the amount to be converted.
    public static void conversionCalculator(int currencyName) {
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Please input the amount to be exchanged: \n");
        double amount = scanner2.nextDouble();

        // Calls the Conversion class, and conversion method.
        // Passes the input currencyName - 1 to locate the correct instance.
        // Subtracts 1 to correspond with the Java zero-index.
        System.out.println(Conversion.conversion(amount, currency[currencyName - 1]));
        System.out.println("Currency conversion complete.");

        userMenu();
    }
}
