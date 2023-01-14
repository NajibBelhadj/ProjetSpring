package com.example.springprojet.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Commande {
    @Id
    @Column(name="NUM_CMD")
    @GeneratedValue
    private long numCommande;
    @Column(name="DATE_CMD")
    private Date dateCommande;
    @Column(name="ADR_LIVRAISON")
    private String adresseLivraison;

    //Relation bidirectionnelle Commande --> Client
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id",nullable = true)
    private Clients client;

    public Commande() {
    }

    public Commande(Date dateCommande, String adresseLivraison) {
        super();
        this.dateCommande = dateCommande;
        this.adresseLivraison = adresseLivraison;
    }

    public Commande(Date dateCommande, String adresseLivraison, Clients client) {
        super();
        this.dateCommande = dateCommande;
        this.adresseLivraison = adresseLivraison;
        this.client = client;
    }

    public long getNumCommande() {
        return numCommande;
    }

    public void setNumCommande(long numCommande) {
        this.numCommande = numCommande;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getAdresseLivraison() {
        return adresseLivraison;
    }

    public void setAdresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
    }

    public Clients getClient() {
        return client;
    }

    public void setClient(Clients client) {
        this.client = client;
    }
}
