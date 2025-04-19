public class MeatCalc {
    public static void main(String[] args) {
        double pris_A = 35.90;
        double vekt_A = 450.0;

        double pris_B = 39.50;
        double vekt_B = 500.0;

        double kr_pr_kg_A = pris_A / (vekt_A/100);
        double kr_pr_kg_B = pris_B / (vekt_B/100);

        if ( kr_pr_kg_A > kr_pr_kg_B) {
            System.out.println("Merke B er billigst, og koster " + Math.round(kr_pr_kg_B) + " kr per kg."); // Må finne hvordan avrunde til 2 desimaler.
        } else {
            System.out.println("Merke A er billigst, og koster " + kr_pr_kg_A + " kr per kg."); // Må finne hvordan avrunde til 2 desimaler.
        }

    }
}


/*
Øving 2 - Oppgave 2

Lag et program som hjelper oss i forhold til følgende problemstilling:
Kjøttdeig av merke A koster kr 35,90 for 450 gram, mens kjøttdeig av merke B koster kr 39,50 for 500 gram.
Hvilket merke er billigst?
 */