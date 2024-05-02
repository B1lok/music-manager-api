package com.music_manager_api.web.dto.artist;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import lombok.Data;

/**
 * DTO for {@link com.music_manager_api.domain.Artist}
 */
@Data
public class ArtistUpdateDto implements Serializable {
  @NotEmpty(message = "Specify artist name")
  String name;
  @NotEmpty(message = "Specify artist nationality")
  String nationality;
  @NotNull(message = "Specify number of followers")
  @PositiveOrZero(message = "Should be greater or equal to zero")
  Integer numberOfFollowers;
}