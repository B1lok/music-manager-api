package com.music_manager_api.web.dto.song;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import lombok.Value;

/**
 * DTO for {@link com.music_manager_api.domain.Song}
 */
@Value
public class SongDto implements Serializable {
  Long id;
  String title;
  LocalDate releaseDate;
  ArtistDto artist;
  Set<String> genreNames;
  Integer duration;

  /**
   * DTO for {@link com.music_manager_api.domain.Artist}
   */
  @Value
  public static class ArtistDto implements Serializable {
    Long id;
    String name;
    String nationality;
    Integer numberOfFollowers;
  }
}