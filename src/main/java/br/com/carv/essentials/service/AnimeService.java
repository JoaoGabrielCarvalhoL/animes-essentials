package br.com.carv.essentials.service;

import br.com.carv.essentials.dto.request.AnimeInsertRequest;
import br.com.carv.essentials.dto.request.AnimeUpdateRequest;
import br.com.carv.essentials.dto.response.AnimeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnimeService {

    AnimeResponse findByKey(final Long key);

    AnimeResponse save(final AnimeInsertRequest animeInsertRequest);

    AnimeResponse update(final AnimeUpdateRequest animeUpdateRequest);

    List<AnimeResponse> findAll();

    Page<AnimeResponse> findAllPaginated(final Pageable pageable);

    void smartDelete(final Long id);

    List<AnimeResponse> findByName(final String name);
}
