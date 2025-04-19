package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.enums.BankHeader;
import no.ntnu.idatt2106.model.BankTransaction;
import no.ntnu.idatt2106.utils.CsvParser;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * Service class for parsing bank statements.
 * Responsible for parsing CSV files from different banks and returning a list of BankTransaction objects.
 * Communicates with the CsvParser.
 */
@Service
public class BankStatementParserService {

    private final CsvParser csvParser = new CsvParser();

    /**
     * Method for checking the header format of a CSV.
     * Will then pass it to the correct parser method in CsvParser.
     * CsvParser will return a list of BankTransaction objects.
     *
     * @param bankCSV input file, will be header-checked, then passed to CsvParser for processing
     * @return List<BankTransaction> returned from CsvParser
     */
    public List<BankTransaction> parseCSV( InputStream bankCSV) throws IOException {
        try (InputStream csvStream = bankCSV;
             ByteArrayOutputStream copyOfCsv = new ByteArrayOutputStream()) {
            csvStream.transferTo(copyOfCsv);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(copyOfCsv.toByteArray())))) {
                String header = reader.readLine();
                String[] headerFields = header.split(";");
                // Perform format check for different banks

                // DNB format check
                if (Arrays.equals(headerFields, BankHeader.DNB.getHeaders().toArray())){
                    return csvParser.parseDnbCsv(new ByteArrayInputStream(copyOfCsv.toByteArray()));

                    // Nordea format check
                } else if (Arrays.equals(headerFields, BankHeader.NORDEA.getHeaders().toArray())) {
                    return csvParser.parseNordeaCsv(new ByteArrayInputStream(copyOfCsv.toByteArray()));

                    // Unsupported format exception
                } else {
                    throw new IllegalArgumentException("Unsupported bank format with header: " + header);
                }
            }
        }
    }
}
