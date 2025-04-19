package oving.oving01;


/* Oppgave 3 */
import java.util.Scanner; /* Importerer scanner funksjon for å lese user input */
public class Oppgave03 {
    public static void main(String[] args) {

        int HH_to_ss = 3600; /* Faktor, konvertere timer til sekunder */
        int mm_to_ss = 60; /* Faktor, konvertere minutter til sekunder */

        Scanner scanner = new Scanner(System.in); /* Kaller på scanner funksjon for å lese user input */
        System.out.println();
        System.out.print("Skriv inn antall sekunder: "); /* Ber bruker skrive inn antall timer */
        int ss_input = scanner.nextInt(); /* Gjør user input om til et tall/double og tilorder variabelnavn */
        scanner.close(); /* Lukker scanner funksjonen */

        /* Utfører omregningen */
        int HH = (ss_input / 3600);
        int mm = (ss_input / 60) % 60;
        int ss = (ss_input % 60);

        /* Skriver ut i timer, minutter og sekunder */
        System.out.println(ss_input + " = " + HH + " timer " + mm + " minutter " + ss + " sekunder.");
    }
}


/*
Testsett, manuelt
20 timer = 72 000 sekunder
19 minutt = 1 140 sekunder
8 sekunder = 8 sekunder
Totalt: 73 148 sekunder
 */