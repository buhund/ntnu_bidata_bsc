package oving.oving01;


/* Oppgave 2 */
import java.util.Scanner; /* Importerer scanner funksjon for å lese user input */
public class Oppgave02 {
    public static void main(String[] args) {
        int HH_to_ss = 3600; /* Faktor, konvertere timer til sekunder */
        int mm_to_ss = 60; /* Faktor, konvertere minutter til sekunder */

        Scanner scanner = new Scanner(System.in); /* Kaller på scanner funksjon for å lese user input */
        /* Ber om input og tilegner det en variabel */
        System.out.print("Skriv inn antall timer: ");
        int HH_input = scanner.nextInt();
        System.out.print("Skriv inn antall minutter: ");
        int mm_input = scanner.nextInt();
        System.out.print("Skriv inn antall sekunder: ");
        int ss_input = scanner.nextInt();
        scanner.close(); /* Lukker scanner funksjonen */


        /* Utfører omregningen og summerer antall sekunder */
        int totalt_sekunder = (HH_input * HH_to_ss) + (mm_input * mm_to_ss) + ss_input;


        /* Skriver ut hva input blir i sekunder */
        System.out.println(HH_input + " timer " + mm_input + " minutter og " + ss_input + " sekunder = " + totalt_sekunder + " sekunder.");
    }
}


/*
Testsett, manuelt
20 timer = 72 000 sekunder
19 minutt = 1 140 sekunder
8 sekunder = 8 sekunder
Totalt: 73 148 sekunder
 */