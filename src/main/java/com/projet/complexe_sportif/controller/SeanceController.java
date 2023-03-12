package com.projet.complexe_sportif.controller;

import com.projet.complexe_sportif.model.Organisateur;
import com.projet.complexe_sportif.model.Seance;
import com.projet.complexe_sportif.model.Adherent;
import com.projet.complexe_sportif.model.AdherentSeance;
import com.projet.complexe_sportif.repository.SeanceRepository;
import com.projet.complexe_sportif.repository.AdherentRepository;
import com.projet.complexe_sportif.repository.AdherentSeanceRepository;
import com.projet.complexe_sportif.repository.OrganisateurRepository;
import com.projet.complexe_sportif.service.SeanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@RestController
public class SeanceController {
    @Autowired
    private SeanceService seanceService;

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private AdherentRepository adherentRepository;

    @Autowired
    private OrganisateurRepository organisateurRepository;

    @Autowired
    private AdherentSeanceRepository adherentSeanceRepository;

    @GetMapping("/seance/liste") // liste de toutes les séances
    public ModelAndView listeSeances() {
        return new ModelAndView("listeSeance", "seances", seanceService.getSeances());
    }

    @GetMapping("/seance/creer") // création d'une séance
    public ModelAndView creerSeance() {
        ModelAndView modelAndView = new ModelAndView("creerSeance");
        modelAndView.addObject("seance", new Seance());
        modelAndView.addObject("organisateurs", organisateurRepository.findAll()); // Récupération de la liste des
                                                                                   // organisateurs
        return modelAndView;
    }

    @PostMapping("/seance/creer")
    public ModelAndView creerSeance(@ModelAttribute("seance") Seance seance, ModelMap model) {
        model.addAttribute("date", seance.getDate());
        model.addAttribute("heureDebut", seance.getHeureDebut());
        model.addAttribute("heureFin", seance.getHeureFin());
        model.addAttribute("sport", seance.getSport());
        model.addAttribute("lieu", seance.getLieu());

        seanceService.saveSeance(seance);
        ModelAndView modelAndView = new ModelAndView("listeSeance");
        modelAndView.addObject("seances", seanceService.getSeances());
        modelAndView.addObject("message", "La séance a été créée avec succès.");
        return modelAndView;
    }

    @GetMapping("/seance/modifier/{id}") // modification d'une séance
    public ModelAndView modifierSeance(@PathVariable("id") Integer id, ModelMap model) {
        Seance seance = seanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid seance Id:" + id));
        model.addAttribute("seance", seance);

        ModelAndView modelAndView = new ModelAndView("modifierSeance");
        modelAndView.addObject("seance", seance);
        return modelAndView;
    }

    @PostMapping("/seance/modifier/{id}")
    public ModelAndView modifierSeance(@ModelAttribute("seance") Seance seance) {
        seanceRepository.save(seance);
        ModelAndView modelAndView = new ModelAndView("listeSeance");
        modelAndView.addObject("seances", seanceService.getSeances());
        modelAndView.addObject("message", "La séance a été modifié avec succès.");
        return modelAndView;
    }

    @GetMapping("/seance/supprimer/{id}") // suppression d'une séance
    public ModelAndView supprimerSeance(@PathVariable("id") Integer id) {
        seanceRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("messageSuppression");
        modelAndView.addObject("message", "Séance supprimée avec succès");
        modelAndView.setViewName("redirect:/seance/liste");
        return modelAndView;
    }

    @GetMapping("/seance/{id}/detail") // détail d'une séance
    public ModelAndView detailSeance(@PathVariable("id") Integer id) {
        Seance seance = seanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid seance Id:" + id));
        Organisateur organisateur = seance.getOrganisateur();
        List<Adherent> adherents = seance.getAdherents();
        ModelAndView modelAndView = new ModelAndView("detailSeance");
        modelAndView.addObject("seance", seance);
        modelAndView.addObject("organisateur", organisateur);
        modelAndView.addObject("adherents", adherents);
        modelAndView.addObject("seanceId", id);
        return modelAndView;
    }

    @GetMapping("/seance/{id}/ajoutAdherent") // ajouter un adhérent à une séance
    public ModelAndView ajoutAdherent(@PathVariable("id") Integer id) {
        Seance seance = seanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid seance Id:" + id));
        List<Adherent> adherents = adherentRepository.findBySeancesNotContaining(seance);
        ModelAndView modelAndView = new ModelAndView("ajoutAdherent");
        modelAndView.addObject("seance", seance);
        modelAndView.addObject("ajoutAdherent", adherents);
        return modelAndView;
    }

    @PostMapping("/seance/{id}/ajoutAdherent")
    public ModelAndView ajoutAdherent(@PathVariable("id") Integer id,
            @RequestParam("adherentId") Integer adherentId) {
        Seance seance = seanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid seance Id:" + id));
        Adherent adherent = adherentRepository.findById(adherentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid adherent Id:" + adherentId));
        AdherentSeance adherentSeance = new AdherentSeance();
        adherentSeance.setSeance(seance);
        adherentSeance.setAdherent(adherent);
        adherentSeanceRepository.save(adherentSeance);
        ModelAndView modelAndView = new ModelAndView("redirect:/seance/{id}/detail");
        modelAndView.addObject("message", "Adhérent ajouté à la séance avec succès");
        modelAndView.addObject("id", id);
        return modelAndView;
    }

    @GetMapping("/seance/{id}/retirerAdherent/{adherentId}") // retirer un adherent d'une séance
    public ModelAndView retirerAdherent(@PathVariable("id") Integer id,
            @PathVariable("adherentId") Integer adherentId) {
        AdherentSeance adherentSeance = adherentSeanceRepository.findByAdherent_IdAndSeance_Id(adherentId, id);
        adherentSeanceRepository.delete(adherentSeance);
        ModelAndView modelAndView = new ModelAndView("messageSuppression");
        modelAndView.addObject("message", "Adhérent retiré de la séance avec succès");
        modelAndView.setViewName("redirect:/seance/{id}/detail");
        return modelAndView;
    }

    @GetMapping("/seance/{id}/changerOrganisateur") // changer l'organisateur
    public ModelAndView changerOrganisateur(@PathVariable("id") Integer id) {
        Seance seance = seanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid seance Id:" + id));
        List<Organisateur> organisateurs = organisateurRepository.findBySeancesNotContaining(seance);
        ModelAndView modelAndView = new ModelAndView("changerOrganisateur");
        modelAndView.addObject("seance", seance);
        modelAndView.addObject("changerOrganisateur", organisateurs);
        return modelAndView;
    }

    @PostMapping("/seance/{id}/changerOrganisateur")
    public ModelAndView changerOrganisateur(@PathVariable("id") Integer id,
            @RequestParam("organisateurId") Integer organisateurId) {
        Seance seance = seanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid seance Id:" + id));
        Organisateur nouvelOrganisateur = organisateurRepository.findById(organisateurId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid organisateur Id:" + organisateurId));
        seance.setOrganisateur(nouvelOrganisateur);
        seanceRepository.save(seance);
        ModelAndView modelAndView = new ModelAndView("detailSeance");
        modelAndView.addObject("seance", seance);
        modelAndView.addObject("seanceId", id);
        modelAndView.addObject("message", "Organisateur de la séance changé avec succès");
        return modelAndView;
    }

    @GetMapping("/testMethodeEchouee")  //méthode pour tester la methode aspect methodeEchouee
    public String testMethodeEchouee() {
        throw new RuntimeException("");
    }
}
