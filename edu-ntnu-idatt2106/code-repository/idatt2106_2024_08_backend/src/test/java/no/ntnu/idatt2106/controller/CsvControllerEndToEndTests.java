package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.model.User;
import no.ntnu.idatt2106.repository.UserRepository;
import no.ntnu.idatt2106.service.LoginService;
import no.ntnu.idatt2106.service.UserService;
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

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CsvControllerEndToEndTests {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  CsvController csvController;

  @Autowired
  UserController userController;

  @Autowired
  LoginService loginService;

  @Autowired
  UserRepository userRepository;

  private static final String TEST_EMAIL = "some@test.email";

  private String token;

  private MockMultipartFile multipartDnbFile;
  private MockMultipartFile multipartDnbNoContent;
  private MockMultipartFile multipartNordeaFile;
  private MockMultipartFile multipartHandelsbankenFile; //Parsing not implemented, used for testing "bad" csv.

  @Autowired
  private UserService userService;

  @BeforeEach
  void addTestUser() throws Exception {
    token = loginService.generateToken(TEST_EMAIL);

    //Add test user with controller because it also creates bank accounts for the user.
    this.mockMvc
        .perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\":  \"some@test.email\"," +
                " \"password\": \"password\"," +
                " \"firstName\": \"test\"," +
                " \"lastName\": \"test\"}"));

    Resource dnbFile = new ClassPathResource("dummybankstatement-dnb.csv");
    Resource dnbNoContentFile = new ClassPathResource("dummybankstatement-dnb-no-content.csv");
    Resource nordeaFile = new ClassPathResource("dummybankstatement-nordea.csv");
    Resource handelsbankenFile = new ClassPathResource("dummybankstatement-handelsbanken.csv");

    multipartDnbFile = new MockMultipartFile("file",
        dnbFile.getFilename(),
        "text/csv",
        dnbFile.getInputStream());
    //Dnb dummybankstatement file without content, only header. Used for testing.
    multipartDnbNoContent = new MockMultipartFile("file",
        dnbNoContentFile.getFilename(),
        "text/csv",
        dnbNoContentFile.getInputStream());
    multipartNordeaFile = new MockMultipartFile("file",
        nordeaFile.getFilename(),
        "text/csv",
        nordeaFile.getInputStream());
    multipartHandelsbankenFile = new MockMultipartFile("file",
        handelsbankenFile.getFilename(),
        "text/csv",
        handelsbankenFile.getInputStream());
  }

  @AfterEach
  void clearTestUser() throws Exception {
    userRepository.deleteAll();
  }

  @Test
  void testCsvEndpointReturnsCreatedOnSuccessfullyParsingDnbCsv() throws Exception {
    this.mockMvc
        .perform(multipart("/users/" + TEST_EMAIL + "/transactions_csv_to_first_account")
            .file(multipartDnbFile)
            .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().string("Transactions successfully parsed and saved."));
  }

  @Test
  void testCsvEndpointReturnsCreatedOnSuccessfullyParsingNordeaCsv() throws Exception {
    this.mockMvc
        .perform(multipart("/users/" + TEST_EMAIL + "/transactions_csv_to_first_account")
            .file(multipartNordeaFile)
            .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().string("Transactions successfully parsed and saved."));
  }

  @Test
  void testCsvEndpointReturnsNotFoundOnNonExistingUser() throws Exception {
      String email = "some@wrong.email";
      this.mockMvc
          .perform(multipart("/users/" + email + "/transactions_csv_to_first_account")
              .file(multipartDnbFile)
              .header("Authorization", "Bearer " + token))
          .andDo(print())
          .andExpect(status().isNotFound())
          .andExpect(content().string("User with email " + email + " does not exist."));
  }

  @Test
  void testCsvEndpointReturnsBadRequestOnNoFile() throws Exception {
    this.mockMvc
        .perform(multipart("/users/" + TEST_EMAIL + "/transactions_csv_to_first_account")
            .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void testCsvEndpointReturnsUnprocessableEntityWhenUserHasNoAssociated() throws Exception {
    String email = "someone@somewhere.somethinng";
    User testUser = new User();
    testUser.setEmail(email);
    testUser.setFirstName("man");
    testUser.setLastName("woman");
    testUser.setPassword("lol");
    userRepository.save(testUser);

    this.mockMvc
        .perform(multipart("/users/" + email + "/transactions_csv_to_first_account")
            .file(multipartDnbFile)
            .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isUnprocessableEntity())
        .andExpect(content().string("User with email " + email + " has no associated bank accounts."));
  }

  @Test
  void testCsvEndpointReturnsBadRequestOnUnparsableCsv() throws Exception {
    this.mockMvc
        .perform(multipart("/users/" + TEST_EMAIL + "/transactions_csv_to_first_account")
            .file(multipartHandelsbankenFile)
            .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("Unsupported bank format with header:")));
  }

  @Test
  void testCsvEndpointReturnsBadRequestOnCorrectHeaderButNoContentInCsv() throws Exception {
    this.mockMvc
        .perform(multipart("/users/" + TEST_EMAIL + "/transactions_csv_to_first_account")
            .file(multipartDnbNoContent)
            .header("Authorization", "Bearer " + token))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().string("No transactions provided."));
  }
}
