package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
class UserRepositoryIntegrationTests {

  private static final String TEST_USER_EMAIL = "mail@example.com";

  @Autowired
  UserRepository userRepository;

  @BeforeEach
  void addATestUser() {
    User user = new User();
    user.setEmail(TEST_USER_EMAIL);
    user.setPassword("password");
    user.setFirstName("Hans");
    user.setLastName("Hansen");
    userRepository.save(user);
  }

  @Test
  void testSaveAddUserToRepositoryIfNotExists() {
    User user = new User();
    String email = "test@test.test";
    user.setEmail(email);
    user.setPassword("password");
    user.setFirstName("name");
    user.setLastName("lastname");
    userRepository.save(user);
    User savedUser = userRepository.findByEmail(email);
    assertEquals(savedUser.getEmail(), user.getEmail());
    assertEquals(savedUser.getPassword(), user.getPassword());
    assertEquals(savedUser.getFirstName(), user.getFirstName());
    assertEquals(savedUser.getLastName(), user.getLastName());
  }

  @Test
  void testSaveModifiesExistingUser() {
    User user = userRepository.findByEmail(TEST_USER_EMAIL);
    long id = user.getId();
    user.setFirstName("Per");
    user.setLastName("Person");
    user.setPassword("passord");
    userRepository.save(user);
    userRepository.findById(id).ifPresentOrElse(savedUser -> {
      assertEquals(savedUser.getId(), user.getId());
      assertEquals(savedUser.getFirstName(), user.getFirstName());
      assertEquals(savedUser.getLastName(), user.getLastName());
      assertEquals(savedUser.getPassword(), user.getPassword());
    }, Assertions::fail);
  }

  @Test
  void testSaveModifiesExistingUserButDoesNotNullNullableFieldsWhenNotUpdatingNullableFields() {
    User user = userRepository.findByEmail(TEST_USER_EMAIL);
    long id = user.getId();
    User newUserInfo = new User();
    newUserInfo.setId(id);
    newUserInfo.setEmail(TEST_USER_EMAIL);
    newUserInfo.setFirstName("Per");
    userRepository.save(newUserInfo);
    userRepository.findById(id).ifPresentOrElse(savedUser -> {
      assertEquals(savedUser.getId(), user.getId());
      assertEquals(savedUser.getFirstName(), newUserInfo.getFirstName());
      assertEquals(savedUser.getLastName(), user.getLastName());
      assertEquals(savedUser.getPassword(), user.getPassword());
    }, Assertions::fail);
  }

  @Test
  void testDeleteDeletesExistingUser() {
    User user = userRepository.findByEmail(TEST_USER_EMAIL);
    userRepository.delete(user);
    assertNull(userRepository.findByEmail(TEST_USER_EMAIL));
  }

  @Test
  void testFindAllFindsAll() {
    User user = new User();
    String email = "test@test.test";
    user.setEmail(email);
    user.setPassword("password");
    user.setFirstName("name");
    user.setLastName("lastname");
    userRepository.save(user);
    List<User> userList = userRepository.findAll();
    assertEquals(2, userList.size());
    assertEquals(TEST_USER_EMAIL, userList.getFirst().getEmail());
    assertEquals("test@test.test", userList.get(1).getEmail());
  }
}
