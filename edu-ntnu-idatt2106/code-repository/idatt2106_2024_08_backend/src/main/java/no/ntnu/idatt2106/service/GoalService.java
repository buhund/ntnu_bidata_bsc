package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.enums.AccountType;
import no.ntnu.idatt2106.enums.ChallengeType;
import no.ntnu.idatt2106.model.*;
import no.ntnu.idatt2106.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * The service layer is responsible for handling the logic for the Goal class.
 * It is the intermediary between the controller and the repository.
 */
@Service
public class GoalService {

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private BankTransactionService bankTransactionService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private BadgeAwardService badgeAwardService;

    /**
     * Updates a Goal object in the repository.
     *
     * @param goal The Goal object to be updated.
     * @return The updated Goal object.
     */
    public Goal updateGoal( Goal goal){
        badgeAwardService.awardGoalBadge(goal.getUser());
        return goalRepository.save(goal);
    }

    /**
     * Retrieves a Goal with the given ID.
     *
     * @param id The ID of the Goal to retrieve.
     * @return The retrieved Goal object with the specified ID, or null if no Goal is found.
     */
    public Goal findById(long id){
        return goalRepository.findById(id);
    }

    /**
    * Deletes a Goal from the repository.
    *
    * @param goal The Goal object to be deleted.
    */
    public void deleteGoal( Goal goal){
        goalRepository.delete(goal);
    }

    /**
     * Updates the progress of a Goal and checks if it is completed. If the progress
     * is equal to or greater than the target, the goal is considered completed.
     *
     * @param goal The Goal object to check the progress of.
     */
    public void checkProgress(Goal goal){
        if(goal.getProgress() >= goal.getTarget()){
            badgeAwardService.awardGoalBadge(goal.getUser());
            goal.setCompleted(true);
        } else if (LocalDate.now().isAfter(goal.getEndDate())) {
            goal.setFailed(true);
        }
        goalRepository.save(goal);
    }

    /**
    * Retrieves all goals associated with a specific user.
    *
    * @param user The User object representing the user whose goals to retrieve.
    * @return A list of Goal objects representing the goals of the user.
    */
    public List<Goal> findAllGoalsByUser(User user) {
    return goalRepository.findAllByUser(user);
    }

    /**
    * Method for setting start date for goal to current date
    *
    * @param goal Goal to set start date for.
    */
    public void setStartDateForGoalToToday(Goal goal) {
    goal.setStartDate(LocalDate.now());
    }

    /**
     * Method for updating progress on a goal.
     *
     * @param goal Goal to be updated.
     */
    public void updateProgress(Goal goal) {
        if (!goal.isCompleted()) {
            List<Challenge> challengesForGoal = challengeService.findAllChallengesByGoal(goal);
            int totalProgress = 0;
            for (Challenge c : challengesForGoal) {
                challengeService.updateProgress(c);
                if (c.getEndDate().isBefore(LocalDate.now())) {
                    if (Objects.requireNonNull(c.getChallengeType()) == ChallengeType.REDUCE_SPENDING) {
                        User user = goal.getUser();
                        BankAccount spendingAccount = bankAccountService.getBankAccountByAccountTypeAndUser(AccountType.SPENDING_ACCOUNT, user);
                        List<BankTransaction> transactions = bankTransactionService.getTransactionsByBankAccountInRange(c.getStartDate()
                         .minusMonths(1), c.getStartDate(), spendingAccount);
                        double averageAmount = bankTransactionService.calculateAverageByCategory(transactions, c.getCategory());
                        totalProgress += (int) (averageAmount * (c.getTarget() - c.getProgress()));
                    } else {
                        totalProgress += c.getProgress();
                    }
                        goal.setProgress(totalProgress);
                }
            }

            if (goal.getProgress() >= goal.getTarget()){
                badgeAwardService.awardGoalBadge(goal.getUser());
                goal.setCompleted(true);
            }
        }

        goalRepository.save(goal);
    }
}
