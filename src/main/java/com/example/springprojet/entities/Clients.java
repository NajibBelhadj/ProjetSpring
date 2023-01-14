package com.example.springprojet.entities;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="CLIENTS")
public class Clients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    private String nomClient;
    private String mailClient;
    private String telClient;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressClient addressClient;

    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
    private Set<Commande> commandes = new HashSet<Commande>();

    public Clients() {
    }

    public Clients(String nomClient, String mailClient, String telClient) {
        super();
        this.nomClient = nomClient;
        this.mailClient = mailClient;
        this.telClient = telClient;
    }

    public Clients(long id,String nomClient, String mailClient, String telClient) {
        super();
        this.id=id;
        this.nomClient = nomClient;
        this.mailClient = mailClient;
        this.telClient = telClient;
    }

    public Clients(long id, String nomClient, String mailClient, String telClient, AddressClient addressClient) {
        super();
        this.id = id;
        this.nomClient = nomClient;
        this.mailClient = mailClient;
        this.telClient = telClient;
        this.addressClient = addressClient;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getMailClient() {
        return mailClient;
    }

    public void setMailClient(String mailClient) {
        this.mailClient = mailClient;
    }

    public String getTelClient() {
        return telClient;
    }

    public void setTelClient(String telClient) {
        this.telClient = telClient;
    }

    public AddressClient getAddressClient() {
        return addressClient;
    }

    public void setAddressClient(AddressClient addressClient) {
        this.addressClient = addressClient;
    }

    public Set<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(Set<Commande> commandes) {
        this.commandes = commandes;
    }

    public void ajouterCommande(Commande c) {
        this.commandes.add(c);
    }

    public void supprimerCommande(Commande c) {
        this.commandes.remove(c);
    }
}
