package it.unicam.doit.controller;


import it.unicam.doit.controller.helper.AbstractApiController;
import it.unicam.doit.model.Progetto;
import it.unicam.doit.model.Utente;
import it.unicam.doit.repository.ProgettoRepository;
import it.unicam.doit.repository.UtenteRepository;
import it.unicam.doit.service.ApiResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class ProgettoController extends AbstractApiController {

    @Autowired
    ProgettoRepository pRep;

    @Autowired
    UtenteRepository uRep;

    @GetMapping("/getProgetti")
    public ApiResponse getProgetti(){

        return this.getSuccess(pRep.findByStato(Progetto.OPENED));
    }

    @GetMapping("/getProgettiProponente/{idProponente}")
    public ApiResponse getProgettiProponente(@PathVariable long idProponente){
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        if (idProponente != user.getId()){
            return this.getError("utente non autorizzato");
        }
        Utente utente = uRep.findById(idProponente);
        return this.getSuccess(pRep.findByProponenteProgetto(utente));
    }



    @GetMapping("/getProgetto/{id}")
    public ApiResponse getProgetto(@PathVariable long id) {

        return this.getSuccess(pRep.findById(id));
    }


    @PostMapping("/addProgetto")
    public ApiResponse addProgetto(@RequestBody Progetto progetto){
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        Progetto prj = pRep.findByTitolo(progetto.getTitolo());
        if (prj != null) {
            return this.getError("titolo progetto esistente");
        }
        progetto.setProponenteProgetto(user);
        progetto.setStato(Progetto.DRAFT);
        Progetto pres = pRep.save(progetto);
        return this.getSuccess(pres);
    }

    @PutMapping("/updateProgetto/{id}")
    public ApiResponse updateProgetto(@PathVariable(value = "id") Long cid,
                                       @Valid @RequestBody Progetto progettoData) {
        Progetto prj = pRep.findById((long)cid);
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        if (prj.getProponenteProgetto().getId() != user.getId()){
            return this.getError("utente non autorizzato");
        }
        try {
            BeanUtils.copyProperties(prj,progettoData);
        }catch(Exception e){
            return this.getError(e.getMessage());
        }
        Progetto pres = pRep.save(prj);
        return this.getSuccess(pres);
    }


    @GetMapping("/draftProgetto/{id}")
    public ApiResponse draftProgetto(@PathVariable(value = "id") Long rpid) {
        Progetto prj = pRep.findById((long)rpid);
        if(prj == null)
            return this.getError("progetto inesistente");
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!prj.getProponenteProgetto().getUsername().equals(ud.getUsername()))
            return this.getError("utente non autorizzato");
        prj.setStato(Progetto.DRAFT);
        pRep.save(prj);
        return this.getSuccess("progetto aggiornato");
    }


    @GetMapping("/openProgetto/{id}")
    public ApiResponse openProgetto(@PathVariable(value = "id") Long rpid) {
        Progetto prj = pRep.findById((long)rpid);
        if(prj == null)
            return this.getError("progetto inesistente");
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!prj.getProponenteProgetto().getUsername().equals(ud.getUsername()))
            return this.getError("utente non autorizzato");
        prj.setStato(Progetto.OPENED);
        pRep.save(prj);
        return this.getSuccess("progetto aggiornato");
    }

    @GetMapping("/closeProgetto/{id}")
    public ApiResponse closeProgetto(@PathVariable(value = "id") Long rpid) {
        Progetto prj = pRep.findById((long)rpid);
        if(prj == null)
            return this.getError("progetto inesistente");
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!prj.getProponenteProgetto().getUsername().equals(ud.getUsername()))
            return this.getError("utente non autorizzato");
        prj.setStato(Progetto.CLOSED);
        pRep.save(prj);
        return this.getSuccess("progetto aggionrato");
    }


    @DeleteMapping("/deleteProgetto/{id}")
    public ApiResponse deleteProgetto(@PathVariable(value = "id") Long cid) {
        Progetto prj = pRep.findById((long)cid);
        if(prj == null)
            return this.getError("progetto inesistente");
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!prj.getProponenteProgetto().getUsername().equals(ud.getUsername()))
            return this.getError("utente non autorizzato");
        pRep.delete(prj);

        return this.getSuccess("progetto eliminato");
    }

}
