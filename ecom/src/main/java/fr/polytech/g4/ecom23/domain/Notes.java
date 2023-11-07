package fr.polytech.g4.ecom23.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Notes.
 */
@Entity
@Table(name = "notes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Notes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "commentaire")
    private String commentaire;

    @ManyToOne
    @JsonIgnoreProperties(value = { "compte", "taches", "alertes", "notes", "patients", "etablissements" }, allowSetters = true)
    private Medecin patient;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "alerte", "notes", "etablissement", "suividonnees", "taches", "medecins", "soignants" },
        allowSetters = true
    )
    private Patient medecin;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Notes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentaire() {
        return this.commentaire;
    }

    public Notes commentaire(String commentaire) {
        this.setCommentaire(commentaire);
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Medecin getPatient() {
        return this.patient;
    }

    public void setPatient(Medecin medecin) {
        this.patient = medecin;
    }

    public Notes patient(Medecin medecin) {
        this.setPatient(medecin);
        return this;
    }

    public Patient getMedecin() {
        return this.medecin;
    }

    public void setMedecin(Patient patient) {
        this.medecin = patient;
    }

    public Notes medecin(Patient patient) {
        this.setMedecin(patient);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notes)) {
            return false;
        }
        return id != null && id.equals(((Notes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Notes{" +
            "id=" + getId() +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
