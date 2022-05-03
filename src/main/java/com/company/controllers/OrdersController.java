package com.company.controllers;

import com.company.exception.ResourceNotFound;
import com.company.models.Orders;
import com.company.repository.OrderedRepository;
import com.company.repository.OrdersRepository;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class OrdersController {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderedRepository orderedRepository;

    @GetMapping("/orders")
    public List<Orders> getOrders() {
        List<Orders> list = ordersRepository.findAll();
        return list;
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Object> getOrders(@PathVariable(value = "id") Integer id) throws ResourceNotFound {
        Optional<Orders> country = ordersRepository.findById(id);
        if (!country.isPresent()) throw new ResourceNotFound("Orders not found for id: " + id);
        else return new ResponseEntity<>(country.get(), HttpStatus.OK);
    }

    @PostMapping("/orders")
    public Orders saveOrders(@RequestBody Orders Orders) {
        return ordersRepository.save(Orders);
    }


    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Object> deleteOrders(@PathVariable(value = "id") Integer id) throws ResourceNotFound {
        Optional<Orders> Orders = ordersRepository.findById(id);
        if (!Orders.isPresent()) throw new ResourceNotFound("Orders not found for id: " + id);
        else {
            Orders.get().getOrdered().forEach(ordered -> orderedRepository.deleteById(ordered.getOrderedId().getFkOrder(), ordered.getOrderedId().getFkMedication()));
            Orders orders = Orders.get();
            orders.setOrdered(new ArrayList<>());
            ordersRepository.delete(orders);
        }

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<Object> putOfOrders(@PathVariable(value = "id") Integer id, @RequestBody Orders OrdersChanged) throws ResourceNotFound {
        Optional<Orders> Orders = ordersRepository.findById(id);
        if (!Orders.isPresent()) throw new ResourceNotFound("Orders not found for id: " + id);
        else {
            OrdersChanged.setId(Orders.get().getId());
            ordersRepository.save(OrdersChanged);
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("changed", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
