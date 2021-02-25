package it.unicam.doit.controller;

import it.unicam.doit.controller.helper.AbstractApiController;
import it.unicam.doit.exception.ResourceNotFoundException;
import it.unicam.doit.model.Utente;
import it.unicam.doit.repository.UtenteRepository;
import it.unicam.doit.service.ApiResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class UtenteController extends AbstractApiController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UtenteRepository uRep;


    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/getUtenti")
    public ApiResponse getUtenti(){
        return this.getSuccess(uRep.findAll());
    }


    @GetMapping("/getUtente/{id}")
    public ApiResponse getUtente(@PathVariable long id) {
        return this.getSuccess(uRep.findById(id));
    }

/*** public per permettere la registrazione ***/
    @PostMapping("addUtente")
    public ApiResponse addUtente(@RequestBody Utente utente){
        Utente usr = uRep.findByUsername(utente.getUsername());
        if (usr != null ) return this.getError("utente esistente");
        utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        Utente res = uRep.save(utente);
        return this.getSuccess(res);
    }

    @PutMapping("/updateUtente/{id}")
    public ApiResponse updateUtente(@PathVariable(value = "id") Long cid,
                                   @Valid @RequestBody Utente utenteData) {
        Utente utente = uRep.findById((long)cid);
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        if (utente.getId() != user.getId()){
            return this.getError("utente non autorizzato");
        }
        String oldPwd = utente.getPassword();
        Boolean oldState = utente.getEnabled();
        try {
            utenteData.setId(cid);
            BeanUtils.copyProperties(utente,utenteData);

            utente.setPassword(oldPwd);
            utente.setEnabled(oldState);

            Utente ures = uRep.save(utente);
            return this.getSuccess(ures);
        }catch(Exception e){
            return this.getError(e.getMessage());
        }
    }

    @PutMapping("/updatePasswordUtente/{id}")
    public ApiResponse updatePasswordUtente(@PathVariable(value = "id") Long cid,
                               @Valid @RequestBody ChangePasswordRequest pwd) {
        Utente utente = uRep.findById((long)cid);
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        if (utente.getId() != user.getId()){
            return this.getError("utente non autorizzato");
        }
        // Effettuo l autenticazione
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        utente.getUsername(),
                        pwd.oldPassword
                )
        );
        if(!authentication.isAuthenticated()) {
            return this.getError("password corrente errata");
        }
        try {
            utente.setPassword(
                    passwordEncoder.encode(pwd.newPassword)
                    );
            uRep.save(utente);
        }catch(Exception e){
            return this.getError(e.getMessage());
        }
        return this.getSuccess("password aggiornata correttamente");
    }



    @DeleteMapping("/deleteUtente/{id}")
    public ApiResponse deleteUtente(@PathVariable(value = "id") Long cid) {
        Utente utente = uRep.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Utente", "id", cid));
        uRep.delete(utente);

        return this.getSuccess("utente eliminato");
    }

public static class ChangePasswordRequest{
        public String oldPassword;
        public String newPassword;
}


}
