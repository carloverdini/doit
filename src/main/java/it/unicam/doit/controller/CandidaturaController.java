package it.unicam.doit.controller;

import it.unicam.doit.controller.helper.AbstractApiController;
import it.unicam.doit.model.Candidatura;
import it.unicam.doit.model.Progetto;
import it.unicam.doit.model.RuoloProgetto;
import it.unicam.doit.model.Utente;
import it.unicam.doit.repository.CandidaturaRepository;
import it.unicam.doit.repository.ProgettoRepository;
import it.unicam.doit.repository.RuoloProgettoRepository;
import it.unicam.doit.repository.UtenteRepository;
import it.unicam.doit.service.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class CandidaturaController extends AbstractApiController {

    @Autowired
    CandidaturaRepository cRep;

    @Autowired
    ProgettoRepository pRep;
    @Autowired
    RuoloProgettoRepository rpRep;

    @Autowired
    UtenteRepository uRep;

    @GetMapping("/getCandidatureUtente/{idUtente}")
    public ApiResponse getCandidatureUtente(@PathVariable long idUtente){
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        if (idUtente != user.getId()){
            return this.getError("utente non autorizzato");
        }
        Utente utente = uRep.findById(idUtente);
        return this.getSuccess(cRep.findByCandidato(utente));
    }

    @GetMapping("/getCandidatureRuoloProgetto/{idRuoloProgetto}")
    public ApiResponse getCandidatureRuoloProgetto(@PathVariable long idRuoloProgetto){
        RuoloProgetto  rpj = rpRep.findById(idRuoloProgetto);
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        Progetto prj = pRep.findById((long)rpj.getProgetto().getId());
        if (prj.getProponenteProgetto().getId() != user.getId()){
            return this.getError("utente non autorizzato");
        }
        return this.getSuccess(cRep.findByRuoloProgetto(rpj));
    }

    @PostMapping("/addCandidatura")
    public ApiResponse addCandidatura(@RequestBody Candidatura candidatura){
        //associo utente a candidatura
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        candidatura.setCandidato(user);
        //procedo con la candidatura e la imposto come attiva
        candidatura.setStato(Candidatura.ACTVD);
        Candidatura cres = cRep.save(candidatura);
        return this.getSuccess(cres);
    }


    @PutMapping("/updateCandidatura/{id}")
    public ApiResponse updateCandidatura(@PathVariable(value = "id") Long cid,
                                       @Valid @RequestBody Candidatura cData) {
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        Candidatura cnd = cRep.findById((long)cid);
        if (cnd.getCandidato().getId() != user.getId()){
            return this.getError("utente non autorizzato");
        }
        cnd.setPresentazione(cData.getPresentazione());
        cnd.setStato(cData.getStato());
        Candidatura cres = cRep.save(cnd);
        return this.getSuccess(cres);
    }

    @DeleteMapping("/deleteCandidatura/{id}")
    public ApiResponse deleteCandidatura(@PathVariable(value = "id") Long cid) {
        Candidatura cnd = cRep.findById((long)cid);
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        if (cnd.getCandidato().getId() != user.getId()){
            return this.getError("utente non autorizzato");
        }

        cRep.delete(cnd);

        return this.getSuccess("candidatura eliminata");
    }





}
