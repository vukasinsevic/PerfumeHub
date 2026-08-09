package com.vukasin.perfumehub.repository;

import com.vukasin.perfumehub.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenderRepository extends JpaRepository<Gender, Long> {

    Optional<Gender> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}
