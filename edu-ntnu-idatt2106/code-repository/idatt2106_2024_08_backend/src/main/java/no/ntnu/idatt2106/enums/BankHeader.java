package no.ntnu.idatt2106.enums;

import java.util.Arrays;
import java.util.List;


/**
 * Enum representing the header formats for different banks in CSV files.
 *
 */
public enum BankHeader {
    DNB("\"Dato\"", "\"Forklaring\"", "\"Rentedato\"", "\"Ut fra konto\"", "\"Inn på konto\""),
    NORDEA("Bokføringsdato", "Beløp", "Avsender", "Mottaker", "Navn", "Tittel", "Valuta", "Betalingstype");

    private final List<String> headers;

    BankHeader(String... headers) {
        this.headers = Arrays.asList(headers);
    }

    public List<String> getHeaders() {
        return headers;
    }
}