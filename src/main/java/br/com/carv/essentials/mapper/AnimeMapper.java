package br.com.carv.essentials.mapper;

import br.com.carv.essentials.domain.Anime;
import br.com.carv.essentials.dto.request.AnimeInsertRequest;
import br.com.carv.essentials.dto.request.AnimeUpdateRequest;
import br.com.carv.essentials.dto.response.AnimeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AnimeMapper {

    @Mapping(target = "key", source = "anime.id")
    AnimeResponse toAnimeResponse(Anime anime);

    Anime toAnime(AnimeInsertRequest animeInsertRequest);

    Anime toAnime(AnimeUpdateRequest animeUpdateRequest);
}
