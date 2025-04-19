package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.Badge;
import no.ntnu.idatt2106.model.Goal;
import no.ntnu.idatt2106.model.User;
import no.ntnu.idatt2106.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BadgeAwardServiceTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    TransactionProcessingService transactionProcessingService;

    @MockBean
    BadgeService badgeService;

    @Autowired
    BadgeAwardService badgeAwardService;

    @Autowired
    UserService userService;

    private static final String TEST_EMAIL = "another@test.email";
    private static final String TEST_PASSWORD = "password";
    private static final String TEST_FIRST_NAME = "A";
    private static final String TEST_LAST_NAME = "B";

    @BeforeEach
    void addTestUser() {
        // Create a test user
        User testUser = new User();
        testUser.setEmail(TEST_EMAIL);
        testUser.setPassword(TEST_PASSWORD);
        testUser.setFirstName(TEST_FIRST_NAME);
        testUser.setLastName(TEST_LAST_NAME);
        testUser.setUserBadges(new ArrayList<>());

        // Mock the response of findByEmail to return this test user
        Mockito.when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(testUser);
    }

    @Test
    void testAwardSavingBadge() {
        // Arrange
        User user = userRepository.findByEmail(TEST_EMAIL);
        user.setUserBadges(new ArrayList<>());

        Mockito.when(transactionProcessingService.processUserAccount(user)).thenReturn(20000.0);

        Badge existingBadge = new Badge();
        existingBadge.setName("20 000kr Spart!");
        Mockito.when(badgeService.findByName("20 000kr Spart!")).thenReturn(existingBadge);

        // Act
        badgeAwardService.awardSavingBadge(user, 20000.0);

        // Assert
        assertTrue(user.getUserBadges().stream().anyMatch(ub -> ub.getBadge().getName().equals("20 000kr Spart!")));
    }

    @Test
    void testAwardLoginBadge() {
        // Arrange
        User user = userRepository.findByEmail(TEST_EMAIL);
        Badge welcomeBadge = new Badge();
        welcomeBadge.setName("Velkommen til Sparesti!");
        Mockito.when(badgeService.findByName("Velkommen til Sparesti!")).thenReturn(welcomeBadge);

        // Act
        badgeAwardService.awardLoginBadge(user.getEmail());

        // Assert
        assertTrue(user.getUserBadges().stream().anyMatch(ub -> ub.getBadge().getName().equals("Velkommen til Sparesti!")));
    }

    @Test
    void testAwardStreakBadge() {
        // Arrange
        User user = userRepository.findByEmail(TEST_EMAIL);
        user.setLoginStreak(3);
        Badge streakBadge = new Badge();
        streakBadge.setName("3 Dagers Streak!");
        Mockito.when(badgeService.findByName("3 Dagers Streak!")).thenReturn(streakBadge);

        // Act
        badgeAwardService.awardStreakBadge(user);

        // Assert
        assertTrue(user.getUserBadges().stream().anyMatch(ub -> ub.getBadge().getName().equals("3 Dagers Streak!")));
    }

    @Test
    void testAwardGoalBadge() {
        // Arrange
        User user = userRepository.findByEmail(TEST_EMAIL);
        Goal goal = new Goal();
        goal.setCompleted(true);
        user.setGoals(List.of(goal));
        Badge goalBadge = new Badge();
        goalBadge.setName("Første mål!");
        Mockito.when(badgeService.findByName("Første mål!")).thenReturn(goalBadge);

        // Act
        badgeAwardService.awardGoalBadge(user);

        // Assert
        assertTrue(user.getUserBadges().stream().anyMatch(ub -> ub.getBadge().getName().equals("Første mål!")));
    }

    @Test
    void testAwardSavingBadgeNoSavings() {
        // Arrange
        User user = userRepository.findByEmail(TEST_EMAIL);
        user.setUserBadges(new ArrayList<>());
        Mockito.when(transactionProcessingService.processUserAccount(user)).thenReturn(0.0);

        // Act
        badgeAwardService.awardSavingBadge(user, 0.0);

        // Assert
        assertFalse(user.getUserBadges().stream().anyMatch(ub -> ub.getBadge().getName().equals("20 000kr Spart!")),
         "Badge should not be awarded for zero savings.");
    }

    @Test
    void testAwardStreakBadgeInterrupted() {
        // Arrange
        User user = userRepository.findByEmail(TEST_EMAIL);
        user.setLoginStreak(2);  // Not meeting the requirement for 3-day streak
        Mockito.when(badgeService.findByName("3 Dagers Streak!")).thenReturn(null);  // Assume no badge is returned

        // Act
        badgeAwardService.awardStreakBadge(user);

        // Assert
        assertTrue(user.getUserBadges().isEmpty(), "No streak badge should be awarded for an interrupted streak.");
    }

    @Test
    void testAwardRepeatedGoalCompletion() {
        // Arrange
        User user = userRepository.findByEmail(TEST_EMAIL);
        Goal goal1 = new Goal();
        goal1.setCompleted(true);
        Goal goal2 = new Goal();
        goal2.setCompleted(true);  // Same goal type, completed twice
        user.setGoals(List.of(goal1, goal2));
        Badge goalBadge = new Badge();
        goalBadge.setName("First Goal!");
        Mockito.when(badgeService.findByName("First Goal!")).thenReturn(goalBadge);

        // Act
        badgeAwardService.awardGoalBadge(user);

        // Assert
        assertEquals(1, user.getUserBadges().size(), "Badge for the first goal should only be awarded once.");
    }

    @Test
    void testAwardAccountDuration30DayBadge() {
        // Arrange
        User user = userRepository.findByEmail(TEST_EMAIL);
        user.setCreationDate(LocalDate.now().minusDays(30));  // Set creation date to 30 days ago
        Badge durationBadge = new Badge();
        durationBadge.setName("30 Dager Med Sparesti!");
        Mockito.when(badgeService.findByName("30 Dager Med Sparesti!")).thenReturn(durationBadge);

        // Act
        badgeAwardService.awardAccountAgeBadge(user);

        // Assert
        assertTrue(user.getUserBadges().stream().filter(Objects::nonNull).anyMatch(ub -> ub.getBadge() != null
         && ub.getBadge().getName().equals("30 Dager Med Sparesti!")));
    }

    @Test
    void testAwardAccountDuration180DayBadge() {
        // Arrange
        User user = userRepository.findByEmail(TEST_EMAIL);
        user.setCreationDate(LocalDate.now().minusDays(180));  // Set creation date to 30 days ago
        Badge durationBadge = new Badge();
        durationBadge.setName("6 Måneder Med Sparesti!");
        Mockito.when(badgeService.findByName("6 Måneder Med Sparesti!")).thenReturn(durationBadge);

        // Act
        badgeAwardService.awardAccountAgeBadge(user);

        // Assert
        assertTrue(user.getUserBadges().stream().filter(Objects::nonNull).anyMatch(ub -> ub.getBadge() != null
         && ub.getBadge().getName().equals("6 Måneder Med Sparesti!")));
    }


    @Test
    void testAwardAccountAgeBadgeNoBadge() {
        // Arrange
        User user = userRepository.findByEmail(TEST_EMAIL);
        user.setCreationDate(LocalDate.now().minusDays(29));  // Set creation date to 29 days ago

        // Act
        badgeAwardService.awardAccountAgeBadge(user);

        // Assert
        assertTrue(user.getUserBadges().isEmpty(), "No badge should be awarded for less than 30 days.");
    }
}