package com.projet.complexe_sportif.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projet.complexe_sportif.model.Organisateur;
import com.projet.complexe_sportif.model.Seance;

@Repository
public interface OrganisateurRepository extends CrudRepository<Organisateur, Integer>
{

    List<Organisateur> findBySeancesNotContaining(Seance seance);
    
}