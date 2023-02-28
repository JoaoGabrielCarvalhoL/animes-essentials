package br.com.carv.essentials.controller.impl;

import br.com.carv.essentials.dto.request.AnimeInsertRequest;
import br.com.carv.essentials.dto.request.AnimeUpdateRequest;
import br.com.carv.essentials.dto.response.AnimeResponse;
import br.com.carv.essentials.mapper.AnimeMapper;
import br.com.carv.essentials.service.impl.AnimeServiceImpl;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Anime Controller")
@ActiveProfiles("test")
class AnimeControllerImplTest {

    @InjectMocks
    private AnimeControllerImpl animeController;
    @Mock
    private AnimeServiceImpl animeService;


    private AnimeMapper mapper = Mappers.getMapper(AnimeMapper.class);

    @BeforeEach
    void setup() {

        List<AnimeResponse> animeResponseList = List.of(AnimeCreator.createValidAnime())
                .stream().map(mapper::toAnimeResponse).collect(Collectors.toList());
        PageImpl<AnimeResponse> animeResponses = new PageImpl<>(animeResponseList);
        BDDMockito.when(animeService.findAllPaginated(ArgumentMatchers.any())).thenReturn(animeResponses);

        List<AnimeResponse> findAll = List.of(AnimeCreator.createValidAnime()).stream().map(mapper::toAnimeResponse).collect(Collectors.toList());
        BDDMockito.when(animeService.findAll()).thenReturn(findAll);

        BDDMockito.when(animeService.save(ArgumentMatchers.any(AnimeInsertRequest.class))).thenReturn(AnimeCreator.createValidAnimeResponse());

        BDDMockito.when(animeService.findByKey(ArgumentMatchers.anyLong())).thenReturn(AnimeCreator.createValidAnimeResponse());

        BDDMockito.when(animeService.findByName(ArgumentMatchers.anyString())).thenReturn(List.of(AnimeCreator.createValidAnimeResponse()));

        BDDMockito.when(animeService.update(ArgumentMatchers.any(AnimeUpdateRequest.class))).thenReturn(AnimeCreator.createValidAnimeResponse());

        BDDMockito.doNothing().when(animeService).smartDelete(ArgumentMatchers.anyLong());

    }
    @Test
    @DisplayName("List returns list of anime inside page object when successful")
    void listPaginated_returnsListOfAnimesInsidePageObject_WhenSuccessful() {
        String expectedGame = AnimeCreator.createValidAnime().getName();
        Page<AnimeResponse> animePage = animeController.findAllPaginated(null).getBody();
        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList()).isNotEmpty();
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedGame);
    }
    @Test
    @DisplayName("List returns list of anime when successful")
    void list_ReturnsListOfAnimes_WhenSuccessful() {
        String expectedGame = AnimeCreator.createValidAnime().getName();
        List<AnimeResponse> animes = animeController.findAll().getBody();
        Assertions.assertThat(animes).isNotNull();
        Assertions.assertThat(animes).isNotEmpty();
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedGame);
    }
    @Test
    @DisplayName("Returns anime when successful")
    void findById_ReturnsAnime_WhenSuccessful() {
        AnimeResponse animeResponse = animeController.findById(1L).getBody();
        Assertions.assertThat(animeResponse).isNotNull();
        Assertions.assertThat(animeResponse.getKey()).isEqualTo(1L);
    }
    @Test
    @DisplayName("findByName returns list of anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {
        String param = "Naruto Shippuden";
        List<AnimeResponse> animeResponse = animeController.findByName(param).getBody();
        Assertions.assertThat(animeResponse).isNotNull();
        Assertions.assertThat(animeResponse).isNotEmpty();
        Assertions.assertThat(animeResponse.get(0).getName()).isEqualTo(param);
    }

    @Test
    @DisplayName("findByName returns an empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfwAnime_WhenAnimeIsNotFound() {
        BDDMockito.when(animeService.findByName(ArgumentMatchers.anyString())).thenReturn(Collections.emptyList());
        String param = "Naruto Shippuden";
        List<AnimeResponse> animeResponse = animeController.findByName(param).getBody();
        Assertions.assertThat(animeResponse).isNotNull();
        Assertions.assertThat(animeResponse).isEmpty();
    }


    @Test
    @DisplayName("save return anime when save is successful")
    void save_ReturnAnime_WhenSuccessful() {
        AnimeInsertRequest insertRequest = AnimeCreator.createAnimeInsertToBeSaved();
        AnimeResponse animeResponse = animeController.save(insertRequest).getBody();
        Assertions.assertThat(animeResponse).isNotNull();
        Assertions.assertThat(animeResponse.getName()).isEqualTo(insertRequest.getName());
    }


    @Test
    @DisplayName("update anime and return anime when successful")
    void update_ReturnAnime_WhenSuccessful() {
        AnimeUpdateRequest animeUpdateRequest = AnimeCreator.createAnimeUpdateToBeSaved();
        AnimeResponse animeResponse = animeController.update(animeUpdateRequest).getBody();
        Assertions.assertThat(animeResponse).isNotNull();
        Assertions.assertThat(animeUpdateRequest.getId()).isEqualTo(animeResponse.getKey());
        Assertions.assertThat(animeResponse.getName()).isEqualTo(animeUpdateRequest.getName());
    }

    @Test
    @DisplayName("delete anime when successful")
    void delete_WhenSuccessful() {
        Assertions.assertThatCode(() -> animeController.delete(1L)).doesNotThrowAnyException();

    }
}
