package com.music_manager_api.repository;

import com.music_manager_api.domain.Genre;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
  Optional<Genre> findByNameIgnoreCase(String name);
  boolean existsByNameIgnoreCase(String name);
}
