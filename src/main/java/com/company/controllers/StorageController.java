package com.company.controllers;

import com.company.exception.ResourceNotFound;
import com.company.models.StorageLocation;
import com.company.repository.StorageRepository;
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
public class StorageController {
    @Autowired
    private StorageRepository repository;

    @GetMapping("/storages")
    public List<StorageLocation> getStorages() {
        List<StorageLocation> list = repository.findAll();
        return list;
    }

    @GetMapping("/storages/{id}")
    public ResponseEntity<Object> getStorageLocation(@PathVariable(value = "id") Integer id) throws ResourceNotFound {
        Optional<StorageLocation> country = repository.findById(id);
        if (!country.isPresent()) throw new ResourceNotFound("StorageLocation not found for id: " + id);
        else return new ResponseEntity<>(country.get(), HttpStatus.OK);
    }

    @PostMapping("/storages")
    public StorageLocation saveStorageLocation(@RequestBody StorageLocation StorageLocation) {
        return repository.save(StorageLocation);
    }


    @DeleteMapping("/storages/{id}")
    public ResponseEntity<Object> deleteStorageLocation(@PathVariable(value = "id") Integer id) throws ResourceNotFound {
        Optional<StorageLocation> StorageLocation = repository.findById(id);
        if (!StorageLocation.isPresent()) throw new ResourceNotFound("StorageLocation not found for id: " + id);
        else repository.delete(StorageLocation.get());

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/storages/{id}")
    public ResponseEntity<Object> putOfStorageLocation(@PathVariable(value = "id") Integer id, @RequestBody StorageLocation StorageLocationChanged) throws ResourceNotFound {
        Optional<StorageLocation> StorageLocation = repository.findById(id);
        if (!StorageLocation.isPresent()) throw new ResourceNotFound("StorageLocation not found for id: " + id);
        else {
            StorageLocationChanged.setId(StorageLocation.get().getId());
            repository.save(StorageLocationChanged);
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("changed", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
