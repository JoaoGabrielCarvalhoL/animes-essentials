package br.com.carv.essentials.util;

import br.com.carv.essentials.domain.Anime;
import br.com.carv.essentials.dto.request.AnimeInsertRequest;
import br.com.carv.essentials.dto.request.AnimeUpdateRequest;
import br.com.carv.essentials.dto.response.AnimeResponse;

public class AnimeCreator {

    public static Anime createAnimeToBeSaved() {
        return new Anime("Digimon Adventure");
    }

    public static Anime createValidAnime() {
        Anime anime = new Anime("Digimon Adventure");
        anime.setId(1L);
        return anime;
    }

    public static Anime createValidUpdatedAnime() {
        Anime anime = new Anime("Yu-Gi-Oh Update");
        anime.setId(1L);
        return anime;
    }

    public static Anime createInvalidAnime() {
        return new Anime();
    }

    public static AnimeResponse createValidAnimeResponse() {
        AnimeResponse animeResponse = new AnimeResponse(1L, "Naruto Shippuden");
        return animeResponse;
    }

    public static AnimeInsertRequest createAnimeInsertToBeSaved() {
        return new AnimeInsertRequest("Naruto Shippuden");
    }

    public static AnimeUpdateRequest createAnimeUpdateToBeSaved() {
        return new AnimeUpdateRequest(1L, "Naruto Shippuden");
    }
}
