package com.joaoneto.parkinglot.repositories;

import com.joaoneto.parkinglot.entities.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SpotRepository extends JpaRepository<Spot, Long> {
    @Query("SELECT s FROM Spot s WHERE s.code=:code")
    Optional<Spot> findByCode(String code);
}
