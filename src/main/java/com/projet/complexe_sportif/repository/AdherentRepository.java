package com.projet.complexe_sportif.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projet.complexe_sportif.model.Adherent;
import com.projet.complexe_sportif.model.Seance;

@Repository
public interface AdherentRepository extends CrudRepository<Adherent, Integer>
{
    List<Adherent> findBySeancesNotContaining(Seance seance);
}