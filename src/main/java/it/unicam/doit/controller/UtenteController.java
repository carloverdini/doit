package it.unicam.doit.controller;


import it.unicam.doit.exception.ResourceNotFoundException;
import it.unicam.doit.model.Utente;
import it.unicam.doit.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class UtenteController {

    @Autowired
    UtenteRepository uRep;


    @GetMapping("/getUtenti")
    public List<Utente> getUtenti(){
        System.out.println("getUtenti");
        return uRep.findAll();
    }


    @GetMapping("/getUtente/{id}")
    public Utente getUtente(@PathVariable long id) {
        return uRep.findById(id);
    }

    @PostMapping("/addUtente")
    public String addUtente(@RequestBody Utente utente){
        System.out.println("addUtente");
        uRep.save(utente);
        return "utente creato correttamente";
    }

    @PutMapping("/updateUtente/{id}")
    public Utente updateUtente(@PathVariable(value = "id") Long cid,
                                   @Valid @RequestBody Utente utenteData) {
        Utente utente = uRep.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Utente", "id", cid));
        utente.setNome(utenteData.getNome());
        utente.setCognome(utenteData.getCognome());
        Utente updatedUtente = uRep.save(utente);
        return updatedUtente;
    }


    @DeleteMapping("/deleteUtente/{id}")
    public ResponseEntity<?> deleteUtente(@PathVariable(value = "id") Long cid) {
        Utente utente = uRep.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Utente", "id", cid));

        uRep.delete(utente);

        return ResponseEntity.ok().build();
    }




}
