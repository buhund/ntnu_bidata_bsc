package no.ntnu.idatt2106.utils;

import no.ntnu.idatt2106.enums.ProductCategory;
import no.ntnu.idatt2106.model.BankTransaction;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Service class for handling bank transactions.
 * Responsible for saving BankTransaction entities to the database.
 */
public class TransactionCategorisation {

    private final Map<String, ProductCategory> companyCategoryMap;

    /**
     * Class responsible for categorizing bank transactions based on their description.
     */
    public TransactionCategorisation() throws IOException {
        companyCategoryMap = new HashMap<>();
        // Load companies from the text file
        loadCompanies();
    }

    /**
     * Loads the list of companies and their corresponding categories from a file.
     *
     * @throws IOException if there is an error reading the file
     */
    private void loadCompanies() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource("companies").getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 2) {
                String companyName = parts[0].trim();
                ProductCategory category = ProductCategory.valueOf(parts[1].trim().toUpperCase());
                companyCategoryMap.put(companyName, category);
            }
        }
    }

    /**
     * Categorizes a bank transaction based on its description.
     *
     * @param transaction the bank transaction to categorize
     * @return the category of the transaction
     */
    public ProductCategory categoriseTransaction( BankTransaction transaction) {
        for (Map.Entry<String, ProductCategory> entry : companyCategoryMap.entrySet()) {
            if (transaction.getDescription().contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return ProductCategory.OTHER;
    }

    /**
     * Categorizes and sets the category of a bank transaction based on its description.
     *
     * @param transaction the bank transaction to categorize and set the category for
     */
    public void categorizeAndSetTransactionCategory(BankTransaction transaction) {
        ProductCategory productCategory = companyCategoryMap.entrySet().stream()
                .filter(entry -> transaction.getDescription().contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(ProductCategory.OTHER);
        transaction.setCategory(productCategory);
    }
}