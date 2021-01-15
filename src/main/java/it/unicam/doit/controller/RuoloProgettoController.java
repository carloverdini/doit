package it.unicam.doit.controller;

import it.unicam.doit.exception.ResourceNotFoundException;
import it.unicam.doit.model.RuoloProgetto;
import it.unicam.doit.repository.ProgettoRepository;
import it.unicam.doit.repository.RuoloProgettoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api")
public class RuoloProgettoController {

    @Autowired
    RuoloProgettoRepository ruoloProgettoRepository;
    ProgettoRepository progettoRepository;

    @GetMapping("progetto/{id}/ruoloProgetto")
    public List<RuoloProgetto> getAllRuoloProgetto(@PathVariable(value = "id") Long idProgetto) {
        return ruoloProgettoRepository.findByProgettoId(idProgetto);
    }


    @PostMapping("/ruoloProgetto/{progettoId}")
    public RuoloProgetto createRuoloProgetto(@PathVariable(value = "progettoId") Long progettoId,
                                             @Valid @RequestBody RuoloProgetto ruoloProgetto) {
        return progettoRepository.findById(progettoId).map(prj -> {
            ruoloProgetto.setProgetto(prj);
            return ruoloProgettoRepository.save(ruoloProgetto);
        })
                .orElseThrow(() -> new ResourceNotFoundException("Progetto", "id", progettoId));
    }


    @GetMapping("/ruoloProgetto/{id}")
    public RuoloProgetto getRuoloProgettoById(@PathVariable(value = "id") Long ruoloProgettoId) {
        return ruoloProgettoRepository.findById(ruoloProgettoId)
                .orElseThrow(() -> new ResourceNotFoundException("RuoloProgetto", "id", ruoloProgettoId));
    }

    @PutMapping("/ruoloProgetto/{id}")
    public RuoloProgetto updateRuoloProgetto(@PathVariable(value = "id") Long ruoloProgettoId,
                                             @Valid @RequestBody RuoloProgetto ruoloProgettoDetails) {

        RuoloProgetto ruoloProgetto = ruoloProgettoRepository.findById(ruoloProgettoId)
                .orElseThrow(() -> new ResourceNotFoundException("RuoloProgetto", "id", ruoloProgettoId));

        ruoloProgetto.setTitolo(ruoloProgettoDetails.getTitolo());
        ruoloProgetto.setDescrizione(ruoloProgettoDetails.getDescrizione());

        RuoloProgetto updatedRuoloProgetto = ruoloProgettoRepository.save(ruoloProgetto);
        return updatedRuoloProgetto;
    }

    @DeleteMapping("/ruoloProgettos/{id}")
    public ResponseEntity<?> deleteRuoloProgetto(@PathVariable(value = "id") Long ruoloProgettoId) {
        RuoloProgetto ruoloProgetto = ruoloProgettoRepository.findById(ruoloProgettoId)
                .orElseThrow(() -> new ResourceNotFoundException("RuoloProgetto", "id", ruoloProgettoId));

        ruoloProgettoRepository.delete(ruoloProgetto);

        return ResponseEntity.ok().build();
    }
}
