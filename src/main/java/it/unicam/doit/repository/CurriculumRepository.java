package it.unicam.doit.repository;
import it.unicam.doit.model.Curriculum;
import it.unicam.doit.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {
    Curriculum findById(long id);
    List<Curriculum> findByProprietarioCurriculum(Utente utente);
}
