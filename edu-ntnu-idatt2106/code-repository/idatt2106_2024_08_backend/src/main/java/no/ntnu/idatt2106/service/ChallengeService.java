package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.enums.AccountType;
import no.ntnu.idatt2106.enums.ChallengeType;
import no.ntnu.idatt2106.enums.ProductCategory;
import no.ntnu.idatt2106.model.*;
import no.ntnu.idatt2106.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

/**
 * Service class to handle the logic and manipulate Challenge class.
 */
@Service
public class ChallengeService {

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired 
    private BankTransactionService bankTransactionService;

    @Autowired
    private BankAccountService bankAccountService;

    private static final Random random = new Random();
    @Autowired
    private TransactionProcessingService transactionProcessingService;

    /**
     * Method for finding a challenge by its id.
     *
     * @param id The id of the challenge to be found.
     * @return The challenge if found, or null if not found.
     */
    public Challenge findById(long id) {
        return challengeRepository.findById(id).orElse(null);
    }

    public Challenge updateChallenge( Challenge challenge) {
        return challengeRepository.save(challenge);
    }

    public void deleteChallenge(Challenge challenge) {
        challengeRepository.delete(challenge);
    }

    public List<Challenge> findAllChallengesByGoal(Goal goal) {
        return challengeRepository.findAllByGoal(goal);
    }

    public List<Challenge> findAllChallengesByUser(User user) {
        return challengeRepository.findAllByUser(user);
    }

    public void setStartDateForChallengeToToday(Challenge challenge) {   
        challenge.setStartDate(LocalDate.now());
    }

    public void updateProgress(Challenge challenge) {
        LocalDate today = LocalDate.now();

        if (challenge.isCompleted() || challenge.getEndDate().isBefore(today)) {
            processCompletedChallenge(challenge);
            return;
        }
        if (today.isBefore(challenge.getEndDate()) || today.equals(challenge.getEndDate())) {
            updateOngoingChallenge(challenge);
        }
    }


    private void processCompletedChallenge(Challenge challenge) {
        boolean isTargetMet = isTargetMet(challenge);
        if (isTargetMet && !challenge.isCompleted()) {
            challenge.setProgress(challenge.getTarget());
            completeChallenge(challenge);
        } else if (isTargetMet && challenge.isCompleted()) {
            //Set progress to target if a challenge somehow
            //has been marked as completed, but progress has not been properly set to target.
            //This is useful for proper presentation of completed challenges in frontend.
            challenge.setProgress(challenge.getTarget());
        }
        challengeRepository.save(challenge);
    }

    private boolean isTargetMet(Challenge challenge) {
        return switch (challenge.getChallengeType()) {
            case REDUCE_SPENDING -> challenge.getProgress() <= challenge.getTarget();
            case SAVING_TARGET -> challenge.getProgress() >= challenge.getTarget();
        };
    }

    private void completeChallenge(Challenge challenge) {
        challenge.setCompleted(true);
        switch (challenge.getChallengeType()) {
            case REDUCE_SPENDING:
                transactionProcessingService.completedSpendingChallengeTransaction(challenge.getUser(), challenge);
                break;
            case SAVING_TARGET:
                transactionProcessingService.completedSavingChallengeTransaction(challenge.getUser(), challenge);
                break;
            default:
                throw new IllegalStateException("Unsupported challenge type: " + challenge.getChallengeType());
        }
        challengeRepository.save(challenge);
    }

    private void updateOngoingChallenge(Challenge challenge) {
        User user = challenge.getUser();
        BankAccount spendingAccount = bankAccountService.getBankAccountByAccountTypeAndUser(AccountType.SPENDING_ACCOUNT, user);
        List<BankTransaction> transactions =
         bankTransactionService.getTransactionsByBankAccountInRange(challenge.getStartDate(), LocalDate.now(), spendingAccount);

        if (challenge.getChallengeType() == ChallengeType.REDUCE_SPENDING) {
            updateProgressReduceSpendingChallenge(challenge, transactions);
        } else {
            updateProgressSavingTargetChallenge(challenge, transactions, spendingAccount);
        }

        if (isTargetMet(challenge)) {
            challenge.setProgress(challenge.getTarget());  // Ensure progress does not exceed target
            completeChallenge(challenge);  // Complete the challenge if target is met
        }

        challengeRepository.save(challenge);  // Save the challenge state regardless of completion status
    }


