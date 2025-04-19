package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.enums.ProductCategory;
import no.ntnu.idatt2106.model.BankAccount;
import no.ntnu.idatt2106.model.BankTransaction;
import no.ntnu.idatt2106.repository.BankTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a Bank Transaction Service.
 */
@Service
public class BankTransactionService {

    @Autowired
    private BankTransactionRepository bankTransactionRepository;

    public void saveTransaction( BankTransaction bankTransaction) {
        bankTransactionRepository.save(bankTransaction);
    }

    /**
     * Retrieves a list of bank transactions within a specified date range for a given bank account.
     *
     * @param startDate    The start date of the range (inclusive).
     * @param endDate      The end date of the range (inclusive).
     * @param bankAccount  The bank account for which to retrieve the transactions.
     * @return A list of bank transactions within the specified date range.
     */
    public List<BankTransaction> getTransactionsByBankAccountInRange(LocalDate startDate, LocalDate endDate, BankAccount bankAccount) {
        List<BankTransaction> bankTransactions = bankTransactionRepository.findByBankAccount(bankAccount);
        List<BankTransaction> bankTransactionsInRange = new ArrayList<>();

        bankTransactions.forEach(bankTransaction -> {
            if (bankTransaction.getTransactionDate().isAfter(startDate.minusDays(1)) && bankTransaction.getTransactionDate().isBefore(endDate.plusDays(1))) {
                bankTransactionsInRange.add(bankTransaction);
            }
        });

        return bankTransactionsInRange;
    }


    /**
     * Calculates the total expenses from a list of bank transactions.
     *
     * @param bankTransactions The list of bank transactions.
     * @return The total expenses calculated as the sum of negative transaction amounts.
     */
    public double calculateExpenses(List<BankTransaction> bankTransactions) {
        double sumExpenses = 0;

        for (BankTransaction bt : bankTransactions) {
            double amount = bt.getAmount();
            if (amount < 0) {
                sumExpenses += (-amount);
            }
        }

        return sumExpenses;
    }

    /**
     * Calculates the total income from a list of bank transactions.
     *
     * @param bankTransactions The list of bank transactions.
     * @return The total income calculated as the sum of positive transaction amounts.
     */
    public double calculateIncomes(List<BankTransaction> bankTransactions) {
        double sumIncomes = 0;

        for (BankTransaction bt : bankTransactions) {
            double amount = bt.getAmount();
            if (amount > 0) {
                sumIncomes += amount;
            }
        }

        return sumIncomes;
    }

    /**
     * Calculates the total expenses by category from a list of bank transactions.
     *
     * @param bankTransactions The list of bank transactions to calculate expenses from.
     * @return An array of doubles representing the total expenses for each category. The ordering of the categories in the array is as follows:
     *          - GROCERIES
     *          - TRANSPORT
     *          - ENTERTAINMENT
     *          - RESTAURANT
     *          - CLOTHING
     *          - OTHER
     */
    public double[] calculateExpensesByCategory(List<BankTransaction> bankTransactions) {
        double[] expensesByCategory = {0.0,0.0,0.0,0.0,0.0,0.0};

        for (BankTransaction bt : bankTransactions) {
            double amount = bt.getAmount();
            if (amount < 0) {
                ProductCategory category = bt.getCategory();
                if (category == null) {
                    category = ProductCategory.OTHER;
                }
                switch (category) {
                    case GROCERIES:
                        expensesByCategory[0] += (-amount);
                        break;
                    case TRANSPORT:
                        expensesByCategory[1] += (-amount);
                        break;
                    case ENTERTAINMENT:
                        expensesByCategory[2] += (-amount);
                        break;
                    case RESTAURANT:
                        expensesByCategory[3] += (-amount);
                        break;
                    case CLOTHING:
                        expensesByCategory[4] += (-amount);
                        break;
                    default:
                        expensesByCategory[5] += (-amount);
                        break;
                }
            }
        }

        return expensesByCategory;
    }

