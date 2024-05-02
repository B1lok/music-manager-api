package com.music_manager_api.web.mapper;

import com.music_manager_api.domain.Genre;
import com.music_manager_api.domain.Song;
import com.music_manager_api.web.dto.song.*;
import java.util.Set;
import java.util.stream.Collectors;
import jdk.jfr.Name;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SongMapper {
  @Mapping(target = "genreNames", source = "song", qualifiedByName = "mapGenreNames")
  SongDto toDto(Song song);

  Song toEntity(SongCreationDto songCreationDto);

  Song toEntityFromUpload(SongUploadRequestDto songUploadRequestDto);

  @Mapping(target = "artistId", expression = "java(song.getArtist().getId())")
  @Mapping(target = "artistName", expression = "java(song.getArtist().getName())")
  SongForStatisticDto toStatisticResponse(Song song);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Song update(SongUpdateDto songUpdateDto, @MappingTarget Song song);

  @Named("mapGenreNames")
  default Set<String> mapGenreNames(Song song){
    return song.getGenres().stream()
        .map(Genre::getName)
        .collect(Collectors.toSet());
  }
}