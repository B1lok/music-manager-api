package com.music_manager_api.web.dto.song;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class SongStatisticDto {

  @Min(value = 1, message = "Should be 1 or greater")
  Integer page;

  @Min(value = 1, message = "Should be 1 or greater")
  Integer size;

  @NotNull
  Long artistId;

  Sort.Direction releaseDateDirection;

  Sort.Direction durationDirection;
}
