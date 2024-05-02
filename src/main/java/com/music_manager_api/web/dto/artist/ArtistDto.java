package com.music_manager_api.web.dto.artist;

import java.io.Serializable;
import lombok.Data;

/**
 * DTO for {@link com.music_manager_api.domain.Artist}
 */
@Data
public class ArtistDto implements Serializable {
  Long id;
  String name;
  String nationality;
  Integer numberOfFollowers;
}