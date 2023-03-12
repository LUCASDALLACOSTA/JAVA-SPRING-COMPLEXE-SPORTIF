package com.projet.complexe_sportif.model;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seance")
public class Seance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date", length = 50)
    private Date date;

    @Column(name = "heureDebut", length = 50, columnDefinition = "TIME")
    @DateTimeFormat(pattern = "HH:mm")
    private Time heureDebut;

    @Column(name = "heureFin", length = 50, columnDefinition = "TIME")
    @DateTimeFormat(pattern = "HH:mm")
    private Time heureFin;

    @Column(name = "sport", length = 50)
    private String sport;

    @Column(name = "lieu", length = 255)
    private String lieu;

    @ManyToOne
    @JoinColumn(name = "idOrganisateur")
    private Organisateur organisateur;

    @ManyToMany
    @JoinTable(name = "adherent_seance", joinColumns = @JoinColumn(name = "seance_id"), inverseJoinColumns = @JoinColumn(name = "adherent_id"))
    private List<Adherent> adherents = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Time heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Time getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(Time heureFin) {
        this.heureFin = heureFin;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Organisateur getOrganisateur() {
        return organisateur;
    }

    public void setOrganisateur(Organisateur organisateur) {
        this.organisateur = organisateur;
    }

    public List<Adherent> getAdherents() {
        return adherents;
    }

    public void setAdherents(List<Adherent> adherents) {
        this.adherents = adherents;
    }

    @Override
    public String toString() {
        return "Seance [id=" + id + ", date=" + date + ", heureDebut=" + heureDebut + ", heureFin=" + heureFin
                + ", sport=" + sport + ", lieu=" + lieu + ", organisateur=" + organisateur + ", adherents=" + adherents
                + "]";
    }
}
