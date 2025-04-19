package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.model.User;
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

import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerEndToEndTests {
  @Autowired
  MockMvc mockMvc;

  @Autowired
  LoginService loginService;

  @Autowired
  UserRepository userRepository;

  private String token;

  private static final String TEST_EMAIL = "email@email.email";
  private static final String TEST_PASSWORD = "password";
  private static final String TEST_FIRST_NAME = "hello";
  private static final String TEST_LAST_NAME = "world";

  @BeforeEach
  void generateJwtTokenAndAddTestUser() throws Exception {
    token = loginService.generateToken(TEST_EMAIL);

    //Add test user
    User testUser = new User();
    testUser.setEmail(TEST_EMAIL);
    testUser.setPassword(TEST_PASSWORD);
    testUser.setFirstName(TEST_FIRST_NAME);
    testUser.setLastName(TEST_LAST_NAME);
    userRepository.save(testUser);
  }

  @AfterEach
  void cleanDatabase() {
    userRepository.deleteAll();
  }

  @Test
  void testGetUsersEndpointReturnsUsersList() throws Exception {
    this.mockMvc
        .perform(get("/users").header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].email").value(TEST_EMAIL))
        .andExpect(jsonPath("$[0].password").value(nullValue()))
        .andExpect(jsonPath("$[0].firstName").value(TEST_FIRST_NAME))
        .andExpect(jsonPath("$[0].lastName").value(TEST_LAST_NAME));
  }

  @Test
  void testPostUsersEndpointReturnsPostedUserObject() throws Exception {
    this.mockMvc
        .perform(post("/users")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\"email\": \"test@test.test\"," +
                    "\"password\": \"password\"," +
                    "\"firstName\": \"test\"," +
                    "\"lastName\": \"test\"}"
            ))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.email").value("test@test.test"))
        .andExpect(jsonPath("$.password").value(nullValue()))
        .andExpect(jsonPath("$.firstName").value("test"))
        .andExpect(jsonPath("$.lastName").value("test"));
  }

  @Test
  void testPostUserWithEmailInUseTOUsersEndpointReturnsConflict() throws Exception {
    this.mockMvc
        .perform(post("/users")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\"email\": \"" + TEST_EMAIL + "\"," +
                    "\"password\": \"password\"," +
                    "\"firstName\": \"test\"," +
                    "\"lastName\": \"test\"}"
            ))
        .andDo(print())
        .andExpect(status().isConflict());
  }

  @Test
  void testGetOnUserResourceEndpointReturnsUserDetails() throws Exception {
    this.mockMvc
        .perform(get("/users/" + TEST_EMAIL).header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.email").value(TEST_EMAIL))
        .andExpect(jsonPath("$.password").value(nullValue()))
        .andExpect(jsonPath("$.firstName").value(TEST_FIRST_NAME))
        .andExpect(jsonPath("$.lastName").value(TEST_LAST_NAME));
  }

  @Test
  void testGetOnNonexistentUserResourceEndpointReturnsNotFound() throws Exception {
    this.mockMvc
        .perform(get("/users/" + "some@wrong.email").header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void testPutOnUserEndpointChangesRecordInDatabaseAndReturnsUpdatedUser() throws Exception {
    this.mockMvc
        .perform(put("/users/" + TEST_EMAIL).header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\": \"" + TEST_EMAIL + "\",\"firstName\":  \"bye\",\"lastName\": \"heaven\"}"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value(TEST_EMAIL))
        .andExpect(jsonPath("$.password").value(nullValue()))
        .andExpect(jsonPath("$.firstName").value("bye"))
        .andExpect(jsonPath("$.lastName").value("heaven"));
    User updatedUser = userRepository.findByEmail(TEST_EMAIL);
    assertEquals("bye", updatedUser.getFirstName());
    assertEquals("heaven", updatedUser.getLastName());
  }

  @Test
  void testPutOnUserEndpointWithNewEmailChangesTheEndpointOfTheUser() throws Exception {
    this.mockMvc
        .perform(put("/users/" + TEST_EMAIL).header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\": \"new@user.email\",\"firstName\":  \"bye\",\"lastName\": \"heaven\"}"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value("new@user.email"))
        .andExpect(jsonPath("$.password").value(nullValue()))
        .andExpect(jsonPath("$.firstName").value("bye"))
        .andExpect(jsonPath("$.lastName").value("heaven"));
    this.mockMvc
        .perform(get("/users/" + "new@user.email").header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value("new@user.email"))
        .andExpect(jsonPath("$.password").value(nullValue()))
        .andExpect(jsonPath("$.firstName").value("bye"))
        .andExpect(jsonPath("$.lastName").value("heaven"));
  }

  @Test
  void testPutOnUserEndpointWithNewEmailAlreadyInUseReturnsConflict() throws Exception {
    this.mockMvc
        .perform(post("/users")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\"email\": \"test@test.test\"," +
                    "\"password\": \"password\"," +
                    "\"firstName\": \"test\"," +
                    "\"lastName\": \"test\"}"
            ));
    this.mockMvc
        .perform(put("/users/" + TEST_EMAIL).header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\": \"test@test.test\",\"firstName\":  \"bye\",\"lastName\": \"heaven\"}"))
        .andDo(print())
        .andExpect(status().isConflict());
  }

  @Test
  void testPutOnNonExistentUserResourceEndpointReturnsNotFound() throws Exception {
    this.mockMvc
        .perform(put("/users/" + "some@wrong.email").header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\": \"test@test.test\",\"firstName\":  \"bye\",\"lastName\": \"heaven\"}"))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void testDeleteOnUserResourceEndpointDeletesUser() throws Exception {
    this.mockMvc
        .perform(delete("/users/" + TEST_EMAIL)
            .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("User with email: " + TEST_EMAIL + " deleted successfully."));
    this.mockMvc
        .perform(get("/users" + TEST_EMAIL)
            .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void testDeleteOnUserResourceEndpointThatDoesNotExistReturnsNotFound() throws Exception {
    this.mockMvc
        .perform(delete("/users/" + "some@wrong.email")
            .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isNotFound());
  }
}
