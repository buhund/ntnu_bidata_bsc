package oving.oving01;


/* Oppgave 1 */
import java.util.Scanner; /* Importerer scanner funksjon for å lese user input */
public class Oppgave01 {
    private static int[] test_data = {1, 10, 17, 93}; /* Array for å teste programmet */
    private static final double cm_per_inch = 2.54; /* Definerer  antall cm per tomme*/

    public static void main(String[] args) {
        for (int i: test_data){
            System.out.println(i + " cm = " + i * cm_per_inch + " tommer." );
        }

        Scanner scanner = new Scanner(System.in); /* Kaller på scanner funksjon for å lese user input */
        System.out.print("Skriv inn antall tommer: "); /* Ber bruker skrive inn antall tommer som skal konverteres */
        double inch_input = scanner.nextDouble(); /* Gjør user input om til et tall/double */
        scanner.close(); /* Lukker scanner funksjonen */

        double inch_in_cm = inch_input * cm_per_inch; /* Utfører omregningen ved å gange input tommer med faktor cm til tommer */
        System.out.println(inch_input + " tommer tilsvarer " + inch_in_cm + " centimeter. Metrisk <3"); /* Skriver ut den konverterte verdien til bruker */
    }
}

/*
Testsett, manuelt
1 tomme = 2.54 cm
10 tommer = 25.4 cm
17 tommer = 43.18 cm
93 tommer = 236.22 cm
 */