package com.music_manager_api.web.controller;

import com.music_manager_api.service.ArtistService;
import com.music_manager_api.web.dto.ExceptionResponse;
import com.music_manager_api.web.dto.artist.ArtistCreationDto;
import com.music_manager_api.web.dto.artist.ArtistDto;
import com.music_manager_api.web.dto.artist.ArtistUpdateDto;
import com.music_manager_api.web.mapper.ArtistMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/artists")
@Tag(name = "Artist controller")
public class ArtistController {

  private final ArtistService artistService;
  private final ArtistMapper artistMapper;

  @GetMapping
  @Operation(summary = "Get all artist")
  public ResponseEntity<List<ArtistDto>> getAll() {
    return ResponseEntity.ok(artistService.getAll().stream()
        .map(artistMapper::toDto).toList());
  }

  @PostMapping
  @Operation(summary = "Create new artist", responses = {
      @ApiResponse(responseCode = "201"),
      @ApiResponse(responseCode = "400", description = "Artist already exists",
          content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
  })
  public ResponseEntity<ArtistDto> create(@Valid @RequestBody ArtistCreationDto artistCreationDto) {
    var newArtist = artistService.create(artistMapper.toEntity(artistCreationDto));
    return new ResponseEntity<>(artistMapper.toDto(newArtist), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update existing artist by id", responses = {
      @ApiResponse(responseCode = "200"),
      @ApiResponse(responseCode = "400", description = "Artist already exists",
          content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
  })
  public ResponseEntity<ArtistDto> update(@PathVariable Long id,
                                          @Valid @RequestBody ArtistUpdateDto artistUpdateDto) {
    return ResponseEntity.of(artistService.findById(id)
        .map(artist -> artistMapper.update(artistUpdateDto, artist))
        .map(artistService::update)
        .map(artistMapper::toDto));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete artist by id", responses = {
      @ApiResponse(responseCode = "204")
  })
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    artistService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}