package it.unicam.doit.controller;


import it.unicam.doit.exception.ResourceNotFoundException;
import it.unicam.doit.model.Progetto;
import it.unicam.doit.model.Utente;
import it.unicam.doit.repository.ProgettoRepository;
import it.unicam.doit.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

/*
    @GetMapping("/getProgetto/{titolo}")
    public Progetto getProgetto(@PathVariable String titolo){
        return pRep.findByTitolo(titolo);
    }
*/

    @PostMapping("/addProgetto")
    public String addProgetto(@RequestBody Progetto progetto){
        System.out.println("addProgetto");
        progetto.setStato("creato");
        pRep.save(progetto);
        return "progetto creato correttamente";
    }

    @PutMapping("/updateProgetto/{id}")
    public Progetto updateProgetto(@PathVariable(value = "id") Long cid,
                                       @Valid @RequestBody Progetto progettoData) {
        Progetto progetto = pRep.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Progetto", "id", cid));
        progetto.setTitolo(progettoData.getTitolo());
        progetto.setDescrizione(progettoData.getDescrizione());
        Progetto updatedProgetto = pRep.save(progetto);
        return updatedProgetto;
    }


    @DeleteMapping("/deleteProgetto/{id}")
    public ResponseEntity<?> deleteProgetto(@PathVariable(value = "id") Long cid) {
        Progetto progetto = pRep.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Progetto", "id", cid));

        pRep.delete(progetto);

        return ResponseEntity.ok().build();
    }





}
