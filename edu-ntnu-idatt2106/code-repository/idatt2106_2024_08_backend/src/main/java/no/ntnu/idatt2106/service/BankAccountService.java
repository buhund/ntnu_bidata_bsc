package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.enums.AccountType;
import no.ntnu.idatt2106.model.BankAccount;
import no.ntnu.idatt2106.model.BankTransaction;
import no.ntnu.idatt2106.model.User;
import no.ntnu.idatt2106.repository.BankAccountRepository;
import no.ntnu.idatt2106.repository.BankTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class to handle the logic and manipulate Bank Accounts.
 * The service class communicates with the repository.
 */
@Service
public class BankAccountService {

    /**
     * Repository for BankAccount
     * The repository communicates with the database and handles the BankAccount objects
     */
    @Autowired
    private BankAccountRepository bankAccountRepository;
    
    @Autowired
    private BankTransactionRepository bankTransactionRepository;

    public void updateBankAccount( BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
    }

    public BankAccount getBankAccountByAccountTypeAndUser(AccountType accountType, User user) {
        return bankAccountRepository.findByAccountTypeAndUser(accountType, user);
    }

    public double getTotalSavings(User user) {
        BankAccount savingsAccount = getBankAccountByAccountTypeAndUser(AccountType.SAVINGS_ACCOUNT, user);
        List<BankTransaction> savingTransactions = bankTransactionRepository.findByBankAccount(savingsAccount);
        return calculateIncomes(savingTransactions);
    }

    private double calculateIncomes(List<BankTransaction> bankTransactions) {
        double sumIncomes = 0;

        for (BankTransaction bt : bankTransactions) {
            double amount = bt.getAmount();
            if (amount > 0) {
                sumIncomes += amount;
            }
        }

        return sumIncomes;
    }
}

