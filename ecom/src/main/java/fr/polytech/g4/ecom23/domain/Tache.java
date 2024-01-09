package fr.polytech.g4.ecom23.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Tache.
 */
@Entity
@Table(name = "tache")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tache implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "commentaire")
    private String commentaire;

    @Column(name = "effectuee")
    private Boolean effectuee;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "alerte", "notes", "etablissement", "suividonnees", "taches", "medecins", "soignants" },
        allowSetters = true
    )
    private Patient patient;

    @ManyToOne
    @JsonIgnoreProperties(value = { "etablissement", "soignants", "taches" }, allowSetters = true)
    private Servicesoignant servicesoignant;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "servicesoignant", "patients", "taches" }, allowSetters = true)
    private Soignant soigant;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "taches", "alertes", "notes", "patients", "etablissements" }, allowSetters = true)
    private Medecin medecin;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tache id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Tache date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCommentaire() {
        return this.commentaire;
    }

    public Tache commentaire(String commentaire) {
        this.setCommentaire(commentaire);
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Boolean getEffectuee() {
        return this.effectuee;
    }

    public Tache effectuee(Boolean effectuee) {
        this.setEffectuee(effectuee);
        return this;
    }

    public void setEffectuee(Boolean effectuee) {
        this.effectuee = effectuee;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Tache patient(Patient patient) {
        this.setPatient(patient);
        return this;
    }

    public Servicesoignant getServicesoignant() {
        return this.servicesoignant;
    }

    public void setServicesoignant(Servicesoignant servicesoignant) {
        this.servicesoignant = servicesoignant;
    }

    public Tache servicesoignant(Servicesoignant servicesoignant) {
        this.setServicesoignant(servicesoignant);
        return this;
    }

    public Soignant getSoigant() {
        return this.soigant;
    }

    public void setSoigant(Soignant soignant) {
        this.soigant = soignant;
    }

    public Tache soigant(Soignant soignant) {
        this.setSoigant(soignant);
        return this;
    }

    public Medecin getMedecin() {
        return this.medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }

    public Tache medecin(Medecin medecin) {
        this.setMedecin(medecin);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tache)) {
            return false;
        }
        return id != null && id.equals(((Tache) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tache{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", effectuee='" + getEffectuee() + "'" +
            "}";
    }
}
