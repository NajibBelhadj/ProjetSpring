package com.example.springprojet;

import com.example.springprojet.dao.ClientDAO;
import com.example.springprojet.entities.*;
import com.example.springprojet.service.ClientService;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
public class SpringProjetApplication {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    public static void main(String[] args) {

        ApplicationContext ctx= SpringApplication.run(SpringProjetApplication.class, args);

        ClientDAO clientDAO=ctx.getBean(ClientDAO.class);


        Clients client1 = new Clients("fahmy","najibbelhadj@gmail.com","52365458");
        Clients client0 = new Clients("najib","najibbelhadj@gmail.com","26425926");
        Clients client2 = new Clients("hamadi","najibbelhadj@gmail.com","99874563");
        Clients client3 = new Clients("hamadi","najibbelhadj@gmail.com","46898541");

        AddressClient addressClient = new AddressClient("hbib bourgiba","Tunis","2000");
        AddressClient addressClient1 = new AddressClient("hbib bourgiba","Manouba","2000");

        Commande cd1 = new Commande(new Date(),"Tunis", client0);
        Commande cd2 = new Commande(new Date(), "Bizerte", client0);
        Commande cd3 = new Commande(new Date(), "Nabeul", client1);


        clientDAO.save(client1);
        clientDAO.save(client0);
        clientDAO.save(client2);
        clientDAO.save(client3);

        client0.ajouterCommande(cd1);
        client0.ajouterCommande(cd2);
        client1.ajouterCommande(cd3);;

        client1.setAddressClient(addressClient);
        client2.setAddressClient(addressClient);
        client3.setAddressClient(addressClient1);
        client0.setAddressClient(addressClient);


        clientDAO.save(client1);
        clientDAO.save(client0);
        clientDAO.save(client2);
        clientDAO.save(client3);




        ClientService mv = ctx.getBean(ClientService.class);
        AppRole user = new AppRole(null, "USER");
        AppRole admin = new AppRole(null, "ADMIN");
        mv.saveAppRole(user);
        mv.saveAppRole(admin);
        mv.saveAppUser(new AppUser(null, "user","12345678", 1, new ArrayList<>()));
        mv.saveAppUser(new AppUser(null, "admin","12345678", 1, new ArrayList<AppRole>()));
        mv.addRoleToUser("user", "USER");
        mv.addRoleToUser("admin", "USER");
        mv.addRoleToUser("admin", "ADMIN");
    }

}
