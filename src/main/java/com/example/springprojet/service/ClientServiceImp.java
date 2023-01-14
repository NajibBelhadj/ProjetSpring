package com.example.springprojet.service;

import com.example.springprojet.dao.*;
import com.example.springprojet.entities.AppRole;
import com.example.springprojet.entities.AppUser;
import com.example.springprojet.entities.Clients;
import com.example.springprojet.entities.Commande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientServiceImp implements ClientService {

    private ClientDAO clientDAO;
    private AddressClientDAO addressClientDAO;
    private AppUserDAO appUserDAO;
    private AppRoleDAO appRoleDAO;
    private PasswordEncoder passwordEncoder;
    private final CommandeDAO commandeDAO;

    public ClientServiceImp(AppUserDAO appUserDAO, AppRoleDAO appRoleDAO, PasswordEncoder passwordEncoder, ClientDAO clientDAO,AddressClientDAO addressClientDAO,
                            CommandeDAO commandeDAO) {
        this.appUserDAO = appUserDAO;
        this.appRoleDAO = appRoleDAO;
        this.passwordEncoder = passwordEncoder;
        this.clientDAO= clientDAO;
        this.addressClientDAO=addressClientDAO;
        this.commandeDAO = commandeDAO;
    }

    @Override
    public List<Clients> getClient() {
        return clientDAO.findAll();
    }

    @Override
    public Page<Clients> getClientsPageable(int page, int size) {
        // TODO Auto-generated method stub
        PageRequest client = PageRequest.of(page, size);
        return clientDAO.findAll(client);
    }

    @Override
    public AppUser saveAppUser(AppUser appUser) {
        // TODO Auto-generated method stub
        String mdp = appUser.getPassword();
        appUser.setPassword(passwordEncoder.encode(mdp));
        return appUserDAO.save(appUser);
    }

    @Override
    public AppRole saveAppRole(AppRole appRole) {
        // TODO Auto-generated method stub
        return appRoleDAO.save(appRole);
    }

    @Override
    public void addRoleToUser(String login, String nomRole) {
        // TODO Auto-generated method stub
        AppUser user =appUserDAO.findByUsername(login);
        AppRole role =appRoleDAO.findByRoleName(nomRole);
        if(user!=null && role!=null)
            user.getRoles().add(role);


    }

    @Override
    public AppUser getUserByLogin(String login) {
        // TODO Auto-generated method stub
        return appUserDAO.findByUsername(login);
    }

    @Override
    public List<AppUser> getAppUsers() {
        // TODO Auto-generated method stub
        return appUserDAO.findAll();
    }

    @Override
    public Page<Clients> getClientByName(String name,int page,int size) {
        // TODO Auto-generated method stub
        PageRequest client = PageRequest.of(page, size);
            return clientDAO.findByNomClientLike(name,client);
    }

    @Override
    public Page<Clients> getClientOrderByNomClient(int page,int size) {
        // TODO Auto-generated method stub
        PageRequest client = PageRequest.of(page, size);
        return clientDAO.findAllByOrderByNomClient(client);
    }

    @Override
    public Page<Clients> getClientOrderByComandes(int page,int size) {
        // TODO Auto-generated method stub
        PageRequest client = PageRequest.of(page, size);
        return clientDAO.findByOrderByCommande(client);
    }

    @Override
    public Page<Clients> getClientByVille(String ville, int page,int size) {
        // TODO Auto-generated method stub
        PageRequest pr = PageRequest.of(page, size);

        List<Clients> cl = clientDAO.findClientByVille(ville,pr).getContent();

        for(Clients c:cl){
            System.out.println("dbhjlfksdkjsdlfsdohffkslùsdf"+c.getNomClient());
        }

        System.out.println("dbhjlfksdkjsdlfsdohffkslùsdf"+clientDAO.findClientByVille(ville,pr).getTotalPages());

        return clientDAO.findClientByVille(ville , pr);
    }

    @Override
    public void deleteClient(Long id) {
        // TODO Auto-generated method stub
        clientDAO.deleteById(id);
    }
    @Override
    public List<String> getAllVille() {
        // TODO Auto-generated method stub
        return addressClientDAO.findDistinctVille();
    }
    @Override
    public void saveClient(Clients client) {
        // TODO Auto-generated method stub
        clientDAO.save(client);
    }
    @Override
    public Clients getClientById(Long id){
        Optional<Clients> p = clientDAO.findById(id);
        if (p.isPresent())
            return p.get();
        else
            return null;
    }

    @Override
    public Page<Commande> getCommandePageable(Long id, int page, int size) {
        // TODO Auto-generated method stub
        PageRequest commande = PageRequest.of(page, size);
        return commandeDAO.findByClientId(id,commande);
    }

}
