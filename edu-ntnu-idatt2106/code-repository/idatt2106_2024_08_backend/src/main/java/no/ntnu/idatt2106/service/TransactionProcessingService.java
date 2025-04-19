package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.enums.AccountType;
import no.ntnu.idatt2106.model.BankAccount;
import no.ntnu.idatt2106.model.BankTransaction;
import no.ntnu.idatt2106.model.Challenge;
import no.ntnu.idatt2106.model.User;
import no.ntnu.idatt2106.repository.BankAccountRepository;
import no.ntnu.idatt2106.repository.BankTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionProcessingService {

    private final BankAccountService bankAccountService;
    private final BankTransactionService bankTransactionService;
    private final UserAccountService userAccountService;
    private final BankAccountRepository bankAccountRepository;
    private final BankTransactionRepository bankTransactionRepository;

    /**
     * Constructor for TransactionProcessingService
     * @param bankAccountService bankAccountService class
     * @param bankTransactionService bankTransactionService class
     * @param userAccountService userAccountService class
     * @param bankAccountRepository bankAccountRepository
     * @param bankTransactionRepository bankTransactionRepository
     */
    @Autowired
    public TransactionProcessingService(BankAccountService bankAccountService,
                                        BankTransactionService bankTransactionService,
                                        UserAccountService userAccountService,
                                        BankAccountRepository bankAccountRepository,
                                        BankTransactionRepository bankTransactionRepository) {
        this.bankAccountService = bankAccountService;
        this.bankTransactionService = bankTransactionService;
        this.userAccountService = userAccountService;
        this.bankAccountRepository = bankAccountRepository;
        this.bankTransactionRepository = bankTransactionRepository;
    }

    /**
     * Processes total savings for a user
     * used for awarding badges
     * @param user user
     * @return total savings
     */
    public double processUserAccount(User user) {
        double totalSavings = bankAccountService.getTotalSavings(user);
        userAccountService.handleUserAccount(user, totalSavings);
        return totalSavings;
    }

    /**
     * Sends a transaction to the savings account when a user completes a saving challenge
     * @param user user
     * @param challenge challenge
     */
    public void completedSavingChallengeTransaction(User user, Challenge challenge) {
        BankAccount savingsAccount = bankAccountRepository.findByAccountTypeAndUser(AccountType.SAVINGS_ACCOUNT, user);

        BankTransaction rewardTransaction = new BankTransaction();
        rewardTransaction.setAmount((double) challenge.getTarget());
        rewardTransaction.setTransactionDate(LocalDate.now());
        rewardTransaction.setCategory(challenge.getCategory());
        rewardTransaction.setDescription("Fullført SpareSti utfordring");

        userAccountService.handleUserAccount(user, bankAccountService.getTotalSavings(user));

        bankTransactionService.saveTransactionToSavingsAccount(rewardTransaction, savingsAccount);
    }

    /**
     * Sends a transaction to the savings account when a user completes a spending challenge
     * @param user user
     * @param challenge challenge
     */
    public void completedSpendingChallengeTransaction(User user, Challenge challenge) {
        BankAccount spendingAccount = bankAccountRepository.findByAccountTypeAndUser(AccountType.SPENDING_ACCOUNT, user);

        List<BankTransaction> transactions = bankTransactionRepository.findByBankAccount(spendingAccount);
        double averageAmount = bankTransactionService.calculateAverageByCategory(transactions, challenge.getCategory());

        BankTransaction rewardTransaction = new BankTransaction();
        rewardTransaction.setAmount(averageAmount * (challenge.getTarget() - challenge.getProgress()));
        rewardTransaction.setTransactionDate(LocalDate.now());
        rewardTransaction.setCategory(challenge.getCategory());
        rewardTransaction.setDescription("Fullført SpareSti utfordring");

        userAccountService.handleUserAccount(user, bankAccountService.getTotalSavings(user));

        bankTransactionService.saveTransactionToSavingsAccount(rewardTransaction, spendingAccount);
    }
}