package no.ntnu.idatt2106.service;


import no.ntnu.idatt2106.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {

    private final BadgeAwardService badgeAwardService;

    @Autowired
    public UserAccountService(BadgeAwardService badgeAwardService) {
        this.badgeAwardService = badgeAwardService;
    }

    /**
     * Handles the user account by awarding a badge based on the total savings.
     * @param user The user to handle the account for.
     * @param totalSavings The total savings of the user.
     */
    public void handleUserAccount(User user, double totalSavings) {
        badgeAwardService.awardSavingBadge(user, totalSavings);
    }
}