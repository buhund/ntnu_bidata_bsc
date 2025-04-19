import java.util.Scanner; // Imporerer input-funksjonen "scanner"

public class SchaltjahrFinder {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Kaller på input funksjon.
        System.out.print("Bitte Jahr eingeben: "); // Ber om input fra bruker.
        int jahr = scanner.nextInt();
        scanner.close();



        if (((jahr % 100 == 0) && (jahr % 400 == 0)) || ((jahr % 4 == 0) && (jahr % 100 != 0))){
            /*
            Sjekker om år er delelig på 100 = Hundreår, OG delelig på 400 = Skuddår hundreår.
            ELLER
            Sjekker om år er delelig på 4 = Skuddår OG år ikke delelig på 100 = Ikke hundreår.

            Altså:
            Sjekker om det er (hundreår OG skuddår) ELLER (skuddår OG ikke ikke hundreår)
            Printer da ut at det er et skuddår.
            */
            System.out.println("Das Jahr " + jahr + " ist ein Schaltjahr."); // Skriver ut at det *er* et skuddår om det stemmer med if-sjekken, eller
        } else {
            System.out.println("Das Jahr " + jahr + " ist kein Schaltjahr."); // Skriver ut at det ikke er et skuddår, om tallet ikke stemmer med if-sjekken.
        }
    }
}




/*
Øving 2 - Oppgave 1

Et år er skuddår dersom det er delelig med 4. Unntaket er hundreårene, de må være delelig med 400.
Tegn aktivitetsdiagram som viser algoritmen for å finne ut om et år er skuddår.
Årstallet skal leses inn fra brukeren. Sett opp testdata. Lag og prøv ut programmet.
*/
