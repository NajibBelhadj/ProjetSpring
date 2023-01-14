package com.example.springprojet.dao;

import com.example.springprojet.entities.Clients;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClientDAO extends JpaRepository<Clients, Long> {
    Page<Clients> findByNomClientLike(String name, PageRequest pr);

    Page<Clients> findAllByOrderByNomClient(PageRequest pr);

    @Query("SELECT c from Clients c LEFT JOIN Commande cmd ON c.id=cmd.client GROUP BY c.id ORDER BY COUNT(cmd) DESC")
    Page<Clients> findByOrderByCommande(PageRequest pr);

    @Query("SELECT c FROM Clients c where c.addressClient.ville = :ville")
    Page<Clients> findClientByVille(@Param("ville") String ville, PageRequest pr );
}
