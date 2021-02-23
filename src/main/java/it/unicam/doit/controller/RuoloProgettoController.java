package it.unicam.doit.controller;


import it.unicam.doit.exception.ResourceNotFoundException;
import it.unicam.doit.model.Progetto;
import it.unicam.doit.model.RuoloProgetto;
import it.unicam.doit.repository.ProgettoRepository;
import it.unicam.doit.repository.RuoloProgettoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class RuoloProgettoController {

    @Autowired
    RuoloProgettoRepository rpRep;

    @Autowired
    ProgettoRepository pRep;

    @GetMapping("/getRuoliProgetto")
    public List<RuoloProgetto> getRuoliProgetto(){
        System.out.println("getRuoliProgetto");
        return rpRep.findAll();
    }

    @GetMapping("/getRuoliProgetto/{idProgetto}")
    public List<RuoloProgetto> getRuoliProgetto(@PathVariable long idProgetto){
        System.out.println("getRuoliProgetto");

        Progetto progetto = pRep.findById(idProgetto);
        return rpRep.findByProgetto(progetto);
    }



    @GetMapping("/getRuoloProgetto/{id}")
    public RuoloProgetto getRuoloProgetto(@PathVariable long id) {
        return rpRep.findById(id);
    }

/*
    @GetMapping("/getProgetto/{titolo}")
    public Progetto getProgetto(@PathVariable String titolo){
        return pRep.findByTitolo(titolo);
    }
*/

    @PostMapping("/addRuoloProgetto")
    public String addRuoloProgetto(@RequestBody RuoloProgetto ruoloProgetto){
        System.out.println("addRuoloProgetto");
        ruoloProgetto.setStato("creato");
        rpRep.save(ruoloProgetto);
        return "ruolo progetto creato correttamente";
    }

    @PutMapping("/updateRuoloProgetto/{id}")
    public RuoloProgetto updateRuoloProgetto(@PathVariable(value = "id") Long rpid,
                                       @Valid @RequestBody RuoloProgetto ruoloProgettoData) {
        RuoloProgetto ruoloProgetto = rpRep.findById(rpid)
                .orElseThrow(() -> new ResourceNotFoundException("RuoloProgetto", "id", rpid));
        ruoloProgetto.setTitolo(ruoloProgettoData.getTitolo());
        ruoloProgetto.setDescrizione(ruoloProgettoData.getDescrizione());
        RuoloProgetto updatedRP = rpRep.save(ruoloProgetto);
        return updatedRP;
    }


    @DeleteMapping("/deleteRuoloProgetto/{id}")
    public ResponseEntity<?> deleteRuoloProgetto(@PathVariable(value = "id") Long rpid) {
        RuoloProgetto rp = rpRep.findById(rpid)
                .orElseThrow(() -> new ResourceNotFoundException("RuoloProgetto", "id", rpid));

        rpRep.delete(rp);

        return ResponseEntity.ok().build();
    }





}
