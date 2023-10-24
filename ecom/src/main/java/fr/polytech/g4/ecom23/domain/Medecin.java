package fr.polytech.g4.ecom23.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Medecin.
 */
@Entity
@Table(name = "medecin")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Medecin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_m", nullable = false, unique = true)
    private Long idM;

    @Column(name = "nom_m")
    private String nomM;

    @Column(name = "prenom_m")
    private String prenomM;

    @JsonIgnoreProperties(value = { "soignant", "medecin", "admin" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Compte compte;

    @OneToMany(mappedBy = "medecin")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "patient", "servicesoignant", "soigant", "medecin" }, allowSetters = true)
    private Set<Tache> taches = new HashSet<>();

    @OneToMany(mappedBy = "medecin")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "medecin" }, allowSetters = true)
    private Set<Rappel> alertes = new HashSet<>();

    @OneToMany(mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "patient", "medecin" }, allowSetters = true)
    private Set<Notes> notes = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_medecin__patients",
        joinColumns = @JoinColumn(name = "medecin_id"),
        inverseJoinColumns = @JoinColumn(name = "patients_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "alerte", "notes", "infrastructure", "suividonnees", "taches", "medecins", "soignants" },
        allowSetters = true
    )
    private Set<Patient> patients = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_medecin__etablissement",
        joinColumns = @JoinColumn(name = "medecin_id"),
        inverseJoinColumns = @JoinColumn(name = "etablissement_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "patients", "medecins" }, allowSetters = true)
    private Set<Etablissement> etablissements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Medecin id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdM() {
        return this.idM;
    }

    public Medecin idM(Long idM) {
        this.setIdM(idM);
        return this;
    }

    public void setIdM(Long idM) {
        this.idM = idM;
    }

    public String getNomM() {
        return this.nomM;
    }

    public Medecin nomM(String nomM) {
        this.setNomM(nomM);
        return this;
    }

    public void setNomM(String nomM) {
        this.nomM = nomM;
    }

    public String getPrenomM() {
        return this.prenomM;
    }

    public Medecin prenomM(String prenomM) {
        this.setPrenomM(prenomM);
        return this;
    }

    public void setPrenomM(String prenomM) {
        this.prenomM = prenomM;
    }

    public Compte getCompte() {
        return this.compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public Medecin compte(Compte compte) {
        this.setCompte(compte);
        return this;
    }

    public Set<Tache> getTaches() {
        return this.taches;
    }

    public void setTaches(Set<Tache> taches) {
        if (this.taches != null) {
            this.taches.forEach(i -> i.setMedecin(null));
        }
        if (taches != null) {
            taches.forEach(i -> i.setMedecin(this));
        }
        this.taches = taches;
    }

    public Medecin taches(Set<Tache> taches) {
        this.setTaches(taches);
        return this;
    }

    public Medecin addTaches(Tache tache) {
        this.taches.add(tache);
        tache.setMedecin(this);
        return this;
    }

    public Medecin removeTaches(Tache tache) {
        this.taches.remove(tache);
        tache.setMedecin(null);
        return this;
    }

    public Set<Rappel> getAlertes() {
        return this.alertes;
    }

    public void setAlertes(Set<Rappel> rappels) {
        if (this.alertes != null) {
            this.alertes.forEach(i -> i.setMedecin(null));
        }
        if (rappels != null) {
            rappels.forEach(i -> i.setMedecin(this));
        }
        this.alertes = rappels;
    }

    public Medecin alertes(Set<Rappel> rappels) {
        this.setAlertes(rappels);
        return this;
    }

    public Medecin addAlertes(Rappel rappel) {
        this.alertes.add(rappel);
        rappel.setMedecin(this);
        return this;
    }

    public Medecin removeAlertes(Rappel rappel) {
        this.alertes.remove(rappel);
        rappel.setMedecin(null);
        return this;
    }

    public Set<Notes> getNotes() {
        return this.notes;
    }

    public void setNotes(Set<Notes> notes) {
        if (this.notes != null) {
            this.notes.forEach(i -> i.setPatient(null));
        }
        if (notes != null) {
            notes.forEach(i -> i.setPatient(this));
        }
        this.notes = notes;
    }

    public Medecin notes(Set<Notes> notes) {
        this.setNotes(notes);
        return this;
    }

    public Medecin addNotes(Notes notes) {
        this.notes.add(notes);
        notes.setPatient(this);
        return this;
    }

    public Medecin removeNotes(Notes notes) {
        this.notes.remove(notes);
        notes.setPatient(null);
        return this;
    }

    public Set<Patient> getPatients() {
        return this.patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }

    public Medecin patients(Set<Patient> patients) {
        this.setPatients(patients);
        return this;
    }

    public Medecin addPatients(Patient patient) {
        this.patients.add(patient);
        patient.getMedecins().add(this);
        return this;
    }

    public Medecin removePatients(Patient patient) {
        this.patients.remove(patient);
        patient.getMedecins().remove(this);
        return this;
    }

    public Set<Etablissement> getEtablissements() {
        return this.etablissements;
    }

    public void setEtablissements(Set<Etablissement> etablissements) {
        this.etablissements = etablissements;
    }

    public Medecin etablissements(Set<Etablissement> etablissements) {
        this.setEtablissements(etablissements);
        return this;
    }

    public Medecin addEtablissement(Etablissement etablissement) {
        this.etablissements.add(etablissement);
        etablissement.getMedecins().add(this);
        return this;
    }

    public Medecin removeEtablissement(Etablissement etablissement) {
        this.etablissements.remove(etablissement);
        etablissement.getMedecins().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Medecin)) {
            return false;
        }
        return id != null && id.equals(((Medecin) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Medecin{" +
            "id=" + getId() +
            ", idM=" + getIdM() +
            ", nomM='" + getNomM() + "'" +
            ", prenomM='" + getPrenomM() + "'" +
            "}";
    }
}
