package com.joaoneto.parkinglot.services;

import com.joaoneto.parkinglot.entities.ClientSpot;
import com.joaoneto.parkinglot.repositories.ClientSpotRepository;
import com.joaoneto.parkinglot.repositories.projection.ClientSpotProjection;
import com.joaoneto.parkinglot.services.exceptions.ClientSpotNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional(readOnly = true)
    public ClientSpot findByReceiptAndExitTimeIsNull(String receipt) {
        return clientSpotRepository.findByReceiptAndExitTimeIsNull(receipt)
                .orElseThrow(() -> new ClientSpotNotFoundException("Receipt not found or car already checked out"));
    }

    @Transactional(readOnly = true)
    public long countByClientCpf(String cpf) {
        return clientSpotRepository.countByClientCpf(cpf);
    }

    @Transactional(readOnly = true)
    public Page<ClientSpotProjection> findAllSpotsByClientCpf(String cpf, Pageable pageable) {
        return clientSpotRepository.findAllSpotsByClientCpf(cpf, pageable);
    }
}
