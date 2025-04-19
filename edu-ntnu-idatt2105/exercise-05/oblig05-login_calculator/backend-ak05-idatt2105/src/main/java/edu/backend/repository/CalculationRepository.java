package edu.backend.repository;

import edu.backend.model.Calculation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalculationRepository extends JpaRepository<Calculation, Long> {
  List<Calculation> findByAppUserId(Long appUserId);
  Page<Calculation> findByAppUserId(Long userId, Pageable pageable);

}