    /**
     * Method for updating progress on a reduce spending challenge.
     *
     * @param challenge                   Challenge to be updated.
     * @param transactionsDuringChallenge List of bank transactions during challenge.
     */
    public void updateProgressReduceSpendingChallenge( Challenge challenge, List<BankTransaction> transactionsDuringChallenge) {
        int[] nrOfTransactionsByCategory = bankTransactionService.calculateNrOfTransactionsByCategory(transactionsDuringChallenge);
        switch (challenge.getCategory()) {
            case GROCERIES:
                challenge.setProgress(nrOfTransactionsByCategory[0]);
                break;
            case TRANSPORT:
                challenge.setProgress(nrOfTransactionsByCategory[1]);
                break;
            case ENTERTAINMENT:
                challenge.setProgress(nrOfTransactionsByCategory[2]);
                break;
            case RESTAURANT:
                challenge.setProgress(nrOfTransactionsByCategory[3]);
                break;
            case CLOTHING:
                challenge.setProgress(nrOfTransactionsByCategory[4]);
                break;
            default:
                challenge.setProgress(nrOfTransactionsByCategory[5]);
                break;
        }
    }

    /**
     * Method for updating progress on a saving target challenge.
     *
     * @param challenge                   Challenge to be updated.
     * @param transactionsDuringChallenge List of bank transactions during challenge.
     */
    public void updateProgressSavingTargetChallenge( Challenge challenge, List<BankTransaction> transactionsDuringChallenge, BankAccount spendingAccount) {
        List<BankTransaction> transactionsBeforeChallenge = bankTransactionService.getTransactionsByBankAccountInRange(challenge.getStartDate().minusMonths(1), challenge.getStartDate(), spendingAccount);

        long periodBeforeChallenge = ChronoUnit.DAYS.between(challenge.getStartDate().minusMonths(1), challenge.getStartDate().plusDays(1));
        long periodIntoChallenge = ChronoUnit.DAYS.between(challenge.getStartDate(), LocalDate.now().plusDays(1));

        double[] expensesByCategoryDuringChallenge = bankTransactionService.calculateExpensesByCategory(transactionsDuringChallenge);
        double[] expensesByCategoryBeforeChallenge = bankTransactionService.calculateExpensesByCategory(transactionsBeforeChallenge);

        double averageExpensePerDay;
        int progress;

        switch (challenge.getCategory()) {
            case GROCERIES:
                averageExpensePerDay = expensesByCategoryBeforeChallenge[0] / periodBeforeChallenge;
                progress = (int) ((averageExpensePerDay * periodIntoChallenge) - expensesByCategoryDuringChallenge[0]);
                challenge.setProgress(progress);
                break;
            case TRANSPORT:
                averageExpensePerDay = expensesByCategoryBeforeChallenge[1] / periodBeforeChallenge;
                progress = (int) ((averageExpensePerDay * periodIntoChallenge) - expensesByCategoryDuringChallenge[1]);
                challenge.setProgress(progress);
                break;
            case ENTERTAINMENT:
                averageExpensePerDay = expensesByCategoryBeforeChallenge[2] / periodBeforeChallenge;
                progress = (int) ((averageExpensePerDay * periodIntoChallenge) - expensesByCategoryDuringChallenge[2]);
                challenge.setProgress(progress);
                break;
            case RESTAURANT:
                averageExpensePerDay = expensesByCategoryBeforeChallenge[3] / periodBeforeChallenge;
                progress = (int) ((averageExpensePerDay * periodIntoChallenge) - expensesByCategoryDuringChallenge[3]);
                challenge.setProgress(progress);
                break;
            case CLOTHING:
                averageExpensePerDay = expensesByCategoryBeforeChallenge[4] / periodBeforeChallenge;
                progress = (int) ((averageExpensePerDay * periodIntoChallenge) - expensesByCategoryDuringChallenge[4]);
                challenge.setProgress(progress);
                break;
            default:
                averageExpensePerDay = expensesByCategoryBeforeChallenge[5] / periodBeforeChallenge;
                progress = (int) ((averageExpensePerDay * periodIntoChallenge) - expensesByCategoryDuringChallenge[5]);
                challenge.setProgress(progress);
                break;
        }

        if (challenge.getProgress() < 0) {
            challenge.setProgress(0);
        }

    }

