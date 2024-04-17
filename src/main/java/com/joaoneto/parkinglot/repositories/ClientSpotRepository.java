package com.joaoneto.parkinglot.repositories;

import com.joaoneto.parkinglot.entities.ClientSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientSpotRepository extends JpaRepository<ClientSpot, Long> {
    @Query("SELECT c FROM ClientSpot c WHERE c.receipt = :receipt AND c.exitTime IS NULL")
    Optional<ClientSpot> findByReceiptAndExitTimeIsNull(String receipt);

    @Query("SELECT COUNT(c) FROM ClientSpot c WHERE c.client.cpf = :cpf")
    long countByClientCpf(String cpf);
}
