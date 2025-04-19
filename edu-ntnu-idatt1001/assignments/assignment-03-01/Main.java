package oppgave01;

import java.util.Scanner;

public class gangetabell {
    public static void main(String[] args) {
        System.out.println("Hvilket intervall vil du vise gangetabell for?");   // Ber om input

        System.out.println("Skriv inn tabell #1: ");   // Spesifiserer format for input
        Scanner scanner1 = new Scanner(System.in);   // Lager ny scanner.
        int tabell_1 = scanner1.nextInt();     // Gjør om til int og tilordner input en variabel

        System.out.println("Skriv inn tabell #2: ");   // Spesifiserer format for input
        Scanner scanner2 = new Scanner(System.in);   // Lager ny scanner.
        int tabell_2 = scanner2.nextInt();     // Gjør om til int og tilordner input en variabel

        scanner1.close();        // Lukker scanner
        scanner2.close();        // Lukker scanner

        // Trenger egentlig ikke to scannere.
        // Burde lage sjekk for om man skriver f.eks 15 og 13, dvs. at tall#2 er lavere enn tall#1.

        for (int n = tabell_1; n <= tabell_2; n++) {
            System.out.println(n + "-gangen:");    // Skriver ut hvilken gangetabell du får/ba om

            /*
            Lager en for loop.
            Starter ved tallet i lik 1.
            Loop:
                Kjør så lenge i er mindre enn eller lik 10.
                For hver iterasjon, ta i og øk med 1 (++)
                Skriver ut hver rad, frem til i = 10.
                Ved i = 10 er loopen oppfylt, og avsluttes.
             */

            for (int i = 1; i <= 10; i++) {
                System.out.println(n + " * " + i + " = " + n * i);
            }
        }
    }
}
