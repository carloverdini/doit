package it.unicam.doit.repository;

import it.unicam.doit.model.Candidatura;
import it.unicam.doit.model.Valutazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValutazioneRepository extends JpaRepository<Valutazione, Long> {
    Valutazione findByCandidatura(Candidatura candidatura);
}
