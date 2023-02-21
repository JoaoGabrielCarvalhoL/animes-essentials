package br.com.carv.essentials.bootstrap;

import br.com.carv.essentials.domain.Anime;
import br.com.carv.essentials.dto.request.AnimeInsertRequest;
import br.com.carv.essentials.dto.response.AnimeResponse;
import br.com.carv.essentials.dto.response.AnimeResponseClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class RestTemplateExample {

    public static void main(String[] args) {

        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/1", Anime.class);

        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/1", Anime.class);

        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes", Anime[].class);

        ResponseEntity<List<Anime>> exchange = new RestTemplate()
                .exchange("http://localhost:8080/animes", HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {});

        AnimeInsertRequest anime = new AnimeInsertRequest("Digimon Adventure");

        AnimeResponseClient animeResponse = new RestTemplate().postForObject("http://localhost:8080/animes", anime, AnimeResponseClient.class);

        ResponseEntity<AnimeResponseClient> animeResponseResponseEntity = new RestTemplate().postForEntity("http://localhost:8080/animes", anime, AnimeResponseClient.class);

        AnimeInsertRequest anotherAnime = new AnimeInsertRequest("Dragon Ball GT");

        ResponseEntity<AnimeResponseClient> postExchange = new RestTemplate().exchange("http://localhost:8080/animes", HttpMethod.POST, new HttpEntity<>(anotherAnime), AnimeResponseClient.class);

        ResponseEntity<Void> responseDelete = new RestTemplate().exchange("http://localhost:8080/animes/{id}", HttpMethod.DELETE, null, Void.class, 1L);

        System.out.println("Entity: " + entity + "\n");
        System.out.println("Object: " + object + "\n");
        System.out.println("Array: " + Arrays.toString(animes) + "\n");
        System.out.println("Exchange: " + exchange + "\n");
        System.out.println("Exchange Body: " + exchange.getBody() + "\n");
        System.out.println("Anime insert Post for Object: " + animeResponse + "\n");
        System.out.println("Anime Response Entity: " + animeResponseResponseEntity + "\n");
        System.out.println("Anime Response Entity: " + animeResponseResponseEntity.getBody() + "\n");
        System.out.println("Post Exchange: " + postExchange + "\n");
        System.out.println("Post Exchange Body: " + postExchange.getBody() + "\n");
        System.out.println("Delete Exchange: " + responseDelete + "\n");


    }
}
