package no.ntnu.idatt2106.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.internet.MimeMessage;
import no.ntnu.idatt2106.model.User;
import no.ntnu.idatt2106.repository.UserRepository;
import no.ntnu.idatt2106.service.LoginService;
import no.ntnu.idatt2106.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerEndToEndTests {

  @RegisterExtension
  static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP.dynamicPort())
      .withConfiguration(GreenMailConfiguration.aConfig().withUser("test", "password"))
      .withPerMethodLifecycle(false);

  @Autowired
  MockMvc mockMvc;

  @Autowired
  JavaMailSenderImpl javaMailSender;

  @Autowired
  UserService userService;

  @Autowired
  LoginService loginService;

  @Autowired
  UserRepository userRepository;

  private static final String TEST_EMAIL = "another@test.email";
  private static final String TEST_PASSWORD = "password";
  private static final String TEST_FIRST_NAME = "A";
  private static final String TEST_LAST_NAME = "B";

  @BeforeEach
  void addTestUser() {
    javaMailSender.setPort(greenMail.getSmtp().getPort());

    //Add test user
    User testUser = new User();
    testUser.setEmail(TEST_EMAIL);
    testUser.setPassword(TEST_PASSWORD);
    testUser.setFirstName(TEST_FIRST_NAME);
    testUser.setLastName(TEST_LAST_NAME);
    userService.updateUser(testUser);
  }

  @AfterEach
  void cleanDatabase() {
    userRepository.deleteAll();
  }

  @Test
  void testLoginEndpointReturnsJWTTokenOnValidUser() throws Exception {
    final Algorithm hmac512 = Algorithm.HMAC512(LoginService.KEY_STR);
    final JWTVerifier verifier = JWT.require(hmac512).build();

    MvcResult result = this.mockMvc
            .perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"" + TEST_EMAIL + "\", \"password\": \"" + TEST_PASSWORD + "\"}"))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

    assertDoesNotThrow(() -> {
      verifier.verify(result.getResponse().getContentAsString());
    });
  }

  @Test
  void testLoginEndpointUnauthorizedWhenEmailIsWrong() throws Exception {
    this.mockMvc
        .perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\": \"" + "some@wrong.email" + "\", \"password\": \"" + TEST_PASSWORD + "\"}"))
        .andDo(print())
        .andExpect(status().isUnauthorized());
  }

  @Test
  void testLoginEndpointUnauthorizedWhenPasswordIsWrong() throws Exception {
    this.mockMvc
        .perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\": \"" + TEST_EMAIL + "\", \"password\": \"" + "upperclasswomen" + "\"}"))
        .andDo(print())
        .andExpect(status().isUnauthorized());
  }

  @Test
  void testCheckTokenEndpointReturnsOKWhenSuppliedTokenIsValid() throws Exception {
    this.mockMvc
        .perform(get("/check_token")
            .header("Authorization", "Bearer " + loginService.generateToken(TEST_EMAIL)))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void testCheckTokenEndpointReturnsUnauthorizedWhenTokenIsInvalid() throws Exception {
    this.mockMvc
        .perform(get("/check_token")
            .header("Authorization", "Bearer " + "biafbjkøadsnjkødsnkødsbjkadsnkdsfnkløfdbjkdfnklø"))
        .andDo(print())
        .andExpect(status().isUnauthorized());
  }

  @Test
  void testForgotPasswordEndpointSendsEmailWhenEmailIsValid() throws Exception {
    User user = userService.findByEmail(TEST_EMAIL);
    String oldPassword = user.getPassword();

    this.mockMvc
        .perform(post("/forgot_password")
            .contentType(MediaType.TEXT_PLAIN)
            .content(TEST_EMAIL))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("New password sent to email " + TEST_EMAIL));

    user = userService.findByEmail(TEST_EMAIL);
    assertNotEquals(oldPassword, user.getPassword());

    MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
    assertTrue(GreenMailUtil.getBody(receivedMessage).endsWith("Hilsen alle oss i Sparesti"));
    assertTrue(GreenMailUtil.getBody(receivedMessage).startsWith("Hei"));
    assertEquals(1, receivedMessage.getAllRecipients().length);
    assertEquals(TEST_EMAIL, receivedMessage.getAllRecipients()[0].toString());
  }

  @Test
  void testForgotPasswordEndpointReturnsNotFoundOnWrongEmail() throws Exception {
    this.mockMvc
        .perform(post("/forgot_password")
            .contentType(MediaType.TEXT_PLAIN)
            .content("some@wrong.email"))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(content().string("No user found with email some@wrong.email"));
  }
}
