package br.com.carv.essentials.service.impl;

import br.com.carv.essentials.controller.impl.AnimeControllerImpl;
import br.com.carv.essentials.domain.Anime;
import br.com.carv.essentials.dto.request.AnimeInsertRequest;
import br.com.carv.essentials.dto.request.AnimeUpdateRequest;
import br.com.carv.essentials.dto.response.AnimeResponse;
import br.com.carv.essentials.exception.ResourceNotFoundException;
import br.com.carv.essentials.mapper.AnimeMapper;
import br.com.carv.essentials.repository.AnimeRepository;
import br.com.carv.essentials.service.AnimeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class AnimeServiceImpl implements AnimeService {

    private final AnimeRepository animeRepository;
    private final AnimeMapper mapper;
    private final Logger logger = Logger.getLogger(AnimeServiceImpl.class.getSimpleName());

    public AnimeServiceImpl(AnimeRepository animeRepository, AnimeMapper mapper) {
        this.animeRepository = animeRepository;
        this.mapper = mapper;
    }

    @Override
    public AnimeResponse findByKey(Long key) {
        logger.info("Getting Anime by id: " + key);
        AnimeResponse anime = animeRepository.findById(key).filter(Anime::getIsActive).map(mapper::toAnimeResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Anime not found into database"));
        anime.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AnimeControllerImpl.class).findById(key)).withSelfRel());
        return anime;
    }

    @Override
    public AnimeResponse save(AnimeInsertRequest animeInsertRequest) {
        logger.info("Saving into database");
        Anime anime = mapper.toAnime(animeInsertRequest);
        //anime.changeActive(true);
        animeRepository.save(anime);
        return mapper.toAnimeResponse(anime).add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AnimeControllerImpl.class)
                .findById(anime.getId())).withSelfRel());
    }

    @Override
    public AnimeResponse update(AnimeUpdateRequest animeUpdateRequest) {
        logger.info("Update Anime");
        Anime anime = mapper.toAnime(animeUpdateRequest);
        //anime.changeActive(true);
        animeRepository.saveAndFlush(anime);
        return mapper.toAnimeResponse(anime).add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AnimeControllerImpl.class)
                .findById(anime.getId())).withSelfRel());

    }

    @Override
    public List<AnimeResponse> findAll() {
        logger.info("Getting all animes");
        List<AnimeResponse> collect = animeRepository.findAll().stream().filter(Anime::getIsActive)
                .map(mapper::toAnimeResponse).collect(Collectors.toList());
       collect.stream().forEach(anime -> anime.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AnimeControllerImpl.class)
                .findById(anime.getKey())).withSelfRel()));

       return collect;

    }

    @Override
    public Page<AnimeResponse> findAllPaginated(Pageable pageable) {
        logger.info("Getting all animes paginated");
        List<AnimeResponse> response = animeRepository.findAll(pageable).stream().filter(Anime::getIsActive)
                .map(mapper::toAnimeResponse).collect(Collectors.toList());
        response.stream().forEach(anime -> anime.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AnimeControllerImpl.class)
                .findById(anime.getKey())).withSelfRel()));
        return new PageImpl<AnimeResponse>(response, pageable, response.size());
    }

    @Override
    public void smartDelete(Long id) {
        logger.info("Disable anime by id: " + id);
        Anime anime = findByIdUtil(id);
        anime.changeActive(false);
        animeRepository.save(anime);

    }

    @Override
    public List<AnimeResponse> findByName(String name) {
        logger.info("Getting animes by param: " + name);
        return animeRepository.findByName(name).stream().filter(Anime::getIsActive).map(mapper::toAnimeResponse).collect(Collectors.toList());
    }

    private Anime findByIdUtil(Long id) {
        return animeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Anime not found into database!"));
    }

    //Only reference.
    public Page<Anime> anotherFindAllPaginated(Pageable pageable) {
        return animeRepository.findAll(pageable);
    }
}
