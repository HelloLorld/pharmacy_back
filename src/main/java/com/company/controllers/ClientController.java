package com.company.controllers;

import com.company.exception.ResourceNotFound;
import com.company.models.Client;
import com.company.repository.ClientRepository;
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
public class ClientController {
    @Autowired
    private ClientRepository repository;

    @GetMapping("/clients")
    public List<Client> getClients() {
        List<Client> list = repository.findAll();
        return list;
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Object> getClient(@PathVariable(value = "id") Integer id) throws ResourceNotFound {
        Optional<Client> country = repository.findById(id);
        if (!country.isPresent()) throw new ResourceNotFound("Client not found for id: " + id);
        else return new ResponseEntity<>(country.get(), HttpStatus.OK);
    }

    @PostMapping("/clients")
    public Client saveClient(@RequestBody Client Client) {
        return repository.save(Client);
    }


    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Object> deleteClient(@PathVariable(value = "id") Integer id) throws ResourceNotFound {
        Optional<Client> Client = repository.findById(id);
        if (!Client.isPresent()) throw new ResourceNotFound("Client not found for id: " + id);
        else repository.delete(Client.get());

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<Object> putOfClient(@PathVariable(value = "id") Integer id, @RequestBody Client ClientChanged) throws ResourceNotFound {
        Optional<Client> Client = repository.findById(id);
        if (!Client.isPresent()) throw new ResourceNotFound("Client not found for id: " + id);
        else {
            ClientChanged.setId(Client.get().getId());
            repository.save(ClientChanged);
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("changed", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
