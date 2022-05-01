package com.company.controllers;

import com.company.exception.ResourceNotFound;
import com.company.models.Saler;
import com.company.repository.SalerRepository;
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
public class SalerController {
    @Autowired
    private SalerRepository repository;

    @GetMapping("/sellers")
    public List<Saler> getSellers() {
        List<Saler> list = repository.findAll();
        return list;
    }

    @GetMapping("/sellers/{id}")
    public ResponseEntity<Object> getSaler(@PathVariable(value = "id") Integer id) throws ResourceNotFound {
        Optional<Saler> country = repository.findById(id);
        if (!country.isPresent()) throw new ResourceNotFound("Saler not found for id: " + id);
        else return new ResponseEntity<>(country.get(), HttpStatus.OK);
    }

    @PostMapping("/sellers")
    public Saler saveSaler(@RequestBody Saler Saler) {
        return repository.save(Saler);
    }


    @DeleteMapping("/sellers/{id}")
    public ResponseEntity<Object> deleteSaler(@PathVariable(value = "id") Integer id) throws ResourceNotFound {
        Optional<Saler> Saler = repository.findById(id);
        if (!Saler.isPresent()) throw new ResourceNotFound("Saler not found for id: " + id);
        else repository.delete(Saler.get());

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/sellers/{id}")
    public ResponseEntity<Object> putOfSaler(@PathVariable(value = "id") Integer id, @RequestBody Saler SalerChanged) throws ResourceNotFound {
        Optional<Saler> Saler = repository.findById(id);
        if (!Saler.isPresent()) throw new ResourceNotFound("Saler not found for id: " + id);
        else {
            SalerChanged.setId(Saler.get().getId());
            repository.save(SalerChanged);
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("changed", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
