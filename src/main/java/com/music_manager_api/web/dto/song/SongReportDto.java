package com.music_manager_api.web.dto.song;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class SongReportDto {
  @NotNull
  Long artistId;

  Sort.Direction releaseDateDirection;

  Sort.Direction durationDirection;
}
