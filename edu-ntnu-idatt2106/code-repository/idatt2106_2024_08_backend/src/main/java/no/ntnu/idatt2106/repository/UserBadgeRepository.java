package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.UserBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing UserBadge entities.
 */
@Repository
public interface UserBadgeRepository extends JpaRepository<UserBadge, Long>{
}
