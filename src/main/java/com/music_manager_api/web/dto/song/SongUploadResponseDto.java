package com.music_manager_api.web.dto.song;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class SongUploadResponseDto {
  Integer successfullyAdded;
  Integer failedToAdd;
  List<String> errorMessages;
}
