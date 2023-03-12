package com.projet.complexe_sportif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.projet.complexe_sportif.model.Organisateur;
import com.projet.complexe_sportif.repository.OrganisateurRepository;
import com.projet.complexe_sportif.service.OrganisateurService;
import org.springframework.web.servlet.ModelAndView;
import java.util.Optional;

@RestController
public class OrganisateurController {
    @Autowired
    private OrganisateurService organisateurService;

    @Autowired
    private OrganisateurRepository organisateurRepository;

    @GetMapping("/organisateur/liste")
    public ModelAndView listeOrganisateurs() {
        return new ModelAndView("listeOrganisateur", "organisateurs", organisateurService.getOrganisateurs());
    }

    @GetMapping("/organisateur/creer")
    public ModelAndView creerOrganisateur() {
        Organisateur o = new Organisateur();
        return new ModelAndView("creerOrganisateur", "organisateur", o);
    }

    @PostMapping("/organisateur/creer")
    public ModelAndView creerOrganisateur(@ModelAttribute("organisateur") Organisateur organisateur, ModelMap model) {
        model.addAttribute("nom", organisateur.getNom());
        model.addAttribute("prenom", organisateur.getPrenom());
        model.addAttribute("email", organisateur.getEmail());
        model.addAttribute("telephone", organisateur.getTelephone());

        organisateurService.saveOrganisateur(organisateur);
        ModelAndView modelAndView = new ModelAndView("listeOrganisateur");
        modelAndView.addObject("organisateurs", organisateurService.getOrganisateurs());
        modelAndView.addObject("message", "L'organisateur a été créée avec succès.");
        return modelAndView;
    }

    @GetMapping("/organisateur/modifier/{id}")
    public ModelAndView modifierOrganisateur(@PathVariable("id") Integer id, ModelMap model) {
        Organisateur organisateur = organisateurRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid seance Id:" + id));
        model.addAttribute("organisateur", organisateur);

        ModelAndView modelAndView = new ModelAndView("modifierOrganisateur");
        modelAndView.addObject("organisateur", organisateur);
        return modelAndView;
    }

    @PostMapping("/organisateur/modifier/{id}")
    public ModelAndView modifierOrganisateur(@ModelAttribute("organisateur") Organisateur organisateur) {
        organisateurRepository.save(organisateur);
        ModelAndView modelAndView = new ModelAndView("listeOrganisateur");
        modelAndView.addObject("organisateurs", organisateurService.getOrganisateurs());
        modelAndView.addObject("message", "L'organisateur a été modifié avec succès.");
        return modelAndView;
    }

    @GetMapping("/organisateur/supprimer/{id}")
    public ModelAndView supprimerOrganisateur(@PathVariable("id") Integer id) {
        organisateurRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("messageSuppression");
        modelAndView.addObject("message", "Organisateur supprimé avec succès");
        modelAndView.setViewName("redirect:/organisateur/liste");
        return modelAndView;
    }

    @GetMapping("/organisateur/detail/{id}")
    public ModelAndView detailOrganisateur(@PathVariable("id") final Integer id) {
        Optional<Organisateur> organisateur = organisateurService.getOrganisateur(id);
        return new ModelAndView("detailOrganisateur", "organisateur", organisateur.orElse(null));
    }

}
