package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for User repository
 * The repository communicates with the database and handles the User objects on behalf of the service layer
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}