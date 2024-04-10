package com.joaoneto.parkinglot.services;

import com.joaoneto.parkinglot.entities.Spot;
import com.joaoneto.parkinglot.repositories.SpotRepository;
import com.joaoneto.parkinglot.services.exceptions.SpotAlreadyExistsException;
import com.joaoneto.parkinglot.services.exceptions.SpotNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpotService {
    private final SpotRepository spotRepository;

    @Transactional
    public Spot createSpot (Spot spot) {
        try {
            return spotRepository.save(spot);
        } catch (DataIntegrityViolationException e) {
            throw new SpotAlreadyExistsException("Spot already exists!");
        }
    }

    @Transactional(readOnly = true)
    public Spot findByCode(String code) {
        return spotRepository.findByCode(code).orElseThrow(() -> new SpotNotFoundException("Spot not found"));
    }

}
