package com.projet.complexe_sportif.service;

import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.complexe_sportif.model.Organisateur;
import com.projet.complexe_sportif.repository.OrganisateurRepository;

import java.util.Optional;

@Data
@Service
public class OrganisateurService {
    @Autowired
    private OrganisateurRepository organisateurRepository;

    public Optional<Organisateur> getOrganisateur(final Integer id){
        return organisateurRepository.findById(id);
    }

    public Iterable<Organisateur> getOrganisateurs() {
        return organisateurRepository.findAll();
    }

    public void deleteOrganisateur(final Integer id){
        organisateurRepository.deleteById(id);
    }
    
    public Organisateur saveOrganisateur(Organisateur organisateur){
        return organisateurRepository.save(organisateur);
    }
}
