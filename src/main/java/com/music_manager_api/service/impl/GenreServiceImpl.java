package com.music_manager_api.service.impl;

import com.music_manager_api.domain.Genre;
import com.music_manager_api.domain.Song;
import com.music_manager_api.repository.GenreRepository;
import com.music_manager_api.service.GenreService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
  private final GenreRepository genreRepository;
  @Override
  public Optional<Genre> findByName(String name) {
    return genreRepository.findByNameIgnoreCase(name);
  }

  @Override
  public boolean existsByName(String name) {
    return genreRepository.existsByNameIgnoreCase(name);
  }

  @Override
  public Genre create(Genre genre) {
    return genreRepository.save(genre);
  }
}
