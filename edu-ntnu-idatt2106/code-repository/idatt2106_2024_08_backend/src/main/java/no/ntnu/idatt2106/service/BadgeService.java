package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.Badge;
import no.ntnu.idatt2106.model.User;
import no.ntnu.idatt2106.model.UserBadge;
import no.ntnu.idatt2106.repository.BadgeRepository;
import no.ntnu.idatt2106.repository.UserBadgeRepository;
import no.ntnu.idatt2106.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The BadgeService class provides methods for managing badges and their assignment to users.
 */
@Service
public class BadgeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private UserBadgeRepository userBadgeRepository;

    /**
     * Assigns a badge to a user if the user does not already have the badge.
     *
     * @param user  The user to assign the badge to. Cannot be null.
     * @param badge The badge to assign. Cannot be null.
     */
    public void assignBadgeToUser( User user, Badge badge) {
        boolean hasBadge = user.getUserBadges().stream()
            .anyMatch(userBadge -> userBadge.getBadge().getName().equals(badge.getName()));
        if (hasBadge) {
            return;
        }

        UserBadge userBadge = new UserBadge();
        userBadge.setUser(user);
        userBadge.setBadge(badge);
        userBadge.setAchieved(true);
        user.getUserBadges().add(userBadge);

        userBadgeRepository.save(userBadge);
    }

    /**
     * Finds a badge by its name.
     *
     * @param name The name of the badge to find. Cannot be null.
     * @return The badge with the specified name, or null if no badge with the given name is found.
     */
    public Badge findByName(String name) {
        return badgeRepository.findByName(name);
    }

    /**
     * Finds all badges associated with a user based on their email.
     *
     * @param email The email of the user to retrieve badges for. Cannot be null.
     * @return A set of badges associated with the user, or an empty set if the user has no badges.
     * @throws IllegalArgumentException If no user is found with the given email.
     */
    public Set<Badge> findAllBadgesByUserEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            List<UserBadge> userBadges = user.getUserBadges();
            return userBadges.stream()
                .map(UserBadge::getBadge)
                .collect(Collectors.toSet());
        }
        throw new IllegalArgumentException("User not found");
    }

    /**
     * Finds all missing badges for a user based on their email.
     *
     * @param email The email of the user to find missing badges for. Cannot be null.
     * @return A set of badges that the user is missing, or an empty set if the user has all badges.
     * @throws IllegalArgumentException If no user is found with the given email.
     */
    public Set<Badge> findAllMissingBadgesByUserEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            List<UserBadge> userBadges = user.getUserBadges();
            Set<Badge> allBadges = new HashSet<>(badgeRepository.findAll());
            return allBadges.stream()
                .filter(badge -> userBadges.stream()
                    .noneMatch(userBadge -> userBadge.getBadge().getName().equals(badge.getName())))
                .collect(Collectors.toSet());
        }
        throw new IllegalArgumentException("User not found");
    }

    /**
     * Finds all badges in the badge repository.
     *
     * @return A list of all badges.
     */
    public List<Badge> findAllBadges() {
        return badgeRepository.findAll();
    }
}