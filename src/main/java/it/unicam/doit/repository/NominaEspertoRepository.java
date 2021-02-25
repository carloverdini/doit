package it.unicam.doit.repository;
import it.unicam.doit.model.NominaEsperto;
import it.unicam.doit.model.RuoloProgetto;
import it.unicam.doit.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NominaEspertoRepository extends JpaRepository<NominaEsperto, Long> {
    NominaEsperto findById(long id);
    List<NominaEsperto> findByEsperto(Utente esperto);

    NominaEsperto findByRuoloProgetto(RuoloProgetto rp);
}
