package com.joaoneto.parkinglot.repositories;

import com.joaoneto.parkinglot.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c WHERE c.user.id = :id")
    Client findByUserId(long id);

    @Query("select c FROM Client c WHERE c.cpf = :cpf")
    Optional<Client> findByCpf(String cpf);
}
