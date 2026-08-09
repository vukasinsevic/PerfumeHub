package com.vukasin.perfumehub.repository;

import com.vukasin.perfumehub.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeasonRepository extends JpaRepository<Season, Long> {

    Optional<Season> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}
