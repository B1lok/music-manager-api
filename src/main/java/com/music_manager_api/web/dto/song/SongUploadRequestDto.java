package com.music_manager_api.web.dto.song;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;
import lombok.Data;

@Data
public class SongUploadRequestDto {
  @NotEmpty(message = "Specify title")
  String title;
  @NotEmpty
  String artistName;
  @NotNull(message = "Specify song duration")
  @Positive
  Integer duration;
  @NotNull(message = "Specify Release Date")
  @PastOrPresent(message = "Specify past or present release date")
  LocalDate releaseDate;
  @NotNull(message = "Genres can not be null")
  @Size(message = "Specify at least one genre", min = 1)
  Set<String> genresNames;
}