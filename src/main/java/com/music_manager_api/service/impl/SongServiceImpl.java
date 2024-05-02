package com.music_manager_api.service.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.music_manager_api.domain.Song;
import com.music_manager_api.exception.ArtistNotFoundException;
import com.music_manager_api.exception.GenreNotFoundException;
import com.music_manager_api.exception.SongNotFoundException;
import com.music_manager_api.repository.SongRepository;
import com.music_manager_api.service.ArtistService;
import com.music_manager_api.service.GenreService;
import com.music_manager_api.service.SongService;
import com.music_manager_api.web.dto.song.*;
import com.music_manager_api.web.mapper.SongMapper;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Validator;
import java.io.IOException;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {

  private final ArtistService artistService;

  private final SongRepository songRepository;

  private final Validator validator;

  private final SongMapper songMapper;

  private final GenreService genreService;

  @Override
  @Transactional
  public Song create(Song song, Set<String> genres, Long artistId) {
    setArtist(song, artistId);
    addGenresToSong(song, genres);
    return songRepository.save(song);
  }

  @Override
  @Transactional
  public Song update(Song song, Long artistId, Set<String> genresNames) {
    setArtist(song, artistId);
    song.clearGenres();
    addGenresToSong(song, genresNames);
    return songRepository.save(song);
  }

  @Override
  public SongUploadResponseDto upload(MultipartFile file) throws IOException {
    List<String> errorMessages = new ArrayList<>();
    int successfullyUploaded = 0;
    int skipped = 0;

    List<SongUploadRequestDto> songs = parseSongsFromFile(file);

    for (SongUploadRequestDto songDto : songs) {
      if (!validator.validate(songDto).isEmpty()) {
        skipped++;
        continue;
      }
      if (isSongValidToSave(songDto, errorMessages)) {
        var artistId = artistService.findByName(songDto.getArtistName()).get().getId();
        create(songMapper.toEntityFromUpload(songDto), songDto.getGenresNames(), artistId);
        successfullyUploaded++;
      } else skipped++;
    }

    return SongUploadResponseDto.builder()
        .successfullyAdded(successfullyUploaded)
        .failedToAdd(skipped)
        .errorMessages(errorMessages)
        .build();
  }

  private List<SongUploadRequestDto> parseSongsFromFile(MultipartFile file) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    List<SongUploadRequestDto> songs = new ArrayList<>();

    try (JsonParser jsonParser = mapper.getFactory().createParser(file.getBytes())) {
      if (jsonParser.nextToken() != JsonToken.START_ARRAY) {
        throw new IllegalStateException("Expected content to be an array");
      }
      while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
        SongUploadRequestDto songDto = mapper.readValue(jsonParser, SongUploadRequestDto.class);
        songs.add(songDto);
      }
    }

    return songs;
  }


  @Override
  public Optional<Song> getById(Long id) {
    return songRepository.findById(id);
  }

  private boolean isSongValidToSave(SongUploadRequestDto song, List<String> errorMessages) {
    if (!artistService.existsByName(song.getArtistName())) {
      errorMessages.add(String.format("For song - %s, artist with name - %s does not exist",
          song.getTitle(), song.getArtistName()));
      return false;
    }
    boolean allGenresExist = song.getGenresNames().stream()
        .anyMatch(genre -> !genreService.existsByName(genre));

    if (allGenresExist) {
      errorMessages.add("At least one genre for the song - %s, does not exist".formatted(song.getTitle()));
      return false;
    }
    return true;
  }

  @Override
  public void getReport(HttpServletResponse response, SongReportDto songReportDto) throws Exception {
    String filename = "songs-report.csv";
    var sortSettings = getSortingSetting(songReportDto.getDurationDirection(), songReportDto.getReleaseDateDirection());
    var songsToWrite = songRepository.findByArtistId(songReportDto.getArtistId(), sortSettings)
        .stream()
        .map(songMapper::toStatisticResponse).toList();

    response.setContentType("text/csv");
    response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + filename + "\"");
    StatefulBeanToCsv<SongForStatisticDto> writer = new StatefulBeanToCsvBuilder<SongForStatisticDto>(response.getWriter())
        .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
        .withOrderedResults(false)
        .build();
    writer.write(songsToWrite);
  }

  @Override
  public SongStatisticResponseDto getStatistic(SongStatisticDto statisticDto) {
    Sort sortSettings = getSortingSetting(statisticDto.getDurationDirection(), statisticDto.getReleaseDateDirection());
    Pageable pageable = PageRequest.of(statisticDto.getPage() - 1, statisticDto.getSize(), sortSettings);
    Page<Song> pagedSongs = songRepository.findByArtistId(statisticDto.getArtistId(), pageable);

    var songsForStatistic = pagedSongs.getContent().stream()
        .map(songMapper::toStatisticResponse)
        .toList();
    return SongStatisticResponseDto.builder()
        .list(songsForStatistic)
        .totalPages(pagedSongs.getTotalPages())
        .build();
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    var song = songRepository.findById(id).orElseThrow(
        () -> new SongNotFoundException("Such song does not exist")
    );
    song.getGenres().forEach(genre -> genre.removeSong(song));
    song.getArtist().removeSong(song);
    songRepository.deleteById(id);
  }

  private void setArtist(Song song, Long artistId) {
    song.setArtist(artistService.findById(artistId).orElseThrow(
        () -> new ArtistNotFoundException("Artist with this id does not exist")
    ));
  }

  private Sort getSortingSetting(Sort.Direction durationDirection,
                                 Sort.Direction releaseDateDuration) {
    return Sort.by(durationDirection, "duration")
        .and(Sort.by(releaseDateDuration, "releaseDate"));
  }

  private void addGenresToSong(Song song, Set<String> genres) {
    genres.stream()
        .filter(Objects::nonNull)
        .filter(genre -> !genre.isEmpty())
        .forEach(
            genre -> song.addGenre(genreService.findByName(genre).orElseThrow(
                () -> new GenreNotFoundException("Such genre - %s does not exist".formatted(genre))
            ))
        );
  }
}