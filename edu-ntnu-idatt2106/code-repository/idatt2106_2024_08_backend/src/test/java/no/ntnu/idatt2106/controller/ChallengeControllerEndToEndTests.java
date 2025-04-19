package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.enums.ChallengeType;
import no.ntnu.idatt2106.enums.ProductCategory;
import no.ntnu.idatt2106.model.Challenge;
import no.ntnu.idatt2106.model.Goal;
import no.ntnu.idatt2106.model.User;
import no.ntnu.idatt2106.repository.ChallengeRepository;
import no.ntnu.idatt2106.repository.GoalRepository;
import no.ntnu.idatt2106.repository.UserRepository;
import no.ntnu.idatt2106.service.LoginService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ChallengeControllerEndToEndTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ChallengeRepository challengeRepo;

  @Autowired
  private UserRepository userRepo;

  @Autowired
  private GoalRepository goalRepo;

  @Autowired
  private LoginService loginService;

  private static final String TEST_EMAIL = "some@test.email";

  private String token;
  private long testGoalId;
  private long testChallengeId;
  private long testUserId;

  @BeforeEach
  void setup() throws Exception {
    token = loginService.generateToken(TEST_EMAIL);

    //Add another test user with bank accounts.
    this.mockMvc
        .perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\":  \"" + TEST_EMAIL + "\"," +
                " \"password\": \"password\"," +
                " \"firstName\": \"test\"," +
                " \"lastName\": \"test\"," +
                "\"willingnessToHabitChangeLevel\":  \"low\"," +
                "\"knowledgeLevel\":  \"low\"}"));

    User testUser = userRepo.findByEmail("some@test.email");

    //Add test goal
    Goal testGoal = new Goal();
    testGoal.setName("test");
    testGoal.setStartDate(LocalDate.now());
    testGoal.setEndDate(LocalDate.now().plusDays(1));
    testGoal.setProgress(0);
    testGoal.setTarget(1);
    testGoal.setUser(testUser);
    testGoalId = goalRepo.save(testGoal).getId();

    //Add test challenge
    Challenge testChallenge = new Challenge();
    testChallenge.setChallengeType(ChallengeType.REDUCE_SPENDING);
    testChallenge.setCategory(ProductCategory.OTHER);
    testChallenge.setName("test");
    testChallenge.setDescription("hello");
    testChallenge.setStartDate(LocalDate.now());
    testChallenge.setEndDate(LocalDate.now().plusDays(1));
    testChallenge.setProgress(0);
    testChallenge.setTarget(1);
    testChallenge.setGoal(testGoal);
    testChallenge.setUser(testUser);
    testChallengeId = challengeRepo.save(testChallenge).getId();
  }

  @AfterEach
  void cleanUp() {
    userRepo.deleteAll();
    goalRepo.deleteAll();
    challengeRepo.deleteAll();
  }

  @Test
  void testGetOnChallengeResourceEndpointReturnsChallenge() throws Exception {
    this.mockMvc
        .perform(get("/challenges/" + testChallengeId)
            .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.challengeType").value(ChallengeType.REDUCE_SPENDING.toString()))
        .andExpect(jsonPath("$.name").value("test"))
        .andExpect(jsonPath("$.description").value("hello"))
        .andExpect(jsonPath("$.startDate").value(LocalDate.now().toString()))
        .andExpect(jsonPath("$.endDate").value(LocalDate.now().plusDays(1).toString()))
        .andExpect(jsonPath("$.progress").value(0))
        .andExpect(jsonPath("$.target").value(1))
        .andExpect(jsonPath("$.category").value(ProductCategory.OTHER.toString()));
  }

  @Test
  void testGetOnChallengeResourceEndpointReturnsChallengeNotFoundWhenIdIsInvalid() throws Exception {
    this.mockMvc
        .perform(get("/challenges/" + 89347834)
            .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void testPostOnChallengesEndpointCreatesChallenge() throws Exception {
    this.mockMvc
        .perform(post("/challenges")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"lol\"," +
                " \"description\": \"lol\"," +
                " \"challengeType\": \"REDUCE_SPENDING\"," +
                " \"endDate\": \"2024-05-05\"," +
                " \"progress\":  0," +
                " \"target\": 1," +
                " \"category\": \"OTHER\"}"))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("lol"))
        .andExpect(jsonPath("$.description").value("lol"))
        .andExpect(jsonPath("$.startDate").value(LocalDate.now().toString()))
        .andExpect(jsonPath("$.endDate").value("2024-05-05"))
        .andExpect(jsonPath("$.progress").value(0))
        .andExpect(jsonPath("$.category").value(ProductCategory.OTHER.toString()))
        .andExpect(jsonPath("$.target").value(1));
    assertEquals(2, challengeRepo.findAll().size());
  }

  @Test
  void testPutOnChallengeEndpointUpdatesChallenge() throws Exception {
    this.mockMvc
        .perform(put("/challenges/" + testChallengeId)
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"lol\"," +
                " \"description\": \"lol\"," +
                " \"challengeType\": \"REDUCE_SPENDING\"," +
                " \"startDate\": \"2024-05-04\"," +
                " \"endDate\": \"2024-05-05\"," +
                " \"progress\":  0," +
                " \"target\": 1," +
                " \"category\": \"OTHER\"}"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("lol"));
    assertEquals("lol", challengeRepo.findById(testChallengeId).orElse(null).getName());
  }

  @Test
  void testPutOnChallengeEndpointReturnsChallengeNotFoundWhenIdIsInvalid() throws Exception {
    this.mockMvc
        .perform(put("/challenges/" + 89347834)
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"lol\"," +
                " \"description\": \"lol\"," +
                " \"challengeType\": \"REDUCE_SPENDING\"," +
                " \"startDate\": \"2024-05-04\"," +
                " \"endDate\": \"2024-05-05\"," +
                " \"progress\":  0," +
                " \"target\": 1," +
                " \"category\": \"OTHER\"}"))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void testPostOnGoalChallengeResourceEndpointCreatesChallenge() throws Exception {
    this.mockMvc
        .perform(post("/challenges/goal/" + testGoalId)
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"lol\"," +
                " \"description\": \"lol\"," +
                " \"challengeType\": \"REDUCE_SPENDING\"," +
                " \"startDate\": \"2024-05-04\"," +
                " \"endDate\": \"2024-05-05\"," +
                " \"progress\":  0," +
                " \"target\": 1," +
                " \"category\": \"OTHER\"}"))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("lol"))
        .andExpect(jsonPath("$.description").value("lol"))
        .andExpect(jsonPath("$.startDate").value("2024-05-04"))
        .andExpect(jsonPath("$.endDate").value("2024-05-05"))
        .andExpect(jsonPath("$.progress").value(0))
        .andExpect(jsonPath("$.category").value(ProductCategory.OTHER.toString()))
        .andExpect(jsonPath("$.target").value(1));
    assertEquals(2, challengeRepo.findAll().size());
  }

  @Test
  void testPostOnGoalChallengeResourceEndpointReturnsNotFoundOnInvalidGoalId() throws Exception {
    this.mockMvc
        .perform(post("/challenges/goal/" + 345354543)
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"lol\"," +
                " \"description\": \"lol\"," +
                " \"challengeType\": \"REDUCE_SPENDING\"," +
                " \"startDate\": \"2024-05-04\"," +
                " \"endDate\": \"2024-05-05\"," +
                " \"progress\":  0," +
                " \"target\": 1," +
                " \"category\": \"OTHER\"}"))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void testGenerateChallengeForGoalEndpointReturnsChallenge() throws Exception {
    Resource resource = new ClassPathResource("dummybankstatement-dnb.csv");

    MockMultipartFile mockMultipartFile = new MockMultipartFile("file",
        resource.getFilename(),
        "text/csv",
        resource.getInputStream());

    this.mockMvc
        .perform(multipart("/users/" + TEST_EMAIL + "/transactions_csv_to_first_account")
            .file(mockMultipartFile)
            .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isCreated());

    this.mockMvc
        .perform(get("/challenges/generate/goal/" + testGoalId)
            .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(notNullValue()))
        .andExpect(jsonPath("$.startDate").value(LocalDate.now().toString()));
  }

  @Test
  void testGenerateChallengeForGoalEndpointReturnsNotFoundOnInvalidGoalId() throws Exception {
    this.mockMvc
        .perform(get("/challenges/generate/goal/" + 892348790)
            .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void testGetOnGoalChallengesEndpointReturnsAllChallengesConnectedToGoal() throws Exception {
    this.mockMvc
        .perform(get("/challenges/goal/" + testGoalId)
            .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].id").value(testChallengeId));
  }

  @Test
  void testGetOnGoalChallengesEndpointReturnsNotFoundOnInvalidGoalId() throws Exception {
    this.mockMvc
        .perform(get("/challenges/goal/" + 8778)
            .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void testGetOnUserChallengesEndpointReturnsAllChallengesConnectedToUser() throws Exception {
    this.mockMvc
        .perform(get("/challenges/user/" + TEST_EMAIL)
            .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].id").value(testChallengeId));
  }

  @Test
  void testGetOnUserChallengesEndpointReturnsNotFoundOnInvalidEmail() throws Exception {
    this.mockMvc
        .perform(get("/challenges/user/" + "someone@thatdoesnot.exist")
            .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void testDeleteOnChallengeResourceEndpointDeletesChallenge() throws Exception {
    this.mockMvc
        .perform(delete("/challenges/" + testChallengeId)
            .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("Goal with id: " + testChallengeId + " deleted successfully."));
    assertEquals(0, challengeRepo.findAll().size());
  }

  @Test
  void testDeleteOnChallengeResourceEndpointReturnsNotFoundOnInvalidId() throws Exception {
    this.mockMvc
        .perform(delete("/challenges/" + 879879)
            .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isNotFound());
  }
}