    /**
     * Calculates the total income for each category based on a list of bank transactions.
     *
     * @param bankTransactions The list of bank transactions.
     * @return An array of integers representing the number of transactions for each category.
     *         The ordering of the categories in the array is as follows:
     *         - GROCERIES
     *         - TRANSPORT
     *         - ENTERTAINMENT
     *         - RESTAURANT
     *         - CLOTHING
     *         - OTHER
     */
    public double[] calculateIncomesByCategory(List<BankTransaction> bankTransactions) {
        double[] incomesByCategory = {0.0,0.0,0.0,0.0,0.0,0.0};

        for (BankTransaction bt : bankTransactions) {
            double amount = bt.getAmount();
            if (amount > 0) {
                ProductCategory category = bt.getCategory();
                if (category == null) {
                    category = ProductCategory.OTHER;
                }
                switch (category) {
                    case GROCERIES:
                        incomesByCategory[0] += amount;
                        break;
                    case TRANSPORT:
                        incomesByCategory[1] += amount;
                        break;
                    case ENTERTAINMENT:
                        incomesByCategory[2] += amount;
                        break;
                    case RESTAURANT:
                        incomesByCategory[3] += amount;
                        break;
                    case CLOTHING:
                        incomesByCategory[4] += amount;
                        break;
                    default:
                        incomesByCategory[5] += amount;
                        break;
                }
            }
        }

        return incomesByCategory;
    }

    /**
     * Calculates the number of transactions by category from a list of bank transactions.
     *
     * @param bankTransactions The list of bank transactions to calculate the number of transactions by category from.
     * @return An array of integers representing the number of transactions for each category.
     *         The ordering of the categories in the array is as follows:
     *         - GROCERIES
     *         - TRANSPORT
     *         - ENTERTAINMENT
     *         - RESTAURANT
     *         - CLOTHING
     *         - OTHER
     */
    public int[] calculateNrOfTransactionsByCategory(List<BankTransaction> bankTransactions) {
        int[] transactionsByCategory = {0,0,0,0,0,0};

        for (BankTransaction bt : bankTransactions) {
            double amount = bt.getAmount();
            if (amount < 0) {
                ProductCategory category = bt.getCategory();
                if (category == null) {
                    category = ProductCategory.OTHER;
                }
                switch (category) {
                    case GROCERIES:
                        transactionsByCategory[0] ++;
                        break;
                    case TRANSPORT:
                        transactionsByCategory[1] ++;
                        break;
                    case ENTERTAINMENT:
                        transactionsByCategory[2]++;
                        break;
                    case RESTAURANT:
                        transactionsByCategory[3]++;
                        break;
                    case CLOTHING:
                        transactionsByCategory[4]++;
                        break;
                    default:
                        transactionsByCategory[5]++;
                        break;
                }
            }
        }
        return transactionsByCategory;
    }

    /**
     * Calculates the average amount of transactions by category
     * @param transactions the list of transactions from bank account
     * @param category the category to calculate the average amount for
     * @return the average amount of transactions by category
     */
    public double calculateAverageByCategory( List<BankTransaction> transactions, ProductCategory category) {
        double totalAmount = 0;
        int count = 0;

        for (BankTransaction transaction : transactions) {
            if (transaction.getCategory().equals(category)) {
                totalAmount += (-transaction.getAmount());
                count++;
            }
        }

        return count > 0 ? totalAmount / count : 0;
    }

    /**
     * Saves a transaction to the users savings account
     * @param bankTransaction the transaction to save
     * @param savingsAccount the savings account to save the transaction to
     */
    public void saveTransactionToSavingsAccount( BankTransaction bankTransaction, BankAccount savingsAccount) {
        bankTransaction.setBankAccount(savingsAccount);
        bankTransactionRepository.save(bankTransaction);
    }
}