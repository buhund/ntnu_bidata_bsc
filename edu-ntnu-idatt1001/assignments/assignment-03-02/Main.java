package oppgave02;

import java.util.Scanner;
public class primtall {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);   // Lager ny scanner.
        System.out.println("Input an integer, 1<, to check if prime. Input 0 to exit.");   // Ber om input
        int number = scanner.nextInt();     // Gjør input om til int og tilordner input en variabel number

        while(number > 0){ // Using while number greater than 0 to continue asking for input. Input "0" will terminate the loop.
            boolean isNumberAPrime = isPrime(number); // Define variable to decide on printout
            if(isNumberAPrime){ // Trenger ikke sjekke at isNumberAPrime == True, fordi den vil alltid være True?
                System.out.println(number + " is a prime.");
            } else {
                System.out.println(number + " is not a prime.");
            }
            System.out.println("Input an integer, 1<, to check if prime. Input 0 to exit."); // Repeating the input dialogue after printout.
            number = scanner.nextInt();
        }
        scanner.close();
    }

    private static boolean isPrime(long number) { // Conditions for the above boolean test on isNumberAPrime
        if ((number % 2 == 0) || (number == 1)){ // Initial check for primes, or if number is 1 (not a prime).
            return false;
        }
        for (long i = 2; i <= number / 2; ++i) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}