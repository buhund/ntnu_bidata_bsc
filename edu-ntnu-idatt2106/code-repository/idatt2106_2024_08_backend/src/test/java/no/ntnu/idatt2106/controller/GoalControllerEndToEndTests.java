package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.model.Goal;
import no.ntnu.idatt2106.model.User;
import no.ntnu.idatt2106.repository.GoalRepository;
import no.ntnu.idatt2106.repository.UserRepository;
import no.ntnu.idatt2106.service.LoginService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GoalControllerEndToEndTests {
  @Autowired
  MockMvc mockMvc;

  @Autowired
  GoalRepository goalRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  LoginService loginService;

  private static final String TEST_EMAIL = "email@email.email";

  private static final String TEST_GOAL_NAME = "Spare til testing";
  private static final int TEST_GOAL_TARGET = 100;
  private static final int TEST_GOAL_PROGRESS = 100;
  private static final LocalDate TEST_GOAL_START_DATE = LocalDate.of(2024, Month.APRIL, 20);
  private static final LocalDate TEST_GOAL_END_DATE = LocalDate.of(2024, Month.APRIL, 24);

  private static long testGoalId;

  private String token;

  @BeforeEach
  void generateJwtTokenAndAddTestUser() {
    token = loginService.generateToken(TEST_EMAIL);

    //Add test user
    User testUser = new User();
    testUser.setEmail(TEST_EMAIL);
    testUser.setPassword("password");
    testUser.setFirstName("hello");
    testUser.setLastName("world");
    userRepository.save(testUser);

    //Add test goal
    Goal testGoal = new Goal();
    testGoal.setName(TEST_GOAL_NAME);
    testGoal.setStartDate(TEST_GOAL_START_DATE);
    testGoal.setEndDate(TEST_GOAL_END_DATE);
    testGoal.setProgress(TEST_GOAL_PROGRESS);
    testGoal.setTarget(TEST_GOAL_TARGET);
    testGoal.setUser(testUser);
    testGoalId = goalRepository.save(testGoal).getId();
  }

  @AfterEach
  void cleanDatabase() {
    userRepository.deleteAll();
    goalRepository.deleteAll();
  }

  @Test
  void testGetGoalByIdEndpointReturnsCorrectObjectWhenIdIsValid() throws Exception {
    this.mockMvc
        .perform(get("/goals/" + testGoalId)
            .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(testGoalId))
        .andExpect(jsonPath("$.name").value(TEST_GOAL_NAME))
        .andExpect(jsonPath("$.startDate").value(TEST_GOAL_START_DATE.toString()))
        .andExpect(jsonPath("$.endDate").value(TEST_GOAL_END_DATE.toString()))
        .andExpect(jsonPath("$.progress").value(TEST_GOAL_PROGRESS))
        .andExpect(jsonPath("$.target").value(TEST_GOAL_TARGET));
  }

  @Test
  void testGetGoalByIdEndpointReturnsNotFoundWhenIdIsInvalid() throws Exception {
    this.mockMvc
        .perform(get("/goals/" + "8778989789")
            .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void testGetGoalsByUserEmailEndpointReturnsAllGoals() throws Exception {
    this.mockMvc
        .perform(get("/goals/user/" + TEST_EMAIL)
            .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value(TEST_GOAL_NAME))
        .andExpect(jsonPath("$[0].startDate").value(TEST_GOAL_START_DATE.toString()))
        .andExpect(jsonPath("$[0].endDate").value(TEST_GOAL_END_DATE.toString()))
        .andExpect(jsonPath("$[0].progress").value(TEST_GOAL_PROGRESS))
        .andExpect(jsonPath("$[0].target").value(TEST_GOAL_TARGET));
  }

  @Test
  void testGetGoalsByUserEmailEndpointReturnsNotFoundWhenUserIsInvalid() throws Exception {
    this.mockMvc
        .perform(get("/goals/user/" + "some@wrong.email")
            .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void testPostOnGoalsEndpointAddsGoalAndReturnsAddedObject() throws Exception {
    this.mockMvc
        .perform(post("/goals")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"sparing\", \"endDate\": \"2024-05-02\", \"progress\": 0, \"target\": 100 }"))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("sparing"))
        .andExpect(jsonPath("$.startDate").value(LocalDate.now().toString()))
        .andExpect(jsonPath("$.endDate").value("2024-05-02"))
        .andExpect(jsonPath("$.progress").value(0))
        .andExpect(jsonPath("$.target").value(100));
    assertEquals(2, goalRepository.findAll().size());
  }

  @Test
  void testPostOnUserGoalsEndpointAddsGoalAndReturnsAddedObject() throws Exception {
    this.mockMvc
        .perform(post("/goals/user/" + TEST_EMAIL)
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"sparing\", \"endDate\": \"2024-05-02\", \"progress\": 0, \"target\": 100 }"))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("sparing"))
        .andExpect(jsonPath("$.startDate").value(LocalDate.now().toString()))
        .andExpect(jsonPath("$.endDate").value("2024-05-02"))
        .andExpect(jsonPath("$.progress").value(0))
        .andExpect(jsonPath("$.target").value(100));
    assertEquals(2, goalRepository.findAll().size());
  }

  @Test
  void testPostOnUserGoalsEndpointReturnsNotFoundWhenUserIsInvalid() throws Exception {
    this.mockMvc
        .perform(post("/goals/user/" + "some@wrong.email")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"sparing\", \"endDate\": \"2024-05-02\", \"progress\": 0, \"target\": 100 }"))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void testPutOnGoalsEndpointAddsGoalAndReturnsAddedObject() throws Exception {
    this.mockMvc
        .perform(put("/goals/" + testGoalId)
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"" + TEST_GOAL_NAME + "\"," +
                "\"startDate\": \"" + TEST_GOAL_START_DATE + "\"," +
                "\"endDate\": \"2024-05-02\", \"target\": 99," +
                "\"progress\": " + TEST_GOAL_TARGET + " }"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(TEST_GOAL_NAME))
        .andExpect(jsonPath("$.startDate").value(TEST_GOAL_START_DATE.toString()))
        .andExpect(jsonPath("$.endDate").value("2024-05-02"))
        .andExpect(jsonPath("$.progress").value(TEST_GOAL_PROGRESS))
        .andExpect(jsonPath("$.target").value(99));
    Goal goal = goalRepository.findById(testGoalId);
    assertEquals(TEST_GOAL_NAME, goal.getName());
  }

  @Test
  void testPutOnGoalsEndpointReturnsNotFoundWhenIdIsInvalid() throws Exception {
    this.mockMvc
        .perform(put("/goals/" + "9835489034890")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"" + TEST_GOAL_NAME + "\"," +
                "\"startDate\": \"" + TEST_GOAL_START_DATE + "\"," +
                "\"endDate\": \"2024-05-02\", \"target\": 99," +
                "\"progress\": " + TEST_GOAL_TARGET + " }"))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void testDeleteOnGoalsEndpointRemovesGoalAndReturnsCorrectResponse() throws Exception {
    this.mockMvc
        .perform(delete("/goals/" + testGoalId)
            .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("Goal with id: " + testGoalId + " deleted successfully."));
    assertEquals(0, goalRepository.findAll().size());
  }

  @Test
  void testDeleteOnGoalsEndpointReturnsNotFoundWhenIdIsInvalid() throws Exception {
    this.mockMvc
        .perform(delete("/goals/" + "78903897327890")
            .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isNotFound());
  }
}
