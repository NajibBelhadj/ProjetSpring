package com.example.springprojet.controller;

import com.example.springprojet.entities.AddressClient;
import com.example.springprojet.entities.Clients;
import com.example.springprojet.entities.Commande;
import com.example.springprojet.service.ClientServiceImp;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Controller
public class ClientsController {
    private ClientServiceImp clientServiceImp;
    public ClientsController(ClientServiceImp clientService) {
        this.clientServiceImp = clientService;
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }


    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }


    @RequestMapping("/accessDenied")
    public String denied() {
        return "accessDenied";
    }



    @RequestMapping("/")
    public String index() {
        return "redirect:/user/client";
    }



    @RequestMapping("user/client")
    public String clients(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "2") int size,
                        @RequestParam(name = "errorMessage", defaultValue = "") String errorMessage){
        //Liste Ville
        List<String> listeVilles = clientServiceImp.getAllVille();
        model.addAttribute("listVilles",listeVilles);

        //Liste Client
        Page<Clients> listeClients = clientServiceImp.getClientsPageable(page, size);
        model.addAttribute("activePage", page);
        model.addAttribute("size", size);
        int[] taillePagination = IntStream.range(0, listeClients.getTotalPages()).toArray();
        model.addAttribute("taillePagination", taillePagination);

        model.addAttribute("nbPages", listeClients.getTotalPages());
        model.addAttribute("nbElements", listeClients.getTotalElements());
        model.addAttribute("listeClients", listeClients);

        return "home";
    }


    @RequestMapping("/user/rechercheClient")
    public String rechercheProduit(Model model,@RequestParam(name = "name", defaultValue = "") String name,
                                   @RequestParam(name = "ville", defaultValue = "") String ville,
                                   @RequestParam(name = "orderBy", defaultValue = "") String orderBy,
                                   @RequestParam(name = "page", defaultValue = "0") int page,
                                   @RequestParam(name = "size", defaultValue = "2") int size,
                                   @RequestParam(name = "errorMessage", defaultValue = "") String errorMessage) {

        if(name.isEmpty() && orderBy.isEmpty() && ville.isEmpty()){
            return "redirect:/user/client";
        } else{
            List<String> listeVilles = clientServiceImp.getAllVille();
            model.addAttribute("listVilles",listeVilles);

            //filter attribute
            model.addAttribute("ville",ville);
            model.addAttribute("name", name);
            model.addAttribute("orderBy", orderBy);


            model.addAttribute("activePage", page);
            model.addAttribute("size", size);

            Page<Clients> listeClients;
            if (name.isEmpty() && ville.isEmpty() && orderBy.equals("name")) {
                listeClients = clientServiceImp.getClientOrderByNomClient(page, size);
            } else if (name.isEmpty() && ville.isEmpty() && orderBy.equals("nbCommande")) {
                listeClients = clientServiceImp.getClientOrderByComandes(page, size);
            } else if (name.isEmpty() && !ville.isEmpty() && orderBy.isEmpty()) {
                    listeClients = clientServiceImp.getClientByVille(ville,page, size);
            } else {
                String likePattern = "%"+name+"%";
                listeClients = clientServiceImp.getClientByName(likePattern,page, size);
            }

            int[] taillePagination = IntStream.range(0, listeClients.getTotalPages()).toArray();
            model.addAttribute("taillePagination", taillePagination);
            model.addAttribute("nbPages", listeClients.getTotalPages());
            model.addAttribute("nbElements", listeClients.getTotalElements());;
            model.addAttribute("listeClients", listeClients);
            model.addAttribute("nom", name);

            return "search";
        }

    }

    @GetMapping("/admin/ajouterClient")
    public String ajoutClient(Model model){
        model.addAttribute("client", new Clients());
        return "ajouterClient";
    }

    @PostMapping("/admin/ajouterClient")
    public String ajoutClient(Clients client, Model model) {
        clientServiceImp.saveClient(client);
        return "redirect:/user/client";
    }

    @GetMapping("/admin/modifierClient")
    public String ModifierClient(@RequestParam("id") Long id,Model model){
        Clients client = clientServiceImp.getClientById(id);
        model.addAttribute("client", client);

        return "ModifierClient";
    }

    @PostMapping("/admin/modifierClient")
    public String ModifierClient(Clients client) {
        clientServiceImp.saveClient(client);
        return "redirect:/user/client";
    }

    @GetMapping("/user/detailClient")
    public String detailClient(@RequestParam("id") Long id,Model model){
        Clients client = clientServiceImp.getClientById(id);
        model.addAttribute("client", client);

        return "detailClient";
    }


    @GetMapping("/user/listCommande")
    public String detailCommande( @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "size", defaultValue = "2") int size,
                                 @RequestParam(name = "errorMessage", defaultValue = "") String errorMessage,
                                  @RequestParam("id") Long id,Model model){


        //Liste Commande
        Page<Commande> listeCommande = clientServiceImp.getCommandePageable(id,page, size);
        model.addAttribute("activePage", page);
        model.addAttribute("size", size);
        int[] taillePagination = IntStream.range(0, listeCommande.getTotalPages()).toArray();
        model.addAttribute("taillePagination", taillePagination);

        model.addAttribute("nbPages", listeCommande.getTotalPages());
        model.addAttribute("nbElements", listeCommande.getTotalElements());
        model.addAttribute("listeCommandes", listeCommande);
        model.addAttribute("idClient", id);

        return "listCommande";
    }



    @GetMapping("/admin/supprimerClient")
    public String supprimerClient(Long id, Long activePage, Long nbElements, Long size, RedirectAttributes ra) {
        clientServiceImp.deleteClient(id);
        if(activePage>0 && ((nbElements-1)==(size * (activePage))))
            activePage--;
        ra.addAttribute("page", activePage);
        return "redirect:/user/client";
    }



}
