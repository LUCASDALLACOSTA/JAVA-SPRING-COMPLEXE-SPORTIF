package com.projet.complexe_sportif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.projet.complexe_sportif.model.Adherent;
import com.projet.complexe_sportif.repository.AdherentRepository;
import com.projet.complexe_sportif.service.AdherentService;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class AdherentController {
    @Autowired
    private AdherentService adherentService;

    @Autowired
    private AdherentRepository adherentRepository;

    @GetMapping("/adherent/liste") //liste de tout les adhérents
    public ModelAndView listeAdherents() {
        return new ModelAndView("listeAdherent", "adherents", adherentService.getAdherents());
    }

    @GetMapping("/adherent/creer") //créer un adhérent
    public ModelAndView creerAdherent() {
        Adherent a = new Adherent();
        return new ModelAndView("creerAdherent", "adherent", a);
    }

    @PostMapping("/adherent/creer")
    public ModelAndView creerAdherent(@ModelAttribute("adherent") Adherent adherent, ModelMap model) {
        model.addAttribute("nom", adherent.getNom());
        model.addAttribute("prenom", adherent.getPrenom());
        model.addAttribute("age", adherent.getAge());
        model.addAttribute("email", adherent.getEmail());
        model.addAttribute("telephone", adherent.getTelephone());

        adherentService.saveAdherent(adherent);
        ModelAndView modelAndView = new ModelAndView("listeAdherent");
        modelAndView.addObject("adherents", adherentService.getAdherents());
        modelAndView.addObject("message", "L'adhérent a été créé avec succès.");
        return modelAndView;
    }

    @GetMapping("/adherent/modifier/{id}") //modification d'un adhérent
    public ModelAndView modifierAdherent(@PathVariable("id") Integer id, ModelMap model) {
        Adherent adherent = adherentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid adherent Id:" + id));
        model.addAttribute("adherent", adherent);

        ModelAndView modelAndView = new ModelAndView("modifierAdherent");
        modelAndView.addObject("adherent", adherent);
        return modelAndView;
    }

    @PostMapping("/adherent/modifier/{id}")
    public ModelAndView modifierAdherent(@ModelAttribute("adherent") Adherent adherent) {
        adherentRepository.save(adherent);
        ModelAndView modelAndView = new ModelAndView("listeAdherent");
        modelAndView.addObject("adherents", adherentService.getAdherents());
        modelAndView.addObject("message", "L'adhérent a été modifié avec succès.");
        return modelAndView;
    }

    @GetMapping("/adherent/supprimer/{id}") //suppression d'un adhérent
    public ModelAndView supprimerAdherent(@PathVariable("id") Integer id) {
        adherentRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("messageSuppression");
        modelAndView.addObject("message", "Adhérent supprimé avec succès");
        modelAndView.setViewName("redirect:/adherent/liste");
        return modelAndView;
    }
}
