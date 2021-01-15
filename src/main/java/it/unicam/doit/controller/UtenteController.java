package it.unicam.doit.controller;

import it.unicam.doit.exception.ResourceNotFoundException;
import it.unicam.doit.model.Utente;
import it.unicam.doit.repository.ProgettoRepository;
import it.unicam.doit.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api")
public class UtenteController {

    @Autowired
    UtenteRepository utenteRepository;

    @Autowired
    ProgettoRepository progettoRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/utente/list")
    public List<Utente> getAllUtente() {
        return utenteRepository.findAll();
    }

    @PostMapping("/utente")
    public Utente createUtente(@Valid @RequestBody Utente utente) {
        utente.setPassword(
                passwordEncoder.encode(utente.getPassword())
        );

        return utenteRepository.save(utente);
    }

    @GetMapping("/utente/{id}")
    public Utente getUtenteById(@PathVariable(value = "id") Long utenteId) {
        return utenteRepository.findById(utenteId)
                .orElseThrow(() -> new ResourceNotFoundException("Utente", "id", utenteId));
    }

    @PutMapping("/utente/{id}")
    public Utente updateUtente(@PathVariable(value = "id") Long utenteId,
                               @Valid @RequestBody Utente utenteDetails) {

        Utente utente = utenteRepository.findById(utenteId)
                .orElseThrow(() -> new ResourceNotFoundException("Utente", "id", utenteId));

        utente.setNome(utenteDetails.getNome());
        utente.setCognome(utenteDetails.getCognome());
        utente.setEmail(utenteDetails.getEmail());
        utente.setPassword(
                passwordEncoder.encode(utente.getPassword())
        );

        Utente updatedUtente = utenteRepository.save(utente);
        return updatedUtente;
    }

    @DeleteMapping("/utente/{id}")
    public ResponseEntity<?> deleteUtente(@PathVariable(value = "id") Long utenteId) {
        Utente utente = utenteRepository.findById(utenteId)
                .orElseThrow(() -> new ResourceNotFoundException("Utente", "id", utenteId));

        utenteRepository.delete(utente);

        return ResponseEntity.ok().build();
    }
}
