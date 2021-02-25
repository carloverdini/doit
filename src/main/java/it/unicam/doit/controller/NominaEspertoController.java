package it.unicam.doit.controller;

import it.unicam.doit.controller.helper.AbstractApiController;
import it.unicam.doit.model.NominaEsperto;
import it.unicam.doit.model.Progetto;
import it.unicam.doit.model.RuoloProgetto;
import it.unicam.doit.model.Utente;
import it.unicam.doit.repository.NominaEspertoRepository;
import it.unicam.doit.repository.ProgettoRepository;
import it.unicam.doit.repository.RuoloProgettoRepository;
import it.unicam.doit.repository.UtenteRepository;
import it.unicam.doit.service.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class NominaEspertoController extends AbstractApiController {

    @Autowired
    NominaEspertoRepository nRep;

    @Autowired
    RuoloProgettoRepository rpRep;
    @Autowired
    ProgettoRepository pRep;

    @Autowired
    UtenteRepository uRep;




    @GetMapping("/getNomineEsperto/{idUtente}")
    public ApiResponse getNomineValutatore(@PathVariable long idUtente){
        Utente user = uRep.findById(idUtente);
        return this.getSuccess(nRep.findByEsperto(user));
    }

    @PostMapping("/addNominaEsperto")
    public ApiResponse addNominaEsperto(@RequestBody NominaEsperto ne){
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        RuoloProgetto rp = rpRep.findById((long)ne.getRuoloProgetto().getId());
        Progetto p = pRep.findById((long)rp.getProgetto().getId());
        if(!p.getProponenteProgetto().getId().equals(user.getId())){
            return this.getError("utente non autorizzato");
        }
        NominaEsperto existNe = nRep.findByRuoloProgetto(rp);
        if (existNe != null) return this.getError("nomina esistente");

        NominaEsperto nres = nRep.save(ne);
        return this.getSuccess(nres);
    }

    @DeleteMapping("/deleteNominaEsperto/{id}")
    public ApiResponse deleteNominaEsperto(@PathVariable(value = "id") Long nid) {
        NominaEsperto ne = nRep.findById((long)nid);
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        RuoloProgetto rp = rpRep.findById((long)ne.getRuoloProgetto().getId());
        Progetto p = pRep.findById((long)rp.getProgetto().getId());
        if(!p.getProponenteProgetto().getId().equals(user.getId())){
            return this.getError("utente non autorizzato");
        }

        nRep.delete(ne);
        return this.getSuccess("candidatura eliminata");
    }





}
