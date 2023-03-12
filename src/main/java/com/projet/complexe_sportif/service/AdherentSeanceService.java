package com.projet.complexe_sportif.service;

import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.complexe_sportif.model.AdherentSeance;
import com.projet.complexe_sportif.repository.AdherentSeanceRepository;

import java.util.Optional;

@Data
@Service
public class AdherentSeanceService {
    @Autowired
    private AdherentSeanceRepository adherentSeanceRepository;

    public Optional<AdherentSeance> getAdherent(final Integer id){
        return adherentSeanceRepository.findById(id);
    }

    public Iterable<AdherentSeance> getAdherents() {
        return adherentSeanceRepository.findAll();
    }

    public void deleteAdherentSeance(final Integer id){
        adherentSeanceRepository.deleteById(id);
    }
    
    public AdherentSeance saveAdherentSeance(AdherentSeance adherentSeance){
        return adherentSeanceRepository.save(adherentSeance);
    }
}
