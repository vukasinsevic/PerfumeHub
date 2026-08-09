package com.vukasin.perfumehub.repository;

import com.vukasin.perfumehub.entity.Concentration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConcentrationRepository extends JpaRepository<Concentration, Long> {

    Optional<Concentration> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}
