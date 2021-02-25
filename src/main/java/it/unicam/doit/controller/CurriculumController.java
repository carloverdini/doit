package it.unicam.doit.controller;

import it.unicam.doit.controller.helper.AbstractApiController;
import it.unicam.doit.exception.ResourceNotFoundException;
import it.unicam.doit.model.Curriculum;
import it.unicam.doit.model.Utente;
import it.unicam.doit.repository.CurriculumRepository;
import it.unicam.doit.repository.UtenteRepository;
import it.unicam.doit.service.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class CurriculumController extends AbstractApiController {

    @Autowired
    CurriculumRepository cRep;

    @Autowired
    UtenteRepository uRep;

    @GetMapping("/getCurriculum")
    public ApiResponse getCurriculum(){
        System.out.println("getCurriculum");
        return this.getSuccess(cRep.findAll());
    }

    @GetMapping("/getCurriculumUtente/{idUtente}")
    public ApiResponse getCurriculumUtente(@PathVariable long idUtente){
        System.out.println("getCurriculumUtente");
        Utente utente = uRep.findById(idUtente);
        return this.getSuccess(cRep.findByProprietarioCurriculum(utente));
    }

    @GetMapping("/getCurriculum/{id}")
    public ApiResponse getCurriculum(@PathVariable long id) {
        return this.getSuccess(cRep.findById(id));
    }


    @PostMapping("/addCurriculum")
    public ApiResponse addCurriculum(@RequestBody Curriculum curriculum){
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        curriculum.setProprietarioCurriculum(user);
        Curriculum cres = cRep.save(curriculum);
        return this.getSuccess(cres);
    }

    @PutMapping("/updateCurriculum/{id}")
    public ApiResponse updateCurriculum(@PathVariable(value = "id") Long cid,
                                   @Valid @RequestBody Curriculum curriculumData) {
        Curriculum cur = cRep.findById((long)cid);
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        if (cur.getProprietarioCurriculum().getId() != user.getId()){
            return this.getError("utente non autorizzato");
        }
        cur.setTitolo(curriculumData.getTitolo());
        cur.setDescrizione(curriculumData.getDescrizione());
        Curriculum cres = cRep.save(cur);
        return this.getSuccess(cres);
    }


    @DeleteMapping("/deleteCurriculum/{id}")
    public ApiResponse deleteCurriculum(@PathVariable(value = "id") Long cid) {
        Curriculum cur = cRep.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Curriculum", "id", cid));
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente user = uRep.findByUsername(ud.getUsername());
        if (cur.getProprietarioCurriculum().getId() != user.getId()){
            return this.getError("utente non autorizzato");
        }

        cRep.delete(cur);

        return this.getSuccess("curriculum eliminato");
    }




}
