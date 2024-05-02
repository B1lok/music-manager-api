package com.music_manager_api.service;

import com.music_manager_api.domain.Genre;
import com.music_manager_api.domain.Song;
import java.util.Optional;

public interface GenreService {
  Optional<Genre> findByName(String name);

  boolean existsByName(String name);

  Genre create(Genre genre);
}
