package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.Goal;
import no.ntnu.idatt2106.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interface for Goal repository
 * The repository communicates with the database and handles the Goal objects on behalf of the service layer
 */
public interface GoalRepository extends JpaRepository<Goal, Long> {
    Goal findById(long id);
    List<Goal> findAllByUser(User user);
}
