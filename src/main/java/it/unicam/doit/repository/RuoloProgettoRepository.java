package it.unicam.doit.repository;

import it.unicam.doit.model.RuoloProgetto;
import it.unicam.doit.model.Progetto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuoloProgettoRepository extends JpaRepository<RuoloProgetto, Long> {
    RuoloProgetto findById(long id);
    RuoloProgetto findByTitolo(String titolo);
    List<RuoloProgetto> findByProgetto(Progetto progetto);
}
