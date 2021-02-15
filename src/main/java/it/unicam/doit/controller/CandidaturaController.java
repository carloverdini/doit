package it.unicam.doit.controller;

import it.unicam.doit.exception.ResourceNotFoundException;
import it.unicam.doit.model.Candidatura;
import it.unicam.doit.repository.CandidaturaRepository;
import it.unicam.doit.repository.RuoloProgettoRepository;
import it.unicam.doit.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class CandidaturaController {

    @Autowired
    CandidaturaRepository cRep;

    @Autowired
    RuoloProgettoRepository rpRep;

    @Autowired
    UtenteRepository uRep;
/*
    @GetMapping("/getCandidatureUtente/{idUtente}")
    public List<Candidatura> getCandidatureUtente(@PathVariable long uid){
        System.out.println("getCandidatureUtente");

        Utente utente = uRep.findById(uid);
        return cRep.findByUtente(utente);
    }

    @GetMapping("/getCandidatureRuoloProgetto/{idRuoloProgetto}")
    public List<Candidatura> getCandidatureRuoloProgetto(@PathVariable long rpid){
        System.out.println("getCandidatureRuoloProgetto");
        return cRep.findByIdRuoloProgetto(rpid);
    }

*/
/* definire stati candidatura,
    pending: "richiesta" da proponente
        "proposta" da progettista
    accettata
    rifitutata
*/
    @PostMapping("/addCandidatura")
    public String addCandidatura(@RequestBody Candidatura candidatura){
        System.out.println("addCandidatura");
        if(candidatura.getStato()=="" || candidatura.getStato()==null ) {
            candidatura.setStato("creato");
        }
        cRep.save(candidatura);
        return "candidatura creata correttamente";
    }

    @PutMapping("/updateCandidatura/{id}")
    public Candidatura updateCandidatura(@PathVariable(value = "id") Long cid,
                                       @Valid @RequestBody Candidatura cData) {
        Candidatura candidatura = cRep.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Candidatura", "id", cid));
        candidatura.setPresentazione(cData.getPresentazione());
        candidatura.setStato(cData.getStato());

        Candidatura updatedCandidatura = cRep.save(candidatura);
        return updatedCandidatura;
    }


    @DeleteMapping("/deleteCandidatura/{id}")
    public ResponseEntity<?> deleteCandidatura(@PathVariable(value = "id") Long cid) {
        Candidatura candidatura = cRep.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("RuoloProgetto", "id", cid));

        cRep.delete(candidatura);

        return ResponseEntity.ok().build();
    }





}
