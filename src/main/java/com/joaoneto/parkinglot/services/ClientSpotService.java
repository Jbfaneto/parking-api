package com.joaoneto.parkinglot.services;

import com.joaoneto.parkinglot.entities.ClientSpot;
import com.joaoneto.parkinglot.repositories.ClientSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientSpotService {
    private final ClientSpotRepository clientSpotRepository;

    @Transactional
    public ClientSpot createClientSpot(ClientSpot clientSpot) {
        return clientSpotRepository.save(clientSpot);
    }
}
