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
 * A Etablissement.
 */
@Entity
@Table(name = "etablissement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Etablissement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_e", nullable = false, unique = true)
    private Long idE;

    @Column(name = "nom_e")
    private String nomE;

    @Column(name = "adresse")
    private String adresse;

    @Size(min = 10, max = 14)
    @Column(name = "telephone", length = 14)
    private String telephone;

    @OneToMany(mappedBy = "infrastructure")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "alerte", "notes", "infrastructure", "suividonnees", "taches", "medecins", "soignants" },
        allowSetters = true
    )
    private Set<Patient> patients = new HashSet<>();

    @ManyToMany(mappedBy = "etablissements")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "compte", "taches", "alertes", "notes", "patients", "etablissements" }, allowSetters = true)
    private Set<Medecin> medecins = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Etablissement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdE() {
        return this.idE;
    }

    public Etablissement idE(Long idE) {
        this.setIdE(idE);
        return this;
    }

    public void setIdE(Long idE) {
        this.idE = idE;
    }

    public String getNomE() {
        return this.nomE;
    }

    public Etablissement nomE(String nomE) {
        this.setNomE(nomE);
        return this;
    }

    public void setNomE(String nomE) {
        this.nomE = nomE;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Etablissement adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Etablissement telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Set<Patient> getPatients() {
        return this.patients;
    }

    public void setPatients(Set<Patient> patients) {
        if (this.patients != null) {
            this.patients.forEach(i -> i.setInfrastructure(null));
        }
        if (patients != null) {
            patients.forEach(i -> i.setInfrastructure(this));
        }
        this.patients = patients;
    }

    public Etablissement patients(Set<Patient> patients) {
        this.setPatients(patients);
        return this;
    }

    public Etablissement addPatient(Patient patient) {
        this.patients.add(patient);
        patient.setInfrastructure(this);
        return this;
    }

    public Etablissement removePatient(Patient patient) {
        this.patients.remove(patient);
        patient.setInfrastructure(null);
        return this;
    }

    public Set<Medecin> getMedecins() {
        return this.medecins;
    }

    public void setMedecins(Set<Medecin> medecins) {
        if (this.medecins != null) {
            this.medecins.forEach(i -> i.removeEtablissement(this));
        }
        if (medecins != null) {
            medecins.forEach(i -> i.addEtablissement(this));
        }
        this.medecins = medecins;
    }

    public Etablissement medecins(Set<Medecin> medecins) {
        this.setMedecins(medecins);
        return this;
    }

    public Etablissement addMedecin(Medecin medecin) {
        this.medecins.add(medecin);
        medecin.getEtablissements().add(this);
        return this;
    }

    public Etablissement removeMedecin(Medecin medecin) {
        this.medecins.remove(medecin);
        medecin.getEtablissements().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Etablissement)) {
            return false;
        }
        return id != null && id.equals(((Etablissement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Etablissement{" +
            "id=" + getId() +
            ", idE=" + getIdE() +
            ", nomE='" + getNomE() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", telephone='" + getTelephone() + "'" +
            "}";
    }
}
