package com.music_manager_api.repository;

import com.music_manager_api.domain.Song;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
  Page<Song> findByArtistId(Long id, Pageable pageable);

  List<Song> findByArtistId(Long id, Sort sort);

}