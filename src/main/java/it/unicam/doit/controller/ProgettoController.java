package it.unicam.doit.controller;

import it.unicam.doit.exception.ResourceNotFoundException;
import it.unicam.doit.model.Progetto;
import it.unicam.doit.repository.ProgettoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ProgettoController {

    @Autowired
    ProgettoRepository progettoRepository;

    @GetMapping("/progetto/list")
    public List<Progetto> getAllProgetto() {
        return progettoRepository.findAll();
    }

    @GetMapping("/progetto/list/utente/{idProponente}")
    public List<Progetto> getFilteredProgetto(@PathVariable("idProponente") Long id_utente) {
        return (List<Progetto>) progettoRepository.findByUtente(id_utente);
    }

    @PostMapping("/progetto")
    public Progetto createProgetto(@Valid @RequestBody Progetto progetto) {
        return progettoRepository.save(progetto);
    }

    @GetMapping("/progetto/{id}")
    public Progetto getProgettoById(@PathVariable(value = "id") Long progettoId) {
        return progettoRepository.findById(progettoId)
                .orElseThrow(() -> new ResourceNotFoundException("Progetto", "id", progettoId));
    }

    @PutMapping("/progetto/{id}")
    public Progetto updateProgetto(@PathVariable(value = "id") Long progettoId,
                                   @Valid @RequestBody Progetto progettoDetails) {

        Progetto progetto = progettoRepository.findById(progettoId)
                .orElseThrow(() -> new ResourceNotFoundException("Progetto", "id", progettoId));

        progetto.setNome(progettoDetails.getNome());
        progetto.setDescrizione(progettoDetails.getDescrizione());
        progetto.setDataPubblicazione(progettoDetails.getDataPubblicazione());
        progetto.setDataScadenza(progettoDetails.getDataScadenza());

        Progetto updatedProgetto = progettoRepository.save(progetto);
        return updatedProgetto;
    }

    @DeleteMapping("/progetto/{id}")
    public ResponseEntity<?> deleteProgetto(@PathVariable(value = "id") Long progettoId) {
        Progetto progetto = progettoRepository.findById(progettoId)
                .orElseThrow(() -> new ResourceNotFoundException("Progetto", "id", progettoId));

        progettoRepository.delete(progetto);

        return ResponseEntity.ok().build();
    }
}
