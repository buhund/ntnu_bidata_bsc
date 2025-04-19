package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.Badge;
import no.ntnu.idatt2106.model.Goal;
import no.ntnu.idatt2106.model.User;
import no.ntnu.idatt2106.model.UserBadge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Service class to handle the logic and manipulate Badge Awards.
 */
@Service
public class BadgeAwardService {

    @Autowired
    private UserService userService;

    @Autowired
    private BadgeService badgeService;

    Logger logger = LoggerFactory.getLogger(BadgeAwardService.class);

    /**
     * Awards the user with the "Velkommen til Sparesti!" badge after the user logs in for the first time.
     * @param email The email of the user to award the badge to.
     */
    public void awardLoginBadge(String email) {
        User dbUser = userService.findByEmail(email);
        Badge welcomeBadge = badgeService.findByName("Velkommen til Sparesti!");
        awardBadge(dbUser, welcomeBadge);
    }

    /**
     * Awards the user with a streak badge if the user has logged in for 3, 7, or 14 consecutive days.
     * @param user The user to award the badge to.
     */
    public void awardStreakBadge( User user) {
        User dbUser = userService.findByEmail(user.getEmail());
        int loginStreak = dbUser.getLoginStreak();

        if (loginStreak == 3 || loginStreak == 7 || loginStreak == 14) {
            Badge streakBadge = badgeService.findByName(loginStreak + " Dagers Streak!");
            awardBadge(user, streakBadge);
        }
    }

    /**
     * Awards the user with a savings badge based on the total amount saved.
     * @param user The user to award the badge to.
     * @param totalSavings The total amount saved by the user.
     */
    public void awardSavingBadge( User user, double totalSavings) {
        if (totalSavings >= 20000) {
            Badge savingBadge = badgeService.findByName("20 000kr Spart!");
            awardBadge(user, savingBadge);
        } else if (totalSavings >= 10000) {
            Badge savingBadge = badgeService.findByName("10 000kr Spart!");
            awardBadge(user, savingBadge);
        } else if (totalSavings >= 5000) {
            Badge savingBadge = badgeService.findByName("5000kr Spart");
            awardBadge(user, savingBadge);
        } else if (totalSavings >= 1000) {
            Badge savingBadge = badgeService.findByName("1000kr Spart!");
            awardBadge(user, savingBadge);
        }
    }

    /**
     * Awards the user with a goal badge based on the number of completed goals.
     * @param user The user to award the badge to.
     * */
    public void awardGoalBadge( User user) {
        if (user == null) {
            logger.warn("Tried to award badge to null user.");
            return;
        }
        User dbUser = userService.findByEmail(user.getEmail());
        if (dbUser == null) {
            logger.warn("Tried to award badge to user not in database.");
            return;
        }
        int completedGoals = dbUser.getGoals().stream().filter(Goal::isCompleted).toList().size();

        if (completedGoals >= 5) {
            Badge goalBadge = badgeService.findByName("10 mål gjennomført!");
            awardBadge(user, goalBadge);
        } else if (completedGoals >= 3) {
            Badge goalBadge = badgeService.findByName("5 mål gjennomført!");
            awardBadge(user, goalBadge);
        } else if (completedGoals >= 1) {
            Badge goalBadge = badgeService.findByName("Første mål!");
            awardBadge(user, goalBadge);
        }
    }

    public void awardAccountAgeBadge(User user) {
        User dbUser = userService.findByEmail(user.getEmail());
        long daysBetween = ChronoUnit.DAYS.between(dbUser.getCreationDate(), LocalDate.now());

        if (daysBetween == 30) {
            Badge durationBadge = badgeService.findByName("30 Dager Med Sparesti!");
            awardBadge(user, durationBadge);
        } else if (daysBetween == 180) {
            Badge durationBadge = badgeService.findByName("6 Måneder Med Sparesti!");
            awardBadge(user, durationBadge);
        }
    }

    /**
     * Used for awarding a badge to a user.
     * @param user The user to award the badge to.
     * @param badge The badge to award.
     */
    private void awardBadge( User user, Badge badge) {
        if (user == null) {
            logger.warn("Tried to award badge to null user.");
            return;
        }
        // Retrieve the user from the database
        User dbUser = userService.findByEmail(user.getEmail());
        if (dbUser == null) {
            logger.warn("Tried to award badge to user not in database.");
            return;
        }

        // Check if the user has already been awarded the badge
        if (dbUser.getUserBadges().stream().noneMatch(userBadge -> userBadge.getBadge().getName().equals(badge.getName()))) {
            // If not, award the badge
            UserBadge userBadge = new UserBadge();
            userBadge.setUser(dbUser);
            userBadge.setBadge(badge);
            userBadge.setAchieved(true);

            dbUser.getUserBadges().add(userBadge);

            // Save the updated user back to the database
            userService.updateUser(dbUser);
        }
    }

    /**
     * Scheduled method to check account age for all user and award badges accordingly.
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void awardAccountAgeBadgesChecker() {
        List<User> allUsers = userService.findAllUsers();
        for (User user : allUsers) {
            awardAccountAgeBadge(user);
        }
    }

}