package it.unicam.doit.controller;

import it.unicam.doit.exception.ResourceNotFoundException;
import it.unicam.doit.model.Curriculum;
import it.unicam.doit.model.Utente;
import it.unicam.doit.repository.CurriculumRepository;
import it.unicam.doit.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class CurriculumController {

    @Autowired
    CurriculumRepository cRep;

    @Autowired
    UtenteRepository uRep;

    @GetMapping("/getCurriculum")
    public List<Curriculum> getCurriculum(){
        System.out.println("getCurriculum");
        return cRep.findAll();
    }

    @GetMapping("/getCurriculumUtente/{idUtente}")
    public List<Curriculum> getCurriculumUtente(@PathVariable long idUtente){
        System.out.println("getCurriculumUtente");
        Utente utente = uRep.findById(idUtente);
        return cRep.findByProprietarioCurriculum(utente);
    }

    @GetMapping("/getCurriculum/{id}")
    public Curriculum getCurriculum(@PathVariable long id) {
        return cRep.findById(id);
    }


/*
    @GetMapping("/getCurriculum/{titolo}")
    public Curriculum getCurriculum(@PathVariable String titolo){
        return pRep.findByTitolo(titolo);
    }
*/

    @PostMapping("/addCurriculum")
    public String addCurriculum(@RequestBody Curriculum curriculum){
        System.out.println("addCurriculum");
        cRep.save(curriculum);
        return "curriculum creato correttamente";
    }

    @PutMapping("/updateCurriculum/{id}")
    public Curriculum updateCurriculum(@PathVariable(value = "id") Long cid,
                                   @Valid @RequestBody Curriculum curriculumData) {
        Curriculum curriculum = cRep.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Curriculum", "id", cid));
        curriculum.setTitolo(curriculumData.getTitolo());
        curriculum.setDescrizione(curriculumData.getDescrizione());
        Curriculum updatedCurriculum = cRep.save(curriculum);
        return updatedCurriculum;
    }


    @DeleteMapping("/deleteCurriculum/{id}")
    public ResponseEntity<?> deleteCurriculum(@PathVariable(value = "id") Long cid) {
        Curriculum curriculum = cRep.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Curriculum", "id", cid));

        cRep.delete(curriculum);

        return ResponseEntity.ok().build();
    }




}
