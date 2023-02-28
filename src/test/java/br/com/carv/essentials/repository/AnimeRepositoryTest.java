package br.com.carv.essentials.repository;

import br.com.carv.essentials.domain.Anime;
import br.com.carv.essentials.util.AnimeCreator;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Anime Repository")
@ActiveProfiles("test")
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save persists anime when successful")
    void save_persistAnime_WhenSuccessful() {
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);
        Assertions.assertThat(animeSaved).isNotNull();
        Assertions.assertThat(animeSaved.getId()).isNotNull();
        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());
    }

    @Test
    @DisplayName("Save updates anime when successful")
    void save_updatesAnime_WhenSuccessful() {
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        animeSaved.setName("Digimon Adventure Updated");
        Anime animeUpdated = this.animeRepository.save(animeSaved);

        Assertions.assertThat(animeUpdated).isNotNull();
        Assertions.assertThat(animeUpdated.getId()).isNotNull();
        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());
    }

    @Test
    @DisplayName("Delete removes anime when successful")
    void delete_removesAnime_WhenSuccessful() {
        Anime animeToBeSaved = AnimeCreator.createValidAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        this.animeRepository.delete(animeSaved);
        Optional<Anime> animeResult = this.animeRepository.findById(animeSaved.getId());
        Assertions.assertThat(animeResult).isEmpty();
    }

    @Test
    @DisplayName("Find by name returns list of anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        String name = animeSaved.getName();
        List<Anime> resultList = this.animeRepository.findByName(name);
        Assertions.assertThat(resultList).isNotEmpty();
        Assertions.assertThat(resultList.get(0)).isNotNull();
        Assertions.assertThat(resultList.get(0).getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("Find by name returns empty list when no anime is found")
    void findByName_ReturnEmptyList_WhenAnimeIsNotFound() {
        List<Anime> resultList = this.animeRepository.findByName("Anime Not Found");
        Assertions.assertThat(resultList).isEmpty();
        Assertions.assertThat(resultList).isNotNull();
        Assertions.assertThat(resultList.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name is empty")
    void save_Throw_ConstraintViolationException_WhenNameIsEmpty() {
        Anime animeToBeSaved = AnimeCreator.createInvalidAnime();
        Assertions.assertThatThrownBy(() -> this.animeRepository.save(animeToBeSaved)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name is empty")
    void save_Throw_ConstraintViolationException_WhenNameIsEmpty_AnotherWay() {
        Anime animeToBeSaved = AnimeCreator.createInvalidAnime();
        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.animeRepository.save(animeToBeSaved))
                .withMessageContaining("The anime name cannot be empty");
    }



}