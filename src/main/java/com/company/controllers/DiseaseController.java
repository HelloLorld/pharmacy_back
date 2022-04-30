package com.company.controllers;

import com.company.exception.ResourceNotFound;
import com.company.models.Disease;
import com.company.repository.DiseaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class DiseaseController {
    @Autowired
    private DiseaseRepository repository;

    @GetMapping("/Diseases")
    public List<Disease> getDiseases() {
        List<Disease> list = repository.findAll();
        return list;
    }

    @GetMapping("/Diseases/{id}")
    public ResponseEntity<Object> getDisease(@PathVariable(value = "id") Integer id) throws ResourceNotFound {
        Optional<Disease> country = repository.findById(id);
        if (!country.isPresent()) throw new ResourceNotFound("Disease not found for id: " + id);
        else return new ResponseEntity<>(country.get(), HttpStatus.OK);
    }

    @PostMapping("/Diseases")
    public Disease saveDisease(@RequestBody Disease Disease) {
        return repository.save(Disease);
    }


    @DeleteMapping("/Diseases/{id}")
    public ResponseEntity<Object> deleteDisease(@PathVariable(value = "id") Integer id) throws ResourceNotFound {
        Optional<Disease> Disease = repository.findById(id);
        if (!Disease.isPresent()) throw new ResourceNotFound("Disease not found for id: " + id);
        else repository.delete(Disease.get());

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/Diseases/{id}")
    public ResponseEntity<Object> putOfDisease(@PathVariable(value = "id") Integer id, @RequestBody Disease DiseaseChanged) throws ResourceNotFound {
        Optional<Disease> Disease = repository.findById(id);
        if (!Disease.isPresent()) throw new ResourceNotFound("Disease not found for id: " + id);
        else {
            DiseaseChanged.setId(Disease.get().getId());
            repository.save(DiseaseChanged);
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("changed", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
