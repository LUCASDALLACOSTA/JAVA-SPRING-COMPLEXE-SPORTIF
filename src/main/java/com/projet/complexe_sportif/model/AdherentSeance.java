package com.projet.complexe_sportif.model;

import javax.persistence.*;

@Entity
@Table(name = "adherent_seance")
public class AdherentSeance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "seance_id")
    private Seance seance;

    @ManyToOne
    @JoinColumn(name = "adherent_id")
    private Adherent adherent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Seance getSeance() {
        return seance;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
    }

    @Override
    public String toString() {
        return "AdherentSeance [id=" + id + ", seance=" + seance + ", adherent=" + adherent + "]";
    }

}
