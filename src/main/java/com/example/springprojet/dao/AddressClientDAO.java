package com.example.springprojet.dao;

import com.example.springprojet.entities.AddressClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressClientDAO  extends JpaRepository<AddressClient,Long> {

    @Query(value = "SELECT distinct ville FROM address",nativeQuery = true)
    List<String> findDistinctVille();
}
