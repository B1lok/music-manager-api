package com.music_manager_api.web.dto.song;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;
import lombok.Value;

/**
 * DTO for {@link com.music_manager_api.domain.Song}
 */
@Data
public class SongForStatisticDto implements Serializable {
  Long id;
  String title;
  LocalDate releaseDate;
  Long artistId;
  String artistName;
  Integer duration;
}