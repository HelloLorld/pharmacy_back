package com.company.controllers;

import com.company.exception.ResourceNotFound;
import com.company.models.Packaging;
import com.company.repository.PackagingRepository;
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
public class PackagingController {
    @Autowired
    private PackagingRepository repository;

    @GetMapping("/Packagings")
    public List<Packaging> getPackagings() {
        List<Packaging> list = repository.findAll();
        return list;
    }

    @GetMapping("/Packagings/{id}")
    public ResponseEntity<Object> getPackaging(@PathVariable(value = "id") Integer id) throws ResourceNotFound {
        Optional<Packaging> country = repository.findById(id);
        if (!country.isPresent()) throw new ResourceNotFound("Packaging not found for id: " + id);
        else return new ResponseEntity<>(country.get(), HttpStatus.OK);
    }

    @PostMapping("/Packagings")
    public Packaging savePackaging(@RequestBody Packaging Packaging) {
        return repository.save(Packaging);
    }


    @DeleteMapping("/Packagings/{id}")
    public ResponseEntity<Object> deletePackaging(@PathVariable(value = "id") Integer id) throws ResourceNotFound {
        Optional<Packaging> Packaging = repository.findById(id);
        if (!Packaging.isPresent()) throw new ResourceNotFound("Packaging not found for id: " + id);
        else repository.delete(Packaging.get());

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/Packagings/{id}")
    public ResponseEntity<Object> putOfPackaging(@PathVariable(value = "id") Integer id, @RequestBody Packaging PackagingChanged) throws ResourceNotFound {
        Optional<Packaging> Packaging = repository.findById(id);
        if (!Packaging.isPresent()) throw new ResourceNotFound("Packaging not found for id: " + id);
        else {
            PackagingChanged.setId(Packaging.get().getId());
            repository.save(PackagingChanged);
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("changed", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
