package com.joaoneto.parkinglot.repositories;

import com.joaoneto.parkinglot.entities.ClientSpot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientSpotRepository extends JpaRepository<ClientSpot, Long> {
}
