package it.unicam.doit.controller;

import it.unicam.doit.exception.ResourceNotFoundException;
import it.unicam.doit.model.RichiestaCandidatura;
import it.unicam.doit.repository.RichiestaCandidaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class RichiestaCandidaturaController {

    @Autowired
    RichiestaCandidaturaRepository rcRep;

    @GetMapping("/getRichiestaCandidatura/{id}")
    public RichiestaCandidatura getRichiestaCandidatura(@PathVariable long rcid){
        System.out.println("getRichiestaCandidatura");
        return rcRep.findById(rcid);
    }

    @PostMapping("/addRichiestaCandidatura")
    public String addRichiestaCandidatura(@RequestBody RichiestaCandidatura richiestaCandidatura){
        System.out.println("addRichiestaCandidatura");
        rcRep.save(richiestaCandidatura);
        return "valutazione creata correttamente";
    }

    @PutMapping("/updateRichiestaCandidatura/{rcid}")
    public RichiestaCandidatura updateRichiestaCandidatura(@PathVariable(value = "rcid") Long rcid,
                                       @Valid @RequestBody RichiestaCandidatura rcData) {
        RichiestaCandidatura richiestaCandidatura= rcRep.findById(rcid)
                .orElseThrow(() -> new ResourceNotFoundException("Valutazione", "id", rcid));
        richiestaCandidatura.setOggetto(rcData.getOggetto());
        richiestaCandidatura.setDescrizione(rcData.getDescrizione());
        RichiestaCandidatura updatedRC = rcRep.save(richiestaCandidatura);
        return updatedRC;
    }


    @DeleteMapping("/deleteRichiestaCandidatura/{rcid}")
    public ResponseEntity<?> deleteRichiestaCandidatura(@PathVariable(value = "rcid") Long rcid) {
        RichiestaCandidatura rc = rcRep.findById(rcid)
                .orElseThrow(() -> new ResourceNotFoundException("RuoloProgetto", "id", rcid));
        rcRep.delete(rc);
        return ResponseEntity.ok().build();
    }





}
