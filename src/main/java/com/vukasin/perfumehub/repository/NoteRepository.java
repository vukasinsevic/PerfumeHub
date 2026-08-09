package com.vukasin.perfumehub.repository;

import com.vukasin.perfumehub.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {

    Optional<Note> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}
