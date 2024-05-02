package com.music_manager_api.service;

import com.music_manager_api.domain.Artist;
import com.music_manager_api.web.dto.artist.ArtistUpdateDto;
import java.util.List;
import java.util.Optional;

public interface ArtistService {

  List<Artist> getAll();

  Artist create(Artist artist);

  Artist update(Artist artist);

  void deleteById(Long artistId);
  boolean existsByName(String name);
  Optional<Artist> findByName(String name);

  Optional<Artist> findById(Long id);
}
