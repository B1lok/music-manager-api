package com.music_manager_api.web.controller;

import com.music_manager_api.service.SongService;
import com.music_manager_api.web.dto.song.*;
import com.music_manager_api.web.mapper.SongMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor
@Tag(name = "Song controller")
public class SongController {

  private final SongService songService;

  private final SongMapper songMapper;

  @PostMapping("/{artist-id}")
  @Operation(summary = "Create new song")
  public ResponseEntity<SongDto> create(@PathVariable(name = "artist-id") Long artistId,
                                        @Valid @RequestBody SongCreationDto songCreationDto) {
    var newSong = songService.create(songMapper.toEntity(songCreationDto),
        songCreationDto.getGenresNames(), artistId);
    return new ResponseEntity<>(songMapper.toDto(newSong), HttpStatus.CREATED);
  }

  @PostMapping("/upload")
  public ResponseEntity<SongUploadResponseDto> upload(@RequestParam(name = "file")MultipartFile file) throws IOException {
    return ResponseEntity.ok(songService.upload(file));
  }

  @PostMapping("/_list")
  public ResponseEntity<SongStatisticResponseDto> getStatistic(@Valid @RequestBody SongStatisticDto songStatisticDto){
    return ResponseEntity.ok(songService.getStatistic(songStatisticDto));
  }

  @PostMapping(value = "/_report", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  public ResponseEntity<Void> getReport(@Valid @RequestBody SongReportDto songReportDto,
                                        HttpServletResponse response) throws Exception{
    songService.getReport(response, songReportDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get song by id")
  public ResponseEntity<SongDto> getSongById(@PathVariable Long id) {
    return ResponseEntity.of(songService.getById(id).map(songMapper::toDto));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete song by id")
  public ResponseEntity<Void> deleteSongById(@PathVariable Long id) {
    songService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<SongDto> updateSongById(@PathVariable Long id,
                                                @Valid @RequestBody SongUpdateDto songUpdateDto){
    return ResponseEntity.of(songService.getById(id)
        .map(song -> songMapper.update(songUpdateDto, song))
        .map(song -> songService.update(song, songUpdateDto.getArtistId(), songUpdateDto.getGenresNames()))
        .map(songMapper::toDto));
  }

}
