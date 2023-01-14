package com.example.springprojet.service;

import com.example.springprojet.entities.AppRole;
import com.example.springprojet.entities.AppUser;
import com.example.springprojet.entities.Clients;
import com.example.springprojet.entities.Commande;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClientService {

    public List<Clients> getClient();
    public Page<Clients> getClientsPageable(int page, int size);

    public Page<Clients> getClientByName(String name,int page, int size);

    public Page<Clients> getClientOrderByNomClient(int page, int size);
    public Page<Clients> getClientOrderByComandes(int page, int size);

    public Page<Clients> getClientByVille(String ville, int page, int size);

    public List<String> getAllVille();

    public void deleteClient(Long id);
    public AppUser saveAppUser(AppUser appUser);
    public AppRole saveAppRole(AppRole appRole);
    public void addRoleToUser(String login, String nomRole);
    public AppUser getUserByLogin(String login);
    public List<AppUser> getAppUsers();

    public void saveClient(Clients client);
    public Clients getClientById(Long id);

    public Page<Commande> getCommandePageable(Long id, int page, int size);

}
