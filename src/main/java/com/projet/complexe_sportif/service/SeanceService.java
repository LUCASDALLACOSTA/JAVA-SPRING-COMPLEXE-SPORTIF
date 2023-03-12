package com.projet.complexe_sportif.service;

import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.complexe_sportif.model.Organisateur;
import com.projet.complexe_sportif.model.Seance;
import com.projet.complexe_sportif.repository.SeanceRepository;

import java.util.Optional;

@Data
@Service
public class SeanceService {
    @Autowired
    private SeanceRepository seanceRepository;

    public Optional<Seance> getSeance(final Integer id){
        return seanceRepository.findById(id);
    }

    public Iterable<Seance> getSeances(){
        return seanceRepository.findAll();
    }

    public void deleteSeance(final Integer id){
        seanceRepository.deleteById(id);
    }

    public Seance saveSeance(Seance seance){
        return seanceRepository.save(seance);
    }

    public Optional<Organisateur> getOrganisateurBySeanceId(Integer id) {
        Optional<Seance> seance = seanceRepository.findById(id);
        return seance.map(Seance::getOrganisateur);
    }
}
