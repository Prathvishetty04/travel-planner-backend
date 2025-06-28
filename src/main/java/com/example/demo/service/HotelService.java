package com.example.demo.service;

import com.example.demo.model.Hotel;
import com.example.demo.model.Destination;
import com.example.demo.repository.DestinationRepository;
import com.example.demo.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private DestinationRepository destinationRepository;

    public Hotel addHotel(Long destinationId, Hotel hotel) {
        Optional<Destination> destOpt = destinationRepository.findById(destinationId);
        if (destOpt.isPresent()) {
            hotel.setDestination(destOpt.get());
            return hotelRepository.save(hotel);
        }
        return null;
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Optional<Hotel> getHotelById(Long id) {
        return hotelRepository.findById(id);
    }

    public Hotel updateHotel(Long id, Hotel updatedHotel) {
        return hotelRepository.findById(id).map(existingHotel -> {
            existingHotel.setName(updatedHotel.getName());
            existingHotel.setAddress(updatedHotel.getAddress());
            existingHotel.setContactInfo(updatedHotel.getContactInfo());
            return hotelRepository.save(existingHotel);
        }).orElse(null);
    }

    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }


}