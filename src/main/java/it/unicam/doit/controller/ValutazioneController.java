package it.unicam.doit.controller;

import it.unicam.doit.controller.helper.AbstractApiController;
import it.unicam.doit.exception.ResourceNotFoundException;
import it.unicam.doit.model.*;
import it.unicam.doit.repository.*;
import it.unicam.doit.service.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class ValutazioneController extends AbstractApiController {

    @Autowired
    ValutazioneRepository vRep;
    @Autowired
    UtenteRepository uRep;
    @Autowired
    CandidaturaRepository cRep;
    @Autowired
    RuoloProgettoRepository rpRep;
    @Autowired
    ProgettoRepository pRep;
    @Autowired
    NominaEspertoRepository neRep;

    @GetMapping("/getValutazione/{id}")
    public ApiResponse getValutazione(@PathVariable long vid){
        return this.getSuccess(vRep.findById(vid));
    }

    @PostMapping("/addValutazione")
    public ApiResponse addValutazione(@RequestBody Valutazione vData){
        // verificare se proponente o esperto
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        Candidatura cnd = cRep.findById((long)vData.getCandidatura().getId());
        RuoloProgetto rpj = rpRep.findById((long)cnd.getRuoloProgetto().getId());
        Progetto pj = pRep.findById((long)rpj.getProgetto().getId());
        NominaEsperto nme = neRep.findByRuoloProgetto(rpj);
        if(!user.getId().equals(pj.getProponenteProgetto().getId()) && !user.getId().equals(nme.getEsperto().getId())){
            return this.getError("utente non autorizzato");
        }
        vData.setValutatore(user);
        vRep.save(vData);

        //imposto di conseguenza lo stato della candidatura
        if (vData.getEsito()){
            cnd.setStato("CFRMD");
        }else{
            cnd.setStato("RJCTD");
        }
        cRep.save(cnd);

        return this.getSuccess("valutazione creata correttamente");
    }

    @PutMapping("/updateValutazione/{id}")
    public ApiResponse updateValutazione(@PathVariable(value = "id") Long vid,
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

        return this.getSuccess("valutazione aggiornata correttamente");
    }


    @DeleteMapping("/deleteValutazione/{id}")
    public ApiResponse deleteValutazione(@PathVariable(value = "id") Long vid) {
        Valutazione valutazione = vRep.findById(vid)
                .orElseThrow(() -> new ResourceNotFoundException("RuoloProgetto", "id", vid));

        vRep.delete(valutazione);

        return this.getSuccess("valutazione eliminata");
    }





}
