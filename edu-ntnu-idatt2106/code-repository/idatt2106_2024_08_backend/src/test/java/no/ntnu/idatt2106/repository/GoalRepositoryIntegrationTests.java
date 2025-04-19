package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.Goal;
import no.ntnu.idatt2106.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GoalRepositoryIntegrationTests {
  @Autowired
  GoalRepository goalRepository;

  @Autowired
  UserRepository userRepository;

  private static final String TEST_GOAL_NAME = "Spare til testing";
  private static final int TEST_GOAL_TARGET = 100;
  private static final int TEST_GOAL_PROGRESS = 100;
  private static final LocalDate TEST_GOAL_START_DATE = LocalDate.of(2024, Month.APRIL, 20);
  private static final LocalDate TEST_GOAL_END_DATE = LocalDate.of(2024, Month.APRIL, 24);

  private long testGoalId;

  @BeforeEach
  void addATestGoal() {
    Goal testGoal = new Goal();
    testGoal.setName(TEST_GOAL_NAME);
    testGoal.setStartDate(TEST_GOAL_START_DATE);
    testGoal.setEndDate(TEST_GOAL_END_DATE);
    testGoal.setProgress(TEST_GOAL_PROGRESS);
    testGoal.setTarget(TEST_GOAL_TARGET);
    testGoalId = goalRepository.save(testGoal).getId();
  }

  @AfterEach
  void removeATestGoal() {
    goalRepository.deleteAll();
  }

  @Test
  void testFindAllReturnsAllGoals() {
    List<Goal> goals = goalRepository.findAll();
    assertEquals(1, goals.size());
    assertEquals(TEST_GOAL_NAME, goals.getFirst().getName());
    assertEquals(TEST_GOAL_TARGET, goals.getFirst().getTarget());
    assertEquals(TEST_GOAL_PROGRESS, goals.getFirst().getProgress());
    assertEquals(TEST_GOAL_START_DATE, goals.getFirst().getStartDate());
    assertEquals(TEST_GOAL_END_DATE, goals.getFirst().getEndDate());
  }

  @Test
  void testFindByIdReturnsGoal() {
    Goal goal = goalRepository.findById(testGoalId);
    assertNotNull(goal);
    assertEquals(TEST_GOAL_NAME, goal.getName());
    assertEquals(TEST_GOAL_TARGET, goal.getTarget());
    assertEquals(TEST_GOAL_PROGRESS, goal.getProgress());
    assertEquals(TEST_GOAL_START_DATE, goal.getStartDate());
    assertEquals(TEST_GOAL_END_DATE, goal.getEndDate());
  }

  @Test
  void testFindByIdDoesNotReturnGoalIfIdIsWrong() {
    Goal goal = goalRepository.findById(78032478903247890L);
    assertNull(goal);
  }

  @Test
  void testFindAllByUserReturnsAllGoals() {
    Goal newGoal = new Goal();
    newGoal.setName("l");
    newGoal.setStartDate(LocalDate.now());
    newGoal.setEndDate(LocalDate.of(2024, Month.APRIL, 30));
    newGoal.setProgress(0);
    newGoal.setTarget(1);

    User user = new User();
    user.setEmail("lol@lol.email");
    user.setFirstName("name");
    user.setLastName("lastname");
    user.setPassword("somePassword");
    userRepository.save(user);

    newGoal.setUser(user);
    goalRepository.save(newGoal);

    List<Goal> goals = goalRepository.findAllByUser(user);
    assertEquals(1, goals.size());
    assertEquals("l", goals.getFirst().getName());
    assertEquals(1, goals.getFirst().getTarget());
    assertEquals(0, goals.getFirst().getProgress());
    assertEquals(LocalDate.now(), goals.getFirst().getStartDate());
    assertEquals(LocalDate.of(2024, Month.APRIL, 30), goals.getFirst().getEndDate());
  }

  @Test
  void testGetAllByUserReturnsEmptyListIfUserHasNoGoals() {
    User user = new User();
    user.setEmail("lol@lol.email");
    user.setFirstName("name");
    user.setLastName("lastname");
    user.setPassword("somePassword");
    userRepository.save(user);
    List<Goal> goals = goalRepository.findAllByUser(user);
    assertEquals(0, goals.size());
  }

  @Test
  void testDeleteGoalRemovesGoal() {
    goalRepository.deleteById(testGoalId);
    List<Goal> goals = goalRepository.findAll();
    assertEquals(0, goals.size());
  }
}
