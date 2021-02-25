package it.unicam.doit.controller;

import it.unicam.doit.controller.helper.AbstractApiController;
import it.unicam.doit.model.RichiestaCandidatura;
import it.unicam.doit.repository.RichiestaCandidaturaRepository;
import it.unicam.doit.repository.UtenteRepository;
import it.unicam.doit.service.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class RichiestaCandidaturaController extends AbstractApiController {

    @Autowired
    RichiestaCandidaturaRepository rcRep;

    @Autowired
    UtenteRepository uRep;


    @GetMapping("/getRichiestaCandidatura/{id}")
    public ApiResponse getRichiestaCandidatura(@PathVariable long rcid){
        System.out.println("getRichiestaCandidatura");
        return this.getSuccess(rcRep.findById(rcid));
    }

    @PostMapping("/addRichiestaCandidatura")
    public ApiResponse addRichiestaCandidatura(@RequestBody RichiestaCandidatura richiestaCandidatura){
        System.out.println("addRichiestaCandidatura");
        rcRep.save(richiestaCandidatura);
        return this.getSuccess("valutazione creata correttamente");
    }


}
