package com.music_manager_api.repository;

import com.music_manager_api.domain.Artist;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
  Optional<Artist> findByName(String name);
  boolean existsByName(String name);
}
