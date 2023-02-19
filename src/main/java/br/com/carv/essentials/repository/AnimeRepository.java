package br.com.carv.essentials.repository;

import br.com.carv.essentials.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Long> {

    //@Query(value = "SELECT a FROM Anime a WHERE a.name LIKE %:name%")
    List<Anime> findByName(@Param("name") String name);


}
