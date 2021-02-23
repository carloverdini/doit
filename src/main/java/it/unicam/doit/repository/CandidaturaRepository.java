package it.unicam.doit.repository;

import it.unicam.doit.model.Candidatura;
import it.unicam.doit.model.RuoloProgetto;
import it.unicam.doit.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidaturaRepository extends JpaRepository<Candidatura, Long> {
    Candidatura findById(long id);
    List<Candidatura> findByRuoloProgetto(RuoloProgetto rp);
    List<Candidatura> findByCandidato(Utente candidato);
}
