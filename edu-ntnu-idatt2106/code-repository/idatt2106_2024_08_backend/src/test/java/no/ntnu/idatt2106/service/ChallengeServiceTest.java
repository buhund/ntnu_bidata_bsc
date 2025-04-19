package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.enums.ChallengeType;
import no.ntnu.idatt2106.model.Challenge;
import no.ntnu.idatt2106.repository.ChallengeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class ChallengeServiceTest {

    private static final long TEST_CHALLENGE_ID = 1L;
    private static final String TEST_CHALLENGE_NAME = "Test Challenge";
    private static final String TEST_CHALLENGE_DESCRIPTION = "Test Challenge Description";
    private static final int TEST_CHALLENGE_TARGET = 100;
    private static final int TEST_CHALLENGE_PROGRESS = 50;
    private static final LocalDate TEST_CHALLENGE_START_DATE = LocalDate.of(2024, 4, 1);
    private static final LocalDate TEST_CHALLENGE_END_DATE = LocalDate.of(2024, 5, 1);

    @MockBean
    ChallengeRepository challengeRepository;

    @Autowired
    ChallengeService challengeService;

    Challenge testChallenge;


    // Setup and teardown methods
    @BeforeEach
    void setup() {
        // Set up test challenge
        testChallenge = new Challenge();
        testChallenge.setId(TEST_CHALLENGE_ID);
        testChallenge.setName(TEST_CHALLENGE_NAME);
        testChallenge.setDescription(TEST_CHALLENGE_DESCRIPTION);
        testChallenge.setChallengeType(ChallengeType.SAVING_TARGET);
        testChallenge.setTarget(TEST_CHALLENGE_TARGET);
        testChallenge.setProgress(TEST_CHALLENGE_PROGRESS);
        testChallenge.setStartDate(TEST_CHALLENGE_START_DATE);
        testChallenge.setEndDate(TEST_CHALLENGE_END_DATE);
        testChallenge.setCompleted(false);


        when(challengeRepository.save(testChallenge)).thenReturn(testChallenge);
        when(challengeRepository.findById(testChallenge.getId())).thenReturn(Optional.of(testChallenge));
    }

    @AfterEach
    void tearDown() {
        challengeRepository.deleteAll();
        challengeRepository.flush();
    }

    // Helper methods
    /**
     * Helper method for printing details of Challenge
     *
     * @param expected Challenge object
     * @param actual Challenge object
     */
    public void printChallengeDetails(Challenge expected, Challenge actual) {
        if (expected != null && actual != null) {
            System.out.println("Comparing Challenges:");
            compareAttributes("Challenge ID", expected.getId(), actual.getId());
            compareAttributes("Name", expected.getName(), actual.getName());
            compareAttributes("Description", expected.getDescription(), actual.getDescription());
            compareAttributes("Type", expected.getChallengeType(), actual.getChallengeType());
            compareAttributes("Target", expected.getTarget(), actual.getTarget());
            compareAttributes("Progress", expected.getProgress(), actual.getProgress());
            compareAttributes("Start Date", expected.getStartDate(), actual.getStartDate());
            compareAttributes("End Date", expected.getEndDate(), actual.getEndDate());
            compareAttributes("Completed", expected.isCompleted(), actual.isCompleted());
            System.out.println();
        } else {
            System.out.println("One or both Challenge objects are null.");
        }
    }

    /**
     * Helper method for comparing attributes of Challenge
     *
     * @param attribute
     * @param expected
     * @param actual
     */
    private void compareAttributes(String attribute, Object expected, Object actual) {
        System.out.println(attribute + ": " + (expected.equals(actual) ? actual : "Expected [" + expected + "] but was [" + actual + "]"));
    }


    // Test methods

    @Test
    void testFindById() {
        when(challengeRepository.findById(testChallenge.getId())).thenReturn(Optional.of(testChallenge));

        Challenge foundChallenge = challengeService.findById(testChallenge.getId());

        assertNotNull(foundChallenge, "Expected non-null challenge");
        assertEquals(testChallenge.getId(), foundChallenge.getId(), "Expected challenge ID to match");
    }

    @Test
    void testFindByIdTryToCallRepositoryFindById() {
        long id = 1L;
        Challenge challenge = new Challenge();
        challengeService.findById(id);
        Mockito.verify(challengeRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void testUpdateChallengeTriesToCallRepositorySave() {
        Challenge challenge = new Challenge();
        challengeService.updateChallenge(challenge);
        Mockito.verify(challengeRepository, Mockito.times(1)).save(challenge);
    }

    @Test
    void testDeleteChallengeTriesToCallRepositoryDelete() {
        Challenge emptyChallenge = new Challenge();
        challengeService.deleteChallenge(emptyChallenge);
        Mockito.verify(challengeRepository, Mockito.times(1)).delete(emptyChallenge);
    }

    @Test
    void testSetStartDateForChallengeToToday() {
        Challenge challenge = new Challenge();
        challengeService.setStartDateForChallengeToToday(challenge);
        assertEquals(LocalDate.now(), challenge.getStartDate());
    }
}
