package com.company.controllers;

import com.company.exception.ResourceNotFound;
import com.company.models.Medication;
import com.company.repository.MedicationRepository;
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
public class MedicationController {
    @Autowired
    private MedicationRepository repository;

    @GetMapping("/medications")
    public List<Medication> getMedications() {
        return repository.findAll();
    }

    @GetMapping("/medications/{id}")
    public ResponseEntity<Object> getMedication(@PathVariable(value = "id") Integer id) throws ResourceNotFound {
        Optional<Medication> Medication = repository.findById(id);
        if (!Medication.isPresent()) throw new ResourceNotFound("Medication not found for id: " + id); /*return
                new ResponseEntity<>(new ResourceNotFound("Medication not found for id: " + id).getMessage(), HttpStatus.NOT_FOUND);*/
        else return new ResponseEntity<>(Medication.get(), HttpStatus.OK);
    }

    @PostMapping("/medications")
    public Medication saveMedication(@RequestBody Medication medication) {
        return repository.save(medication);
    }

    @DeleteMapping("/medications/{id}")
    public ResponseEntity<Object> deleteMedication(@PathVariable(value = "id") Integer id) throws ResourceNotFound {
        Optional<Medication> Medication = repository.findById(id);
        if (!Medication.isPresent()) throw new ResourceNotFound("Medication not found for id: " + id);
        else repository.delete(Medication.get());

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/medications/{id}")
    public ResponseEntity<Object> putOfMedication(@PathVariable(value = "id") Integer id, @RequestBody Medication MedicationChanged) throws ResourceNotFound {
        Optional<Medication> Medication = repository.findById(id);
        if (!Medication.isPresent()) throw new ResourceNotFound("Medication not found for id: " + id);
        else {
            MedicationChanged.setId(Medication.get().getId());
            repository.save(MedicationChanged);
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("changed", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @PatchMapping("/Medications/{id}")
//    public ResponseEntity<Object> patchOfMedication(@PathVariable(value = "id") Integer id, @RequestBody Medication MedicationChanged) throws ResourceNotFound {
//        Optional<Medication> Medication = repository.findById(id);
//        boolean needChange;
//        if (!Medication.isPresent()) throw new ResourceNotFound("Medication not found for id: " + id);
//        else {
//            MedicationChanged.setId(Medication.get().getId());
//            needChange = service.checkNeedUpdate(Medication.get(), MedicationChanged);
//            if (needChange) repository.save(MedicationChanged);
//        }
//
//        if (needChange) {
//            return new ResponseEntity<>(MedicationChanged, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(null, HttpStatus.OK);
//        }
//    }
}
