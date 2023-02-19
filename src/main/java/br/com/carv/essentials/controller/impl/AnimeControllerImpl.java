package br.com.carv.essentials.controller.impl;

import br.com.carv.essentials.controller.AnimeController;
import br.com.carv.essentials.dto.request.AnimeInsertRequest;
import br.com.carv.essentials.dto.request.AnimeUpdateRequest;
import br.com.carv.essentials.dto.response.AnimeResponse;
import br.com.carv.essentials.service.AnimeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("animes")
public class AnimeControllerImpl implements AnimeController {

    private final AnimeService animeService;

    public AnimeControllerImpl(AnimeService animeService) {
        this.animeService = animeService;
    }

    @Override
    public ResponseEntity<AnimeResponse> save(AnimeInsertRequest animeInsertRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(animeService.save(animeInsertRequest));
    }

    @Override
    public ResponseEntity<AnimeResponse> update(AnimeUpdateRequest animeUpdateRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(animeService.update(animeUpdateRequest));
    }

    @Override
    public ResponseEntity<AnimeResponse> findById(Long id) {
        return ResponseEntity.ok(animeService.findByKey(id));
    }

    @Override
    public ResponseEntity<List<AnimeResponse>> findAll() {
        return ResponseEntity.ok(animeService.findAll());
    }

    @Override
    public ResponseEntity<Page<AnimeResponse>> findAllPaginated(Pageable pageable) {
        return ResponseEntity.ok(animeService.findAllPaginated(pageable));
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        animeService.smartDelete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<List<AnimeResponse>> findByName(String name) {
        return ResponseEntity.status(HttpStatus.OK).body(animeService.findByName(name));
    }
}
