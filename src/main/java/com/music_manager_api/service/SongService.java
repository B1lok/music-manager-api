package com.music_manager_api.service;

import com.music_manager_api.domain.Song;
import com.music_manager_api.web.dto.song.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.web.multipart.MultipartFile;

public interface SongService {
  Song create(Song song, Set<String> genres, Long artistId);

  Song update(Song song, Long artistId, Set<String> genres);

  Optional<Song> getById(Long id);
  void getReport(HttpServletResponse response, SongReportDto songReportDto) throws Exception;

  SongUploadResponseDto upload(MultipartFile file) throws IOException;

  SongStatisticResponseDto getStatistic(SongStatisticDto songStatisticDto);
  void deleteById(Long id);
}