    /**
     * Method for auto generating challenges for a given Goal based on bank transactions.
     * Takes in a list of transactions and a corresponding goal.
     * Randomly selects challenge type, and challenge target is set based on the willingness to change habits of the user connected to the goal.
     * Category for challenge is chosen by looking at which category has the most expenses / transactions in the given list.
     *
     * @param bankTransactions The list of bank transactions for the user.
     * @param goal             The goal for which the challenge is generated.
     * @return The generated challenge.
     * @throws IllegalArgumentException if the goal's end date has already been reached.
     */
    public Challenge autoGenerateChallengeForGoal(List<BankTransaction> bankTransactions, Goal goal) throws IllegalArgumentException {
        double[] expensesByCategory = bankTransactionService.calculateExpensesByCategory(bankTransactions);
        int[] transactionsByCategory = bankTransactionService.calculateNrOfTransactionsByCategory(bankTransactions);

        User user = goal.getUser();

        Challenge challenge = new Challenge();

        Period possiblePeriod = Period.between(LocalDate.now(), goal.getEndDate());

        if (possiblePeriod.isZero() || possiblePeriod.isNegative()) {
            throw new IllegalArgumentException("The goal's end date has already been reached.");
        }

        String challengeDuration;
        double scaleFactor = 1;
        if (random.nextInt(2) == 0 && possiblePeriod.getMonths() > 0) {
            challenge.setEndDate(LocalDate.now().plusMonths(1));
            challengeDuration = "måneden";
        } else if (possiblePeriod.getDays() < 7 && possiblePeriod.getMonths() == 0) {
            double baseScaleFactor = 0.033;
            scaleFactor = baseScaleFactor * possiblePeriod.getDays();
            challenge.setEndDate(LocalDate.now().plusDays(possiblePeriod.getDays()));
            if (possiblePeriod.getDays() > 1) {
                challengeDuration = possiblePeriod.getDays() + " dager";
            } else {
                challengeDuration = "dagen";
            }
        } else {
            challenge.setEndDate(LocalDate.now().plusWeeks(1));
            challengeDuration = "uken";
            scaleFactor = 0.25;
        }

        if (random.nextInt(2) == 0) {
            generateSavingTargetChallenge(challenge, expensesByCategory, user, scaleFactor, challengeDuration);
        } else {
            generateReduceSpendingChallenge(challenge, expensesByCategory, transactionsByCategory, user, scaleFactor, challengeDuration);
        }

        return challenge;
    }

    /**
     * Generates a "Saving Target" challenge based on given parameters.
     *
     * @param challenge The challenge object to be generated.
     * @param expensesByCategory An array of expenses by category.
     * @param user The user associated with the challenge.
     * @param scaleFactor The scaling factor used to determine the target amount.
     * @param challengeDuration The duration of the challenge.
     * @throws IllegalArgumentException if the challenge cannot be generated.
     */
    private void generateSavingTargetChallenge(Challenge challenge, double[] expensesByCategory, User user, double scaleFactor, String challengeDuration) throws IllegalArgumentException {
        challenge.setChallengeType(ChallengeType.SAVING_TARGET);
        double maxValue = 0;
        int maxCategory = 0;

        for (int i = 0; i < expensesByCategory.length - 1; i++) {
            if (expensesByCategory[i] > maxValue) {
                maxValue = expensesByCategory[i];
                maxCategory = i;
            }
        }

        maxValue = (int) (maxValue * scaleFactor);

        switch (user.getWillingnessToHabitChangeLevel()) {
            case HIGH:
                challenge.setTarget((int) ((int) maxValue * 0.75));
                break;
            case MEDIUM:
                challenge.setTarget((int) ((int) maxValue * 0.50));
                break;
            default:
                challenge.setTarget((int) ((int) maxValue * 0.25));
                break;
        }

        switch (maxCategory) {
            case 0:
                challenge.setName("Spar penger på dagligvarer!");
                challenge.setDescription("Spar minst " + challenge.getTarget() + "kr på dagligvarer neste " +  challengeDuration + ".");
                challenge.setCategory(ProductCategory.GROCERIES);
                break;
            case 1:
                challenge.setName("Spar penger på transport!");
                challenge.setDescription("Spar minst " + challenge.getTarget() + "kr på transport neste " +  challengeDuration + ".");
                challenge.setCategory(ProductCategory.TRANSPORT);
                break;
            case 2:
                challenge.setName("Spar penger på underholdning!");
                challenge.setDescription("Spar minst " + challenge.getTarget() + "kr på underholdning neste " +  challengeDuration + ".");
                challenge.setCategory(ProductCategory.ENTERTAINMENT);
                break;
            case 3:
                challenge.setName("Spar penger på restaurant!");
                challenge.setDescription("Spar minst " + challenge.getTarget() + "kr på restaurant neste " +  challengeDuration + ".");
                challenge.setCategory(ProductCategory.RESTAURANT);
                break;
            default:
                challenge.setName("Spar penger på klær!");
                challenge.setDescription("Spar minst " + challenge.getTarget() + "kr på klær neste " +  challengeDuration + ".");
                challenge.setCategory(ProductCategory.CLOTHING);
                break;
        }
    }

