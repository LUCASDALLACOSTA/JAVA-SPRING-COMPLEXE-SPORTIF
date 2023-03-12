package com.projet.complexe_sportif.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projet.complexe_sportif.model.AdherentSeance;

@Repository
public interface AdherentSeanceRepository extends CrudRepository<AdherentSeance, Integer> {
    
    public AdherentSeance findByAdherent_IdAndSeance_Id(Integer adherentId, Integer id);

}
