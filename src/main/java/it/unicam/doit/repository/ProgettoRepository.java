package it.unicam.doit.repository;
import it.unicam.doit.model.Progetto;
import it.unicam.doit.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgettoRepository extends JpaRepository<Progetto, Long> {
    Progetto findById(long id);
    Progetto findByTitolo(String titolo);
    List<Progetto> findByProponenteProgetto(Utente utente);
}
