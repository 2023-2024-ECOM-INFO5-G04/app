package fr.polytech.g4.ecom23.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.polytech.g4.ecom23.domain.enumeration.SoignantMetier;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Soignant.
 */
@Entity
@Table(name = "soignant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Soignant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_s", nullable = false, unique = true)
    private Long idS;

    @Column(name = "nom_s")
    private String nomS;

    @Column(name = "prenom_s")
    private String prenomS;

    @Enumerated(EnumType.STRING)
    @Column(name = "metier")
    private SoignantMetier metier;

    @JsonIgnoreProperties(value = { "soignant", "medecin", "admin" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Compte compte;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "infrastructure", "soignants", "taches" }, allowSetters = true)
    private Servicesoignant servicesoignant;

    @ManyToMany
    @JoinTable(
        name = "rel_soignant__patients",
        joinColumns = @JoinColumn(name = "soignant_id"),
        inverseJoinColumns = @JoinColumn(name = "patients_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "alerte", "notes", "infrastructure", "suividonnees", "taches", "medecins", "soignants" },
        allowSetters = true
    )
    private Set<Patient> patients = new HashSet<>();

    @OneToMany(mappedBy = "soigant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "patient", "servicesoignant", "soigant", "medecin" }, allowSetters = true)
    private Set<Tache> taches = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Soignant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdS() {
        return this.idS;
    }

    public Soignant idS(Long idS) {
        this.setIdS(idS);
        return this;
    }

    public void setIdS(Long idS) {
        this.idS = idS;
    }

    public String getNomS() {
        return this.nomS;
    }

    public Soignant nomS(String nomS) {
        this.setNomS(nomS);
        return this;
    }

    public void setNomS(String nomS) {
        this.nomS = nomS;
    }

    public String getPrenomS() {
        return this.prenomS;
    }

    public Soignant prenomS(String prenomS) {
        this.setPrenomS(prenomS);
        return this;
    }

    public void setPrenomS(String prenomS) {
        this.prenomS = prenomS;
    }

    public SoignantMetier getMetier() {
        return this.metier;
    }

    public Soignant metier(SoignantMetier metier) {
        this.setMetier(metier);
        return this;
    }

    public void setMetier(SoignantMetier metier) {
        this.metier = metier;
    }

    public Compte getCompte() {
        return this.compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public Soignant compte(Compte compte) {
        this.setCompte(compte);
        return this;
    }

    public Servicesoignant getServicesoignant() {
        return this.servicesoignant;
    }

    public void setServicesoignant(Servicesoignant servicesoignant) {
        this.servicesoignant = servicesoignant;
    }

    public Soignant servicesoignant(Servicesoignant servicesoignant) {
        this.setServicesoignant(servicesoignant);
        return this;
    }

    public Set<Patient> getPatients() {
        return this.patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }

    public Soignant patients(Set<Patient> patients) {
        this.setPatients(patients);
        return this;
    }

    public Soignant addPatients(Patient patient) {
        this.patients.add(patient);
        patient.getSoignants().add(this);
        return this;
    }

    public Soignant removePatients(Patient patient) {
        this.patients.remove(patient);
        patient.getSoignants().remove(this);
        return this;
    }

    public Set<Tache> getTaches() {
        return this.taches;
    }

    public void setTaches(Set<Tache> taches) {
        if (this.taches != null) {
            this.taches.forEach(i -> i.setSoigant(null));
        }
        if (taches != null) {
            taches.forEach(i -> i.setSoigant(this));
        }
        this.taches = taches;
    }

    public Soignant taches(Set<Tache> taches) {
        this.setTaches(taches);
        return this;
    }

    public Soignant addTaches(Tache tache) {
        this.taches.add(tache);
        tache.setSoigant(this);
        return this;
    }

    public Soignant removeTaches(Tache tache) {
        this.taches.remove(tache);
        tache.setSoigant(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Soignant)) {
            return false;
        }
        return id != null && id.equals(((Soignant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Soignant{" +
            "id=" + getId() +
            ", idS=" + getIdS() +
            ", nomS='" + getNomS() + "'" +
            ", prenomS='" + getPrenomS() + "'" +
            ", metier='" + getMetier() + "'" +
            "}";
    }
}
