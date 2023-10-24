package fr.polytech.g4.ecom23.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Rappel.
 */
@Entity
@Table(name = "rappel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Rappel implements Serializable {

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

    @Column(name = "effectue")
    private Boolean effectue;

    @ManyToOne
    @JsonIgnoreProperties(value = { "compte", "taches", "alertes", "notes", "patients", "etablissements" }, allowSetters = true)
    private Medecin medecin;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Rappel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Rappel date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCommentaire() {
        return this.commentaire;
    }

    public Rappel commentaire(String commentaire) {
        this.setCommentaire(commentaire);
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Boolean getEffectue() {
        return this.effectue;
    }

    public Rappel effectue(Boolean effectue) {
        this.setEffectue(effectue);
        return this;
    }

    public void setEffectue(Boolean effectue) {
        this.effectue = effectue;
    }

    public Medecin getMedecin() {
        return this.medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }

    public Rappel medecin(Medecin medecin) {
        this.setMedecin(medecin);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rappel)) {
            return false;
        }
        return id != null && id.equals(((Rappel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rappel{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", effectue='" + getEffectue() + "'" +
            "}";
    }
}
