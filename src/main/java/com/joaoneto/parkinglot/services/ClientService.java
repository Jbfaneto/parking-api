package com.joaoneto.parkinglot.services;

import com.joaoneto.parkinglot.entities.Client;
import com.joaoneto.parkinglot.repositories.ClientRepository;
import com.joaoneto.parkinglot.services.exceptions.ClientNotFoundException;
import com.joaoneto.parkinglot.services.exceptions.CpfUniqueViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    @Transactional
    public Client createClient(Client client) {
        try {
            return clientRepository.save(client);
        } catch (DataIntegrityViolationException ex) {
            throw new CpfUniqueViolationException(String.format("CPF %s already exists in the database.", client.getCpf()));
        }
    }

    @Transactional
    public Client findClientById(Long id) {
        try{
            return clientRepository.findById(id).orElseThrow();
        } catch (Exception ex) {
            throw new ClientNotFoundException("Client not found");
        }
    }

}
