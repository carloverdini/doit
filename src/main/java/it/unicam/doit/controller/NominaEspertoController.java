package it.unicam.doit.controller;

import it.unicam.doit.exception.ResourceNotFoundException;
import it.unicam.doit.model.NominaEsperto;
import it.unicam.doit.model.Progetto;
import it.unicam.doit.model.RuoloProgetto;
import it.unicam.doit.model.Utente;
import it.unicam.doit.repository.NominaEspertoRepository;
import it.unicam.doit.repository.ProgettoRepository;
import it.unicam.doit.repository.RuoloProgettoRepository;
import it.unicam.doit.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class NominaEspertoController {

    @Autowired
    NominaEspertoRepository nRep;

    @Autowired
    RuoloProgettoRepository rpRep;
    @Autowired
    ProgettoRepository pRep;

    @Autowired
    UtenteRepository uRep;




    @GetMapping("/getNomineValutatore/{idUtente}")
    public List<NominaEsperto> getNomineValutatore(@PathVariable long idUtente){

        Utente user = uRep.findById(idUtente);
        return nRep.findByEsperto(user);
    }

    @PostMapping("/addNominaEsperto")
    public String addNominaEsperto(@RequestBody NominaEsperto ne){
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        long rpid = ne.getRuoloProgetto().getId();
        RuoloProgetto rp = rpRep.findById(rpid);
        long pid = rp.getProgetto().getId();
        Progetto p = pRep.findById(pid);
        if(!p.getProponenteProgetto().equals(user.getId()) ){
            return "utente non autorizzato alla nomina";
        }
        nRep.save(ne);
        return "nomina creata correttamente";
    }

    @DeleteMapping("/deleteNominaEsperto/{id}")
    public ResponseEntity<?> deleteNominaEsperto(@PathVariable(value = "id") Long nid) {
        NominaEsperto ne = nRep.findById(nid)
                .orElseThrow(() -> new ResourceNotFoundException("nominaEsperto", "id", nid));

        nRep.delete(ne);

        return ResponseEntity.ok().build();
    }





}
