package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.User;
import no.ntnu.idatt2106.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * The service layer is responsible for handling the logic for the User class.
 * It is the intermediary between the controller and the repository.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Updates the user with the given information. If the user's password is provided, it will be encoded and updated.
     * If the password is not provided, the existing password will be used.
     *
     * @param user The user object with the updated information.
     * @return The updated user object.
     */
    public User updateUser(User user) {
        if (user.getPassword() != null) {
            User oldUser = userRepository.findByEmail(user.getEmail());
            if (oldUser != null) {
                if (!user.getPassword().equals(oldUser.getPassword())) {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                }
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        } else {
            Optional<User> oldUserOptional = userRepository.findById(user.getId());
            if (oldUserOptional.isPresent()) {
                User oldUser = oldUserOptional.get();
                user.setPassword(oldUser.getPassword());
            } else {
                throw new NoSuchElementException("No user found with id: " + user.getId());
            }
        }

        return userRepository.save(user);
    }

    /**
     * Deletes a user from the system.
     *
     * @param user The user to delete.
     */
    public void deleteUser(User user){
        userRepository.delete(user);
    }

    /**
     * Finds a User by their email address.
     *
     * @param userEmail The email address of the User to find.
     * @return The User object with the specified email address, or null if no User is found.
     */
    public User findByEmail(String userEmail){
        return userRepository.findByEmail(userEmail);
    }

    /**
     * Sets the password of the given user to null.
     *
     * @param user The user object to set the password to null.
     */
    public void setPasswordToNull(User user) {
        user.setPassword(null);
    }

    /**
     * Retrieves all users from the system.
     *
     * @return A list of all users in the system with their passwords set to null.
     */
    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        users.forEach(user -> user.setPassword(null));
        return users;
    }
}