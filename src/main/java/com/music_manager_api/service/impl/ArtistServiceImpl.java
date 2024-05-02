package com.music_manager_api.service.impl;

import com.music_manager_api.domain.Artist;
import com.music_manager_api.exception.ArtistAlreadyExistException;
import com.music_manager_api.repository.ArtistRepository;
import com.music_manager_api.service.ArtistService;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {

  private final ArtistRepository artistRepository;

  @Override
  public List<Artist> getAll() {
    return artistRepository.findAll();
  }

  @Override
  @Transactional
  public Artist create(Artist artist) {
    if (checkIfExistByName(artist)){
      throw new ArtistAlreadyExistException("Artist with name - %s already exist".formatted(artist.getName()));
    }
    return artistRepository.save(artist);
  }

  @Override
  @Transactional
  public Artist update(Artist artist) {
    if (checkIfExistByName(artist)){
      throw new ArtistAlreadyExistException("Artist with name - %s already exist".formatted(artist.getName()));
    } else {
      return artistRepository.save(artist);
    }
  }

  @Override
  public void deleteById(Long artistId) {
    artistRepository.deleteById(artistId);
  }

  @Override
  public Optional<Artist> findByName(String name) {
    return artistRepository.findByName(name);
  }

  @Override
  public boolean existsByName(String name) {
    return artistRepository.existsByName(name);
  }

  @Override
  public Optional<Artist> findById(Long id) {
    return artistRepository.findById(id);
  }


  private boolean checkIfExistByName(Artist artist){
    return artistRepository.findByName(artist.getName())
        .filter(found -> !found.getId().equals(artist.getId())).isPresent();
  }
}