    /**
     * Generates a "Reduce Spending" challenge based on given parameters.
     *
     * @param challenge         The challenge object to be generated.
     * @param expensesByCategory An array of expenses by category.
     * @param transactionsByCategory An array of transactions by category.
     * @param user              The user associated with the challenge.
     * @param scaleFactor       The scaling factor used to determine the target amount.
     * @param challengeDuration The duration of the challenge.
     * @throws IllegalArgumentException if the challenge cannot be generated.
     */
    private void generateReduceSpendingChallenge(Challenge challenge, double[] expensesByCategory, int[] transactionsByCategory, User user, double scaleFactor, String challengeDuration) throws IllegalArgumentException {
        challenge.setChallengeType(ChallengeType.REDUCE_SPENDING);
        int maxValue = 0;
        int maxCategory = 0;

        for (int i = 0; i < transactionsByCategory.length - 1; i++) {
            if (transactionsByCategory[i] > maxValue) {
                maxValue = transactionsByCategory[i];
                maxCategory = i;
            }
        }

        maxValue = (int) (maxValue * scaleFactor);

        if (maxValue < 2) {
            generateSavingTargetChallenge(challenge, expensesByCategory, user, scaleFactor, challengeDuration);
            return;
        }

        switch (user.getWillingnessToHabitChangeLevel()) {
            case HIGH:
                challenge.setTarget((int) (maxValue * 0.25));
                break;
            case MEDIUM:
                challenge.setTarget((int) (maxValue * 0.50));
                break;
            default:
                challenge.setTarget((int) (maxValue * 0.75));
                break;
        }

        if (challenge.getTarget() < 1) challenge.setTarget(1);

        switch (maxCategory) {
            case 0:
                challenge.setName("Mindre dagligvarer!");
                challenge.setDescription("Reduser antall innkjøp av dagligvarer fra " + maxValue + " til " + challenge.getTarget() + " neste " +  challengeDuration + ".");
                challenge.setCategory(ProductCategory.GROCERIES);
                break;
            case 1:
                challenge.setName("Mindre transport!");
                challenge.setDescription("Reduser antall innkjøp av transporttjenester fra " + maxValue + " til " + challenge.getTarget() + " neste " +  challengeDuration + ".");
                challenge.setCategory(ProductCategory.TRANSPORT);
                break;
            case 2:
                challenge.setName("Mindre underholdning!");
                challenge.setDescription("Reduser antall innkjøp av underholdning fra " + maxValue + " til " + challenge.getTarget() + " neste " +  challengeDuration + ".");
                challenge.setCategory(ProductCategory.ENTERTAINMENT);
                break;
            case 3:
                challenge.setName("Mindre restaurant!");
                challenge.setDescription("Reduser antall besøk på restaurant fra " + maxValue + " til " + challenge.getTarget() + " neste " +  challengeDuration + ".");
                challenge.setCategory(ProductCategory.RESTAURANT);
                break;
            default:
                challenge.setName("Mindre klær!");
                challenge.setDescription("Reduser antall innkjøp av klær fra " + maxValue + " til " + challenge.getTarget() + " neste " +  challengeDuration + ".");
                challenge.setCategory(ProductCategory.CLOTHING);
                break;
        }
    }
}