package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.Goal;
import no.ntnu.idatt2106.model.User;
import no.ntnu.idatt2106.repository.GoalRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GoalServiceTest {
  @MockBean
  private GoalRepository goalRepository;

  @Autowired
  private GoalService goalService;

  @Test
  void testFindByIdTriesToCallRepositoryFindById() {
    long id = 1L;
    goalService.findById(id);
    Mockito.verify(goalRepository, Mockito.times(1)).findById(id);
  }

  @Test
  void testUpdateGoalTriesToCallRepositorySave() {
    Goal emptyGoal = new Goal();
    goalService.updateGoal(emptyGoal);
    Mockito.verify(goalRepository, Mockito.times(1)).save(emptyGoal);
  }

  @Test
  void testDeleteGoalTriesToCallRepositoryDelete() {
    Goal emptyGoal = new Goal();
    goalService.deleteGoal(emptyGoal);
    Mockito.verify(goalRepository, Mockito.times(1)).delete(emptyGoal);
  }

  @Test
  void testFindAllByUserTriesToCallRepositoryFindByUser() {
    User user = new User();
    goalRepository.findAllByUser(user);
    Mockito.verify(goalRepository, Mockito.times(1)).findAllByUser(user);
  }

  @Test
  void testSetStartDateToNowActuallySetsStartDate() {
    Goal goal = new Goal();
    goalService.setStartDateForGoalToToday(goal);
    assertEquals(LocalDate.now(), goal.getStartDate());
  }

  @Test
  void testCheckProgressTriesToUpdateGoalAndChangesIsCompletedToTrueIfProgressIsHigherThanTarget() {
    Goal goal = new Goal();
    goal.setTarget(1);
    goal.setProgress(2);
    goalService.checkProgress(goal);
    Mockito.verify(goalRepository, Mockito.times(1)).save(goal);
    assertTrue(goal.isCompleted());
  }

  @Test
  void testCheckProgressTriesToUpdateGoalAndDoesntSetIsCompletedToTrueIfProgressIsLowerThanTarget() {
    Goal goal = new Goal();
    goal.setTarget(2);
    goal.setProgress(1);
    goal.setEndDate(LocalDate.now().plusDays(1)); // set a deadline for the goal
    goalService.checkProgress(goal);
    Mockito.verify(goalRepository, Mockito.times(1)).save(goal);
    assertFalse(goal.isCompleted());
  }
}
