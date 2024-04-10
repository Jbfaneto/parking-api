package com.joaoneto.parkinglot.services;

import com.joaoneto.parkinglot.entities.Spot;
import com.joaoneto.parkinglot.repositories.SpotRepository;
import com.joaoneto.parkinglot.services.exceptions.SpotAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpotService {
    private final SpotRepository spotRepository;

    public Spot createSpot (Spot spot) {
        try {
            return spotRepository.save(spot);
        } catch (DataIntegrityViolationException e) {
            throw new SpotAlreadyExistsException("Spot already exists!");
        }
    }

}
