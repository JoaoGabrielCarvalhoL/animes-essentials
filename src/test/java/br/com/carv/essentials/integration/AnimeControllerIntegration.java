package br.com.carv.essentials.integration;

import br.com.carv.essentials.dto.request.AnimeInsertRequest;
import br.com.carv.essentials.dto.request.AnimeUpdateRequest;
import br.com.carv.essentials.dto.response.AnimeResponse;
import br.com.carv.essentials.service.AnimeService;
import br.com.carv.essentials.util.AnimeCreator;
import br.com.carv.essentials.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@DisplayName("Tests for Anime Integration")
public class AnimeControllerIntegration {

    @Autowired
    private TestRestTemplate testTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private AnimeService animeService;

    @Test
    @DisplayName("List returns list of anime inside page object when successful")
    void listPaginated_returnsListOfAnimesInsidePageObject_WhenSuccessful() {

        AnimeInsertRequest insertRequest = AnimeCreator.createAnimeInsertToBeSaved();
        animeService.save(insertRequest);

        ResponseEntity<PageableResponse<AnimeResponse>> exchange = testTemplate.exchange("/animes/paginated", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<AnimeResponse>>() {
        });
        Assertions.assertThat(exchange).isNotNull();
        Assertions.assertThat(exchange.getBody().toList()).isNotEmpty();



    }

    @Test
    @DisplayName("List returns list of anime when successful")
    void list_ReturnsListOfAnimes_WhenSuccessful() {

        AnimeInsertRequest insertRequest = AnimeCreator.createAnimeInsertToBeSaved();
        animeService.save(insertRequest);

        ResponseEntity<List<AnimeResponse>> exchange =
                testTemplate.exchange("/animes", HttpMethod.GET, null, new ParameterizedTypeReference<List<AnimeResponse>>() {
        });

        Assertions.assertThat(exchange).isNotNull();
        Assertions.assertThat(exchange.getBody()).isNotEmpty();
        Assertions.assertThat(exchange.getBody().get(0).getName()).isEqualTo(insertRequest.getName());

    }
    @Test
    @DisplayName("Returns anime when successful")
    void findById_ReturnsAnime_WhenSuccessful() {

        AnimeInsertRequest insertRequest = AnimeCreator.createAnimeInsertToBeSaved();
        AnimeResponse savedAnime = animeService.save(insertRequest);

        ResponseEntity<AnimeResponse> exchange = testTemplate.exchange("/animes/{id}", HttpMethod.GET, null, new ParameterizedTypeReference<AnimeResponse>() {
        }, savedAnime.getKey());

       Assertions.assertThat(exchange).isNotNull();
       Assertions.assertThat(exchange.getBody().getKey()).isEqualTo(savedAnime.getKey());
    }

    @Test
    @DisplayName("findByName returns list of anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {

        AnimeInsertRequest insertRequest = AnimeCreator.createAnimeInsertToBeSaved();
        AnimeResponse savedAnime = animeService.save(insertRequest);

        String url = String.format("/animes/param?name=%s", savedAnime.getName());
        List<AnimeResponse> body = testTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<AnimeResponse>>() {
        }).getBody();

        Assertions.assertThat(body).isNotNull();
        Assertions.assertThat(body).isNotEmpty();

    }

    @Test
    @DisplayName("findByName returns an empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfwAnime_WhenAnimeIsNotFound() {

        ResponseEntity<List<AnimeResponse>> exchange = testTemplate.exchange("/animes/param?name=", HttpMethod.GET, null, new ParameterizedTypeReference<List<AnimeResponse>>() {
        }, "Digimon Adventure");

        Assertions.assertThat(exchange).isNotNull();
        Assertions.assertThat(exchange.getBody().stream().toList()).isEmpty();

    }


    @Test
    @DisplayName("save return anime when save is successful")
    void save_ReturnAnime_WhenSuccessful() {

        AnimeInsertRequest insertRequest = AnimeCreator.createAnimeInsertToBeSaved();

        ResponseEntity<AnimeResponse> exchange = testTemplate.exchange("/animes", HttpMethod.POST, new HttpEntity<>(insertRequest), new ParameterizedTypeReference<AnimeResponse>() {
        });

        Assertions.assertThat(exchange).isNotNull();
        Assertions.assertThat(exchange.getBody().getName()).isEqualTo(insertRequest.getName());
        Assertions.assertThat(exchange.getBody().getKey()).isNotNull();

    }


    @Test
    @DisplayName("update anime and return anime when successful")
    void update_ReturnAnime_WhenSuccessful() {

        AnimeInsertRequest insertRequest = AnimeCreator.createAnimeInsertToBeSaved();
        AnimeUpdateRequest animeUpdateRequest = AnimeCreator.createAnimeUpdateToBeSaved();

        ResponseEntity<AnimeResponse> exchangePost = testTemplate.exchange("/animes", HttpMethod.POST, new HttpEntity<>(insertRequest), new ParameterizedTypeReference<AnimeResponse>() {
        });

        ResponseEntity<AnimeResponse> exchange = testTemplate.exchange("/animes", HttpMethod.PUT, new HttpEntity<>(animeUpdateRequest), new ParameterizedTypeReference<AnimeResponse>() {
        });

        Assertions.assertThat(exchange).isNotNull();
        Assertions.assertThat(exchange.getBody().getName()).isEqualTo(animeUpdateRequest.getName());
        Assertions.assertThat(exchange.getBody().getKey()).isNotNull();
    }

    @Test
    @DisplayName("delete anime when successful")
    void delete_WhenSuccessful() {

        AnimeInsertRequest insertRequest = AnimeCreator.createAnimeInsertToBeSaved();

        ResponseEntity<AnimeResponse> exchange = testTemplate.exchange("/animes", HttpMethod.POST, new HttpEntity<>(insertRequest), new ParameterizedTypeReference<AnimeResponse>() {
        });

        Assertions.assertThatCode(() -> testTemplate.exchange("/animes/{id}", HttpMethod.DELETE, null, new ParameterizedTypeReference<Void>() {
        }, exchange.getBody().getKey())).doesNotThrowAnyException();

    }
}
