package it.unicam.doit.controller;

import it.unicam.doit.exception.ResourceNotFoundException;
import it.unicam.doit.model.Utente;
import it.unicam.doit.repository.UtenteRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class UtenteController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UtenteRepository uRep;


    @Autowired
    private AuthenticationManager authenticationManager;


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

        System.out.println(utente.getUsername());

        Utente usr = uRep.findByUsername(utente.getUsername());
        if (usr != null )
            return "utente esistente";

        utente.setPassword(
                passwordEncoder.encode(utente.getPassword())
        );
        uRep.save(utente);
        return "utente creato con id: "+utente.getId();
    }

    @PutMapping("/updateUtente/{id}")
    public String updateUtente(@PathVariable(value = "id") Long cid,
                                   @Valid @RequestBody Utente utenteData) {
        Utente utente = uRep.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Utente", "id", cid));

        String oldPwd = utente.getPassword();
        Boolean oldState = utente.getEnabled();
        try {
            utenteData.setId(cid);
            BeanUtils.copyProperties(utente,utenteData);

            utente.setPassword(oldPwd);
            utente.setEnabled(oldState);

            uRep.save(utente);
        }catch(Exception e){
            return "errore durante il salvataggio "+ e.getMessage();
        }
        return "utente aggiornato correttamente";
    }

    @PutMapping("/updatePasswordUtente/{id}")
    public String updatePasswordUtente(@PathVariable(value = "id") Long cid,
                               @Valid @RequestBody ChangePasswordRequest pwd) {
        Utente utente = uRep.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Utente", "id", cid));



        // Effettuo l autenticazione
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        utente.getUsername(),
                        pwd.oldPassword
                )
        );

        if(!authentication.isAuthenticated()) return "password corrente errata";
        try {
            utente.setPassword(
                    passwordEncoder.encode(pwd.newPassword)
                    );
            uRep.save(utente);
        }catch(Exception e){
            return "errore durante il salvataggio "+ e.getMessage();
        }
        return "password aggiornata correttamente";
    }



    @DeleteMapping("/deleteUtente/{id}")
    public ResponseEntity<?> deleteUtente(@PathVariable(value = "id") Long cid) {
        Utente utente = uRep.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Utente", "id", cid));

        uRep.delete(utente);

        return ResponseEntity.ok().build();
    }

public static class ChangePasswordRequest{
        public String oldPassword;
        public String newPassword;
}


}
