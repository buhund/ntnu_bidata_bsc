package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.User;
import no.ntnu.idatt2106.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceTest {
  private static final String TEST_EMAIL = "tester@test.mail";
  private static final String TEST_PASSWORD = "password";
  private static final String TEST_FIRST_NAME = "Hans";
  private static final String TEST_LAST_NAME = "Hansen";

  @MockBean
  UserRepository userRepository;

  @Autowired
  UserService userService;

  @Autowired
  PasswordEncoder passwordEncoder;

  private User testUser;

  @BeforeEach
  void setup() {
    testUser = new User();
    testUser.setEmail(TEST_EMAIL);
    testUser.setPassword(TEST_PASSWORD);
    testUser.setFirstName(TEST_FIRST_NAME);
    testUser.setLastName(TEST_LAST_NAME);
  }

  @Test
  void testUpdateUserSavesUserAndDoesNotReturnPasswordHash() {
    User oldUser = new User();
    oldUser.setEmail(TEST_EMAIL);
    oldUser.setPassword(TEST_PASSWORD);
    oldUser.setFirstName(TEST_FIRST_NAME);
    oldUser.setLastName(TEST_LAST_NAME);

    Mockito.when(userRepository.save(oldUser)).thenReturn(testUser);

    User updatedUser = userService.updateUser(oldUser);

    assertEquals(updatedUser.getEmail(), testUser.getEmail());
    assertEquals(updatedUser.getPassword(), testUser.getPassword());
    Mockito.verify(userRepository, Mockito.times(1)).save(oldUser);
  }

  @Test
  void testFindAllUsersFindsAllUsers() {
    List<User> users = new ArrayList<>();
    users.add(testUser);

    Mockito.when(userRepository.findAll()).thenReturn(users);

    List<User> returnedUsers = userService.findAllUsers();

    assertEquals(1, returnedUsers.size());
    assertEquals(testUser.getEmail(), returnedUsers.getFirst().getEmail());
    Mockito.verify(userRepository, Mockito.times(1)).findAll();
  }

  @Test
  void testDeleteUserTriesToDeleteUser() {
    userService.deleteUser(testUser);
    Mockito.verify(userRepository, Mockito.times(1)).delete(testUser);
  }

  @Test
  void testFindByEmailCallsFindByEmail() {
    userService.findByEmail(testUser.getEmail());
    Mockito.verify(userRepository, Mockito.times(1)).findByEmail(testUser.getEmail());
  }
}
