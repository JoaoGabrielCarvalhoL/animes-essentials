package br.com.carv.essentials.service.impl;

import br.com.carv.essentials.domain.Anime;
import br.com.carv.essentials.dto.request.AnimeInsertRequest;
import br.com.carv.essentials.dto.request.AnimeUpdateRequest;
import br.com.carv.essentials.dto.response.AnimeResponse;
import br.com.carv.essentials.mapper.AnimeMapper;
import br.com.carv.essentials.repository.AnimeRepository;
import br.com.carv.essentials.util.AnimeCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Anime Service")
@ActiveProfiles("test")
class AnimeServiceImplTest {

    @InjectMocks
    private AnimeServiceImpl animeService;
    @Mock
    private AnimeRepository animeRepository;

    @BeforeEach
    void setup() {
        List<Anime> animes = List.of(AnimeCreator.createValidAnime());
        PageImpl<Anime> animePage = new PageImpl<>(animes);
        BDDMockito.when(animeRepository.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(animePage);

        BDDMockito.when(animeRepository.findAll()).thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepository.save(ArgumentMatchers.any(Anime.class))).thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.when(animeRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepository.findByName(ArgumentMatchers.anyString())).thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.doNothing().when(animeRepository).delete(AnimeCreator.createValidAnime());
    }

    @Test
    @DisplayName("List returns list of anime inside page object when successful")
    void listPaginated_returnsListOfAnimesInsidePageObject_WhenSuccessful() {
        String expectedGame = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeRepository.findAll(PageRequest.of(0, 10));
        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList()).isNotEmpty();
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedGame);
    }
    @Test
    @DisplayName("List returns list of anime when successful")
    void list_ReturnsListOfAnimes_WhenSuccessful() {
        List<Anime> animes = animeRepository.findAll();
        Assertions.assertThat(animes).isNotNull();
        Assertions.assertThat(animes).isNotEmpty();

    }
    @Test
    @DisplayName("Returns anime when successful")
    void findById_ReturnsAnime_WhenSuccessful() {
        Anime anime = animeRepository.findById(1L).get();
        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isEqualTo(1L);
    }
    @Test
    @DisplayName("findByName returns list of anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {
        String param = "Digimon Adventure";
        List<Anime> anime = animeRepository.findByName(param);
        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime).isNotEmpty();
        Assertions.assertThat(anime.get(0).getName()).isEqualTo(param);
    }

    @Test
    @DisplayName("findByName returns an empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfwAnime_WhenAnimeIsNotFound() {
        BDDMockito.when(animeRepository.findByName(ArgumentMatchers.anyString())).thenReturn(Collections.emptyList());
        String param = "Naruto Shippuden";
        List<Anime> anime = animeRepository.findByName(param);
        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime).isEmpty();
    }


    @Test
    @DisplayName("save return anime when save is successful")
    void save_ReturnAnime_WhenSuccessful() {
        Anime anime = AnimeCreator.createAnimeToBeSaved();
        Anime animeResponse = animeRepository.save(anime);
        Assertions.assertThat(animeResponse).isNotNull();
        Assertions.assertThat(animeResponse.getName()).isEqualTo(anime.getName());
    }



    @Test
    @DisplayName("delete anime when successful")
    void delete_WhenSuccessful() {
        Assertions.assertThatCode(() -> animeService.smartDelete(1L)).doesNotThrowAnyException();

    }

}