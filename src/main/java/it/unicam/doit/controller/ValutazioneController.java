package it.unicam.doit.controller;

import it.unicam.doit.exception.ResourceNotFoundException;
import it.unicam.doit.model.Valutazione;
import it.unicam.doit.repository.ValutazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class ValutazioneController {

    @Autowired
    ValutazioneRepository vRep;

    @GetMapping("/getValutazione/{id}")
    public Valutazione getValutazione(@PathVariable long vid){
        System.out.println("getValutazione");
        return vRep.findById(vid)
                .orElseThrow(() -> new ResourceNotFoundException("Candidatura", "id", vid));
    }

    @PostMapping("/addValutazione")
    public String addValutazione(@RequestBody Valutazione valutazione){
        System.out.println("addCandidatura");
        vRep.save(valutazione);
        return "valutazione creata correttamente";
    }

    @PutMapping("/updateValutazione/{id}")
    public Valutazione updateValutazione(@PathVariable(value = "id") Long vid,
                                       @Valid @RequestBody Valutazione vData) {
        Valutazione valutazione = vRep.findById(vid)
                .orElseThrow(() -> new ResourceNotFoundException("Valutazione", "id", vid));
        valutazione.setPunteggio(vData.getPunteggio());
        Valutazione updatedValutazione = vRep.save(valutazione);
        return updatedValutazione;
    }


    @DeleteMapping("/deleteValutazione/{id}")
    public ResponseEntity<?> deleteValutazione(@PathVariable(value = "id") Long vid) {
        Valutazione valutazione = vRep.findById(vid)
                .orElseThrow(() -> new ResourceNotFoundException("RuoloProgetto", "id", vid));

        vRep.delete(valutazione);

        return ResponseEntity.ok().build();
    }





}
