package com.example.springprojet.dao;

import com.example.springprojet.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleDAO extends JpaRepository<AppRole, Long> {
    AppRole findByRoleName(String nomRole);
}

