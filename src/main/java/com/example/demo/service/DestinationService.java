package com.example.demo.service;

import com.example.demo.model.Destination;
import com.example.demo.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinationService {

    @Autowired
    private DestinationRepository destinationRepository;

    public Destination addDestination(Destination destination) {
        return destinationRepository.save(destination);
    }

    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    public Optional<Destination> getDestinationById(Long id) {
        return destinationRepository.findById(id);
    }

    public void deleteDestination(Long id) {
        destinationRepository.deleteById(id);
    }

    public Destination updateDestination(Long id, Destination updated) {
        Optional<Destination> optional = destinationRepository.findById(id);
        if (optional.isPresent()) {
            Destination dest = optional.get();
            dest.setName(updated.getName());
            dest.setDescription(updated.getDescription());
            return destinationRepository.save(dest);
        }
        return null;
    }
}
