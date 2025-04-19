package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.Challenge;
import no.ntnu.idatt2106.model.Goal;
import no.ntnu.idatt2106.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interface for Challenge repository
 * The repository communicates with the database and handles the Challenge objects on behalf of the service layer
 */
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findAllByGoal(Goal goal);
    List<Challenge> findAllByUser(User user);
}
