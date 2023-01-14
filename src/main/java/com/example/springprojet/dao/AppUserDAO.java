package com.example.springprojet.dao;

import com.example.springprojet.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserDAO extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String login);
}
