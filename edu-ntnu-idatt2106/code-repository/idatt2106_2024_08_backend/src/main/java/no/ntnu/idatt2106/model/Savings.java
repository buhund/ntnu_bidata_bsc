package no.ntnu.idatt2106.model;

/**
 * This class represents the savings of a user. It stores the total savings amount,
 * the savings amount by category, and a message related to the savings.
 */
public class Savings {
    double savings;
    double[] savingsByCategory;
    String savingMessage;

    /**
     * Constructs a new Savings object with the specified savings amount,
     * savings by category, and saving message.
     *
     * @param savings           The total savings amount.
     * @param savingsByCategory The savings amount by category.
     * @param savingMessage     The message related to the savings.
     */
    public Savings(double savings, double[] savingsByCategory, String savingMessage) {
        this.savings = savings;
        this.savingsByCategory = savingsByCategory;
        this.savingMessage = savingMessage;
    }

    public double getSavings() {
        return savings;
    }

    public void setSavings(double savings) {
        this.savings = savings;
    }

    public double[] getSavingsByCategory() {
        return savingsByCategory;
    }

    public void setSavingsByCategory(double[] savingsByCategory) {
        this.savingsByCategory = savingsByCategory;
    }

    public String getSavingMessage() {
        return savingMessage;
    }

    public void setSavingMessage(String savingMessage) {
        this.savingMessage = savingMessage;
    }
}
