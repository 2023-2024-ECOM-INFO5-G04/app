package fr.polytech.g4.ecom23.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Alerte.
 */
@Entity
@Table(name = "alerte")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Alerte implements Serializable {

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

    @Column(name = "denutrition")
    private Boolean denutrition;

    @JsonIgnoreProperties(
        value = { "alerte", "notes", "infrastructure", "suividonnees", "taches", "medecins", "soignants" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "alerte")
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Alerte id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Alerte date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCommentaire() {
        return this.commentaire;
    }

    public Alerte commentaire(String commentaire) {
        this.setCommentaire(commentaire);
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Boolean getDenutrition() {
        return this.denutrition;
    }

    public Alerte denutrition(Boolean denutrition) {
        this.setDenutrition(denutrition);
        return this;
    }

    public void setDenutrition(Boolean denutrition) {
        this.denutrition = denutrition;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        if (this.patient != null) {
            this.patient.setAlerte(null);
        }
        if (patient != null) {
            patient.setAlerte(this);
        }
        this.patient = patient;
    }

    public Alerte patient(Patient patient) {
        this.setPatient(patient);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Alerte)) {
            return false;
        }
        return id != null && id.equals(((Alerte) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Alerte{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", denutrition='" + getDenutrition() + "'" +
            "}";
    }
}
