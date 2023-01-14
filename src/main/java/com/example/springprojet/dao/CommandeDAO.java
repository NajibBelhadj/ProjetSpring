package com.example.springprojet.dao;

import com.example.springprojet.entities.Clients;
import com.example.springprojet.entities.Commande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommandeDAO extends JpaRepository<Commande, Long> {
    Page<Commande> findByClientId(Long id, PageRequest pr);
}
