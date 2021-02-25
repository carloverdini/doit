package it.unicam.doit.controller;

import it.unicam.doit.controller.helper.AbstractApiController;
import it.unicam.doit.model.RichiestaCandidatura;
import it.unicam.doit.model.Utente;
import it.unicam.doit.repository.RichiestaCandidaturaRepository;
import it.unicam.doit.repository.UtenteRepository;
import it.unicam.doit.service.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class RichiestaCandidaturaController extends AbstractApiController {

    @Autowired
    RichiestaCandidaturaRepository rcRep;

    @Autowired
    UtenteRepository uRep;


    @GetMapping("/getRichiestaCandidatura/{id}")
    public ApiResponse getRichiestaCandidatura(@PathVariable long rcid){
        return this.getSuccess(rcRep.findById(rcid));
    }


    @GetMapping("/getRichiesteCandidaturaRicevute")
    public ApiResponse getRichiestaCandidatura(){
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        List<RichiestaCandidatura> lcnd = rcRep.findByDestinatario(user);
        return this.getSuccess(lcnd);
    }

    @PostMapping("/addRichiestaCandidaturaEsperto")
    public ApiResponse addRichiestaCandidaturaEsperto(@RequestBody RichiestaCandidatura richiestaCandidatura){
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        richiestaCandidatura.setMittente(user);
        richiestaCandidatura.setTipo(RichiestaCandidatura.ESPERTO);
        rcRep.save(richiestaCandidatura);
        return this.getSuccess("richiesta creata correttamente");
    }

    @PostMapping("/addRichiestaCandidaturaProgettista")
    public ApiResponse addRichiestaCandidaturaProgettista(@RequestBody RichiestaCandidatura richiestaCandidatura){
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        richiestaCandidatura.setMittente(user);
        richiestaCandidatura.setTipo(RichiestaCandidatura.CANDIDATO);
        rcRep.save(richiestaCandidatura);
        return this.getSuccess("richiesta creata correttamente");
    }



}
