package com.company.controllers;

import com.company.exception.ResourceNotFound;
import com.company.models.Manufacturer;
import com.company.repository.ManufacturerRepository;
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
public class ManufacturerContoller {
    @Autowired
    private ManufacturerRepository repository;

    @GetMapping("/Manufacturers")
    public List<Manufacturer> getManufacturers() {
        List<Manufacturer> list = repository.findAll();
        return list;
    }

    @GetMapping("/Manufacturers/{id}")
    public ResponseEntity<Object> getManufacturer(@PathVariable(value = "id") Integer id) throws ResourceNotFound {
        Optional<Manufacturer> country = repository.findById(id);
        if (!country.isPresent()) throw new ResourceNotFound("Manufacturer not found for id: " + id);
        else return new ResponseEntity<>(country.get(), HttpStatus.OK);
    }

    @PostMapping("/Manufacturers")
    public Manufacturer saveManufacturer(@RequestBody Manufacturer Manufacturer) {
        return repository.save(Manufacturer);
    }


    @DeleteMapping("/Manufacturers/{id}")
    public ResponseEntity<Object> deleteManufacturer(@PathVariable(value = "id") Integer id) throws ResourceNotFound {
        Optional<Manufacturer> Manufacturer = repository.findById(id);
        if (!Manufacturer.isPresent()) throw new ResourceNotFound("Manufacturer not found for id: " + id);
        else repository.delete(Manufacturer.get());

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/Manufacturers/{id}")
    public ResponseEntity<Object> putOfManufacturer(@PathVariable(value = "id") Integer id, @RequestBody Manufacturer ManufacturerChanged) throws ResourceNotFound {
        Optional<Manufacturer> Manufacturer = repository.findById(id);
        if (!Manufacturer.isPresent()) throw new ResourceNotFound("Manufacturer not found for id: " + id);
        else {
            ManufacturerChanged.setId(Manufacturer.get().getId());
            repository.save(ManufacturerChanged);
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("changed", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
