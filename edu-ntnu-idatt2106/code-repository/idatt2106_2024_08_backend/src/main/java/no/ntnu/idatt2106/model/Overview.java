package no.ntnu.idatt2106.model;

/**
 * The Overview class represents an overview of a bank account's financial information.
 */
public class Overview {
    double incomes;
    double expenses;
    double[] expensesByCategory;

    /**
     * Represents an overview of a bank account's financial information.
     *
     * @param incomes The total income of the bank account,
     *                i.e. sum of all incomes in the bank account)
     * @param expenses The total expenses of the bank account
     * @param expensesByCategory The total expenses of the bank account
     */
    public Overview (double incomes, double expenses, double[] expensesByCategory) {
        this.incomes = incomes;
        this.expenses = expenses;
        this.expensesByCategory = expensesByCategory;
    }

    public double getIncomes() {
        return incomes;
    }

    public void setIncomes(double incomes) {
        this.incomes = incomes;
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }

    public double[] getExpensesByCategory() {
        return expensesByCategory;
    }

    public void setExpensesByCategory(double[] expensesByCategory) {
        this.expensesByCategory = expensesByCategory;
    }
}
