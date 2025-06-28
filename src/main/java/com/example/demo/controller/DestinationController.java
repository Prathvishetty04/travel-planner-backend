package com.example.demo.controller;

import com.example.demo.model.Destination;
import com.example.demo.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinations")
@CrossOrigin
public class DestinationController {

    @Autowired
    private DestinationService destinationService;

    @PostMapping
    public Destination createDestination(@RequestBody Destination destination) {
        return destinationService.addDestination(destination);
    }

    @GetMapping
    public List<Destination> getAllDestinations() {
        return destinationService.getAllDestinations();
    }

    @GetMapping("/{id}")
    public Destination getDestination(@PathVariable Long id) {
        return destinationService.getDestinationById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Destination updateDestination(@PathVariable Long id, @RequestBody Destination updated) {
        return destinationService.updateDestination(id, updated);
    }

    @DeleteMapping("/{id}")
    public void deleteDestination(@PathVariable Long id) {
        destinationService.deleteDestination(id);
    }
}
