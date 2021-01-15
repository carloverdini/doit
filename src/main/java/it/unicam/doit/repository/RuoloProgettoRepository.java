package it.unicam.doit.repository;

import it.unicam.doit.model.Progetto;
import it.unicam.doit.model.RuoloProgetto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RuoloProgettoRepository extends JpaRepository<RuoloProgetto, Long> {
    List<RuoloProgetto> findByProgettoId(Long idProgetto);

}
