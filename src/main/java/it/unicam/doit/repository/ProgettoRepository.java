package it.unicam.doit.repository;

import it.unicam.doit.model.Progetto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface ProgettoRepository extends JpaRepository<Progetto, Long> {
    List<Progetto> findByUtente(Long utente);

}
