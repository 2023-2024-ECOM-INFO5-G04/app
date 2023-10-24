package fr.polytech.g4.ecom23.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Patient.
 */
@Entity
@Table(name = "patient")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_p", nullable = false, unique = true)
    private Long idP;

    @Column(name = "nom_p")
    private String nomP;

    @Column(name = "prenom_p")
    private String prenomP;

    @Column(name = "age")
    private Integer age;

    @Column(name = "datearrivee")
    private LocalDate datearrivee;

    @Column(name = "poidsactuel")
    private Float poidsactuel;

    @Column(name = "albumine")
    private Float albumine;

    @Column(name = "taille")
    private Float taille;

    @JsonIgnoreProperties(value = { "patient" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Alerte alerte;

    @OneToMany(mappedBy = "medecin")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "patient", "medecin" }, allowSetters = true)
    private Set<Notes> notes = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "patients", "medecins" }, allowSetters = true)
    private Etablissement infrastructure;

    @OneToMany(mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "patient" }, allowSetters = true)
    private Set<Suividonnees> suividonnees = new HashSet<>();

    @OneToMany(mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "patient", "servicesoignant", "soigant", "medecin" }, allowSetters = true)
    private Set<Tache> taches = new HashSet<>();

    @ManyToMany(mappedBy = "patients")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "compte", "taches", "alertes", "notes", "patients", "etablissements" }, allowSetters = true)
    private Set<Medecin> medecins = new HashSet<>();

    @ManyToMany(mappedBy = "patients")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "compte", "servicesoignant", "patients", "taches" }, allowSetters = true)
    private Set<Soignant> soignants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Patient id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdP() {
        return this.idP;
    }

    public Patient idP(Long idP) {
        this.setIdP(idP);
        return this;
    }

    public void setIdP(Long idP) {
        this.idP = idP;
    }

    public String getNomP() {
        return this.nomP;
    }

    public Patient nomP(String nomP) {
        this.setNomP(nomP);
        return this;
    }

    public void setNomP(String nomP) {
        this.nomP = nomP;
    }

    public String getPrenomP() {
        return this.prenomP;
    }

    public Patient prenomP(String prenomP) {
        this.setPrenomP(prenomP);
        return this;
    }

    public void setPrenomP(String prenomP) {
        this.prenomP = prenomP;
    }

    public Integer getAge() {
        return this.age;
    }

    public Patient age(Integer age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getDatearrivee() {
        return this.datearrivee;
    }

    public Patient datearrivee(LocalDate datearrivee) {
        this.setDatearrivee(datearrivee);
        return this;
    }

    public void setDatearrivee(LocalDate datearrivee) {
        this.datearrivee = datearrivee;
    }

    public Float getPoidsactuel() {
        return this.poidsactuel;
    }

    public Patient poidsactuel(Float poidsactuel) {
        this.setPoidsactuel(poidsactuel);
        return this;
    }

    public void setPoidsactuel(Float poidsactuel) {
        this.poidsactuel = poidsactuel;
    }

    public Float getAlbumine() {
        return this.albumine;
    }

    public Patient albumine(Float albumine) {
        this.setAlbumine(albumine);
        return this;
    }

    public void setAlbumine(Float albumine) {
        this.albumine = albumine;
    }

    public Float getTaille() {
        return this.taille;
    }

    public Patient taille(Float taille) {
        this.setTaille(taille);
        return this;
    }

    public void setTaille(Float taille) {
        this.taille = taille;
    }

    public Alerte getAlerte() {
        return this.alerte;
    }

    public void setAlerte(Alerte alerte) {
        this.alerte = alerte;
    }

    public Patient alerte(Alerte alerte) {
        this.setAlerte(alerte);
        return this;
    }

    public Set<Notes> getNotes() {
        return this.notes;
    }

    public void setNotes(Set<Notes> notes) {
        if (this.notes != null) {
            this.notes.forEach(i -> i.setMedecin(null));
        }
        if (notes != null) {
            notes.forEach(i -> i.setMedecin(this));
        }
        this.notes = notes;
    }

    public Patient notes(Set<Notes> notes) {
        this.setNotes(notes);
        return this;
    }

    public Patient addNotes(Notes notes) {
        this.notes.add(notes);
        notes.setMedecin(this);
        return this;
    }

    public Patient removeNotes(Notes notes) {
        this.notes.remove(notes);
        notes.setMedecin(null);
        return this;
    }

    public Etablissement getInfrastructure() {
        return this.infrastructure;
    }

    public void setInfrastructure(Etablissement etablissement) {
        this.infrastructure = etablissement;
    }

    public Patient infrastructure(Etablissement etablissement) {
        this.setInfrastructure(etablissement);
        return this;
    }

    public Set<Suividonnees> getSuividonnees() {
        return this.suividonnees;
    }

    public void setSuividonnees(Set<Suividonnees> suividonnees) {
        if (this.suividonnees != null) {
            this.suividonnees.forEach(i -> i.setPatient(null));
        }
        if (suividonnees != null) {
            suividonnees.forEach(i -> i.setPatient(this));
        }
        this.suividonnees = suividonnees;
    }

    public Patient suividonnees(Set<Suividonnees> suividonnees) {
        this.setSuividonnees(suividonnees);
        return this;
    }

    public Patient addSuividonnees(Suividonnees suividonnees) {
        this.suividonnees.add(suividonnees);
        suividonnees.setPatient(this);
        return this;
    }

    public Patient removeSuividonnees(Suividonnees suividonnees) {
        this.suividonnees.remove(suividonnees);
        suividonnees.setPatient(null);
        return this;
    }

    public Set<Tache> getTaches() {
        return this.taches;
    }

    public void setTaches(Set<Tache> taches) {
        if (this.taches != null) {
            this.taches.forEach(i -> i.setPatient(null));
        }
        if (taches != null) {
            taches.forEach(i -> i.setPatient(this));
        }
        this.taches = taches;
    }

    public Patient taches(Set<Tache> taches) {
        this.setTaches(taches);
        return this;
    }

    public Patient addTaches(Tache tache) {
        this.taches.add(tache);
        tache.setPatient(this);
        return this;
    }

    public Patient removeTaches(Tache tache) {
        this.taches.remove(tache);
        tache.setPatient(null);
        return this;
    }

    public Set<Medecin> getMedecins() {
        return this.medecins;
    }

    public void setMedecins(Set<Medecin> medecins) {
        if (this.medecins != null) {
            this.medecins.forEach(i -> i.removePatients(this));
        }
        if (medecins != null) {
            medecins.forEach(i -> i.addPatients(this));
        }
        this.medecins = medecins;
    }

    public Patient medecins(Set<Medecin> medecins) {
        this.setMedecins(medecins);
        return this;
    }

    public Patient addMedecin(Medecin medecin) {
        this.medecins.add(medecin);
        medecin.getPatients().add(this);
        return this;
    }

    public Patient removeMedecin(Medecin medecin) {
        this.medecins.remove(medecin);
        medecin.getPatients().remove(this);
        return this;
    }

    public Set<Soignant> getSoignants() {
        return this.soignants;
    }

    public void setSoignants(Set<Soignant> soignants) {
        if (this.soignants != null) {
            this.soignants.forEach(i -> i.removePatients(this));
        }
        if (soignants != null) {
            soignants.forEach(i -> i.addPatients(this));
        }
        this.soignants = soignants;
    }

    public Patient soignants(Set<Soignant> soignants) {
        this.setSoignants(soignants);
        return this;
    }

    public Patient addSoignants(Soignant soignant) {
        this.soignants.add(soignant);
        soignant.getPatients().add(this);
        return this;
    }

    public Patient removeSoignants(Soignant soignant) {
        this.soignants.remove(soignant);
        soignant.getPatients().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Patient)) {
            return false;
        }
        return id != null && id.equals(((Patient) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Patient{" +
            "id=" + getId() +
            ", idP=" + getIdP() +
            ", nomP='" + getNomP() + "'" +
            ", prenomP='" + getPrenomP() + "'" +
            ", age=" + getAge() +
            ", datearrivee='" + getDatearrivee() + "'" +
            ", poidsactuel=" + getPoidsactuel() +
            ", albumine=" + getAlbumine() +
            ", taille=" + getTaille() +
            "}";
    }
}
