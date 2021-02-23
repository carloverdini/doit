package it.unicam.doit.controller;


import it.unicam.doit.exception.ResourceNotFoundException;
import it.unicam.doit.model.Progetto;
import it.unicam.doit.model.Utente;
import it.unicam.doit.repository.ProgettoRepository;
import it.unicam.doit.repository.UtenteRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class ProgettoController {

    @Autowired
    ProgettoRepository pRep;

    @Autowired
    UtenteRepository uRep;

    @GetMapping("/getProgetti")
    public List<Progetto> getProgetti(){
        System.out.println("getProgetti");
        return pRep.findAll();
    }
    @GetMapping("/getProgettiAperti")
    public List<Progetto> getProgettiAperti(){
        System.out.println("getProgetti");
        return pRep.findByStato("PUBLIC");
    }

    @GetMapping("/getProgettiProponente/{idProponente}")
    public List<Progetto> getProgettiProponente(@PathVariable long idProponente){
        System.out.println("getProgettiProponente");

        Utente utente = uRep.findById(idProponente);
        return pRep.findByProponenteProgetto(utente);
    }



    @GetMapping("/getProgetto/{id}")
    public Progetto getProgetto(@PathVariable long id) {
        return pRep.findById(id);
    }


    @PostMapping("/addProgetto")
    public String addProgetto(@RequestBody Progetto progetto){
        System.out.println("addProgetto");
        Progetto prj = pRep.findByTitolo(progetto.getTitolo());
        if (prj != null) return "progetto esistente";
        progetto.setStato("DRAFT");
        pRep.save(progetto);
        return "progetto creato correttamente";
    }

    @PutMapping("/updateProgetto/{id}")
    public Progetto updateProgetto(@PathVariable(value = "id") Long cid,
                                       @Valid @RequestBody Progetto progettoData) {
        Progetto progetto = pRep.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Progetto", "id", cid));

        try {
            BeanUtils.copyProperties(progetto,progettoData);
        }catch(Exception e){

        }

        Progetto updatedProgetto = pRep.save(progetto);
        return updatedProgetto;
    }

    @PutMapping("/changeStatusProgetto/{id}")
    public String changeStatusProgetto(@PathVariable long id,
                                @Valid @RequestBody ChangeStatusRequest rq) {
        Progetto progetto = pRep.findById(id);
        if(progetto == null) return "progetto inesistente";

        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!progetto.getProponenteProgetto().getUsername().equals(ud.getUsername()))
            return "utente non autorizzato";
        progetto.setStato(rq.stato);

        Progetto updatedProgetto = pRep.save(progetto);
        return "stato progetto aggiornato correttamente";
    }


    @GetMapping("/closeProgetto/{id}")
    public String closeProgetto(@PathVariable long id) {
        Progetto progetto = pRep.findById(id);
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      if (!progetto.getProponenteProgetto().getUsername().equals(ud.getUsername()))
            return "utente non autorizzato";

        if(progetto != null) progetto.setStato("CLOSED");
        Progetto updatedProgetto = pRep.save(progetto);
        return "progetto aggiornato correttamente";
    }


    @DeleteMapping("/deleteProgetto/{id}")
    public ResponseEntity<?> deleteProgetto(@PathVariable(value = "id") Long cid) {
        Progetto progetto = pRep.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Progetto", "id", cid));

        pRep.delete(progetto);

        return ResponseEntity.ok().build();
    }

    public static class ChangeStatusRequest{
        public String stato;
    }

}
