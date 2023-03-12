package com.projet.complexe_sportif.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projet.complexe_sportif.model.Seance;

@Repository
public interface SeanceRepository extends CrudRepository<Seance, Integer>
{
    
}