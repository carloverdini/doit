package it.unicam.doit.controller;

import it.unicam.doit.exception.ResourceNotFoundException;
import it.unicam.doit.model.Candidatura;
import it.unicam.doit.model.Utente;
import it.unicam.doit.model.Valutazione;
import it.unicam.doit.repository.CandidaturaRepository;
import it.unicam.doit.repository.UtenteRepository;
import it.unicam.doit.repository.ValutazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class ValutazioneController {

    @Autowired
    ValutazioneRepository vRep;
    @Autowired
    UtenteRepository uRep;
    @Autowired
    CandidaturaRepository cRep;


    @GetMapping("/getValutazione/{id}")
    public Valutazione getValutazione(@PathVariable long vid){
        System.out.println("getValutazione");
        return vRep.findById(vid)
                .orElseThrow(() -> new ResourceNotFoundException("Candidatura", "id", vid));
    }

    @PostMapping("/addValutazione")
    public String addValutazione(@RequestBody Valutazione vData){
        //associo utente a valutazione
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        vData.setValutatore(user);
        vRep.save(vData);
        Candidatura cnd = vData.getCandidatura();
        if (vData.getEsito()){
            cnd.setStato("CFRMD");
        }else{
            cnd.setStato("RJCTD");
        }
        cRep.save(cnd);
        return "valutazione creata correttamente";
    }

    @PutMapping("/updateValutazione/{id}")
    public String updateValutazione(@PathVariable(value = "id") Long vid,
                                       @Valid @RequestBody Valutazione vData) {
        Valutazione valutazione = vRep.findById(vid)
                .orElseThrow(() -> new ResourceNotFoundException("Valutazione", "id", vid));
        valutazione.setEsito(vData.getEsito());
        valutazione.setNote(vData.getNote());
        vRep.save(valutazione);
        Candidatura cnd = vData.getCandidatura();
        if (vData.getEsito()){
            cnd.setStato("CFRMD");
        }else{
            cnd.setStato("RJCTD");
        }
        cRep.save(cnd);

        return "valutazione aggiornata correttamente";
    }


    @DeleteMapping("/deleteValutazione/{id}")
    public ResponseEntity<?> deleteValutazione(@PathVariable(value = "id") Long vid) {
        Valutazione valutazione = vRep.findById(vid)
                .orElseThrow(() -> new ResourceNotFoundException("RuoloProgetto", "id", vid));

        vRep.delete(valutazione);

        return ResponseEntity.ok().build();
    }





}
