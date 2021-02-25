package it.unicam.doit.controller;


import it.unicam.doit.controller.helper.AbstractApiController;
import it.unicam.doit.model.Progetto;
import it.unicam.doit.model.RuoloProgetto;
import it.unicam.doit.model.Utente;
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
public class RuoloProgettoController extends AbstractApiController {

    @Autowired
    UtenteRepository uRep;


    @Autowired
    RuoloProgettoRepository rpRep;

    @Autowired
    ProgettoRepository pRep;

    @GetMapping("/getRuoliProgetto/{idProgetto}")
    public ApiResponse getRuoliProgetto(@PathVariable long idProgetto){
        Progetto progetto = pRep.findById(idProgetto);
        return this.getSuccess(rpRep.findByProgetto(progetto));
    }

    @GetMapping("/getRuoloProgetto/{id}")
    public ApiResponse getRuoloProgetto(@PathVariable long id) {
        return this.getSuccess(rpRep.findById(id));
    }

    @PostMapping("/addRuoloProgetto")
    public ApiResponse addRuoloProgetto(@RequestBody RuoloProgetto rpj){
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        Progetto prj = pRep.findById((long)rpj.getProgetto().getId());
        if (prj.getProponenteProgetto().getId() != user.getId()){
            return this.getError("utente non autorizzato");
        }
        rpj.setStato(RuoloProgetto.DRAFT);
        RuoloProgetto res = rpRep.save(rpj);
        return this.getSuccess(res);//"ruolo progetto creato correttamente";
    }


    @GetMapping("/draftRuoloProgetto/{id}")
    public ApiResponse draftRuoloProgetto(@PathVariable(value = "id") Long rpid) {
        RuoloProgetto rpj = rpRep.findById((long)rpid);
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        Progetto prj = pRep.findById((long)rpj.getProgetto().getId());
        if (prj.getProponenteProgetto().getId() != user.getId()){
            return this.getError("utente non autorizzato");
        }
        rpj.setStato(RuoloProgetto.DRAFT);
        RuoloProgetto res = rpRep.save(rpj);
        return this.getSuccess(res);
    }


    @GetMapping("/openRuoloProgetto/{id}")
    public ApiResponse openRuoloProgetto(@PathVariable(value = "id") Long rpid) {
        RuoloProgetto rpj = rpRep.findById((long)rpid);
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        Progetto prj = pRep.findById((long)rpj.getProgetto().getId());
        if (prj.getProponenteProgetto().getId() != user.getId()){
            return this.getError("utente non autorizzato");
        }
        rpj.setStato(RuoloProgetto.OPENED);
        rpRep.save(rpj);
        return this.getSuccess("ruolo progetto pubblicato");
    }

    @GetMapping("/closeRuoloProgetto/{id}")
    public ApiResponse closeRuoloProgetto(@PathVariable(value = "id") Long rpid) {
        RuoloProgetto rpj = rpRep.findById((long)rpid);
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        Progetto prj = pRep.findById((long)rpj.getProgetto().getId());
        if (prj.getProponenteProgetto().getId() != user.getId()){
            return this.getError("utente non autorizzato");
        }
        rpj.setStato(RuoloProgetto.CLOSED);
        rpRep.save(rpj);
        return this.getSuccess("ruolo progetto chiuso");
    }

    @PutMapping("/updateRuoloProgetto/{id}")
    public ApiResponse updateRuoloProgetto(@PathVariable(value = "id") Long rpid,
                                       @Valid @RequestBody RuoloProgetto rpdata) {
        RuoloProgetto rpj = rpRep.findById((long)rpid);
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        Progetto prj = pRep.findById((long)rpj.getProgetto().getId());
        if (prj.getProponenteProgetto().getId() != user.getId()){
            return this.getError("utente non autorizzato");
        }
        rpj.setTitolo(rpdata.getTitolo());
        rpj.setDescrizione(rpdata.getDescrizione());

        rpRep.save(rpj);
        return this.getSuccess("ruolo progetto modificato");
    }


    @DeleteMapping("/deleteRuoloProgetto/{id}")
    public ApiResponse deleteRuoloProgetto(@PathVariable(value = "id") Long rpid) {
        RuoloProgetto rpj = rpRep.findById((long)rpid);
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        Progetto prj = pRep.findById((long)rpj.getProgetto().getId());
        if (prj.getProponenteProgetto().getId() != user.getId()){
            return this.getError("utente non autorizzato");
        }
        rpRep.delete(rpj);
        return this.getSuccess("ruolo progetto eliminato");
    }





}
