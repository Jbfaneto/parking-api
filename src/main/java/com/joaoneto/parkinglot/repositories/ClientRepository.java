package com.joaoneto.parkinglot.repositories;

import com.joaoneto.parkinglot.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
