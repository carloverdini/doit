package it.unicam.doit.repository;

import it.unicam.doit.model.Valutazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValutazioneRepository extends JpaRepository<Valutazione, Long> {
    List<Valutazione> findByIdRuoloProgetto(Long id);
}
