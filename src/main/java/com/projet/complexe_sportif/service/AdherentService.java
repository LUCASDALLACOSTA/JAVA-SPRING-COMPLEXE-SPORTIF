package com.projet.complexe_sportif.service;

import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.complexe_sportif.model.Adherent;
import com.projet.complexe_sportif.repository.AdherentRepository;

import java.util.Optional;

@Data
@Service
public class AdherentService {
    @Autowired
    private AdherentRepository adherentRepository;

    public Optional<Adherent> getAdherent(final Integer id){
        return adherentRepository.findById(id);
    }

    public Iterable<Adherent> getAdherents() {
        return adherentRepository.findAll();
    }

    public void deleteAdherent(final Integer id){
        adherentRepository.deleteById(id);
    }
    
    public Adherent saveAdherent(Adherent adherent){
        return adherentRepository.save(adherent);
    }
}
