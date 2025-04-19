package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface is a repository for managing Badge entities.
 */
@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {
    Badge findByName(String name);
}