package com.vukasin.perfumehub.repository;

import com.vukasin.perfumehub.entity.Accord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccordRepository extends JpaRepository<Accord, Long> {

    Optional<Accord> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}
