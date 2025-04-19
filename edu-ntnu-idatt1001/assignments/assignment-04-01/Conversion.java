package Oppgave01;

public class Conversion {
    double exchangeRate;
    String currencyName;

    public Conversion(double excRate, String curName) {
        exchangeRate = excRate;
        currencyName = curName;
    }

    // Class for converting the selected currency with the provided exchange rate.
    // Returns a string, containing information about the conversion.
    public static String conversion(double amount, Conversion currency){
        double total = currency.exchangeRate * amount;
        String conversionCompleteMsg = amount + " " + currency.currencyName + " converts to " + (Double)total + " NOK.";
        return conversionCompleteMsg;
    }
}
/*
Forbedring:
- Ikke returnere string herfra. Returner resultate, og gjør alt av printing osv i Main.
*/