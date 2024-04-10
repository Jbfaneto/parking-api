package com.joaoneto.parkinglot.repositories;

import com.joaoneto.parkinglot.entities.Spot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotRepository extends JpaRepository<Spot, Long> {
}
