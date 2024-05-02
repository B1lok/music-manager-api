package com.music_manager_api.web.mapper;


import com.music_manager_api.domain.Artist;
import com.music_manager_api.web.dto.artist.ArtistCreationDto;
import com.music_manager_api.web.dto.artist.ArtistDto;
import com.music_manager_api.web.dto.artist.ArtistUpdateDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArtistMapper {
  Artist toEntity(ArtistCreationDto artistCreationDto);

  ArtistDto toDto(Artist artist);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Artist update(ArtistUpdateDto artistDto, @MappingTarget Artist artist);
}
