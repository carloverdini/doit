package it.unicam.doit.repository;

import it.unicam.doit.model.RichiestaCandidatura;
import it.unicam.doit.model.RuoloProgetto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RichiestaCandidaturaRepository extends JpaRepository<RichiestaCandidatura, Long> {
    RichiestaCandidatura findById(long id);
    List<RichiestaCandidatura> findByRuoloProgetto(RuoloProgetto rp);

}
