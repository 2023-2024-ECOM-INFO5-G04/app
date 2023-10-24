package fr.polytech.g4.ecom23.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Admin.
 */
@Entity
@Table(name = "admin")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_a", nullable = false, unique = true)
    private Long idA;

    @Column(name = "nom_a")
    private String nomA;

    @Column(name = "prenom_a")
    private String prenomA;

    @JsonIgnoreProperties(value = { "soignant", "medecin", "admin" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Compte compte;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Admin id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdA() {
        return this.idA;
    }

    public Admin idA(Long idA) {
        this.setIdA(idA);
        return this;
    }

    public void setIdA(Long idA) {
        this.idA = idA;
    }

    public String getNomA() {
        return this.nomA;
    }

    public Admin nomA(String nomA) {
        this.setNomA(nomA);
        return this;
    }

    public void setNomA(String nomA) {
        this.nomA = nomA;
    }

    public String getPrenomA() {
        return this.prenomA;
    }

    public Admin prenomA(String prenomA) {
        this.setPrenomA(prenomA);
        return this;
    }

    public void setPrenomA(String prenomA) {
        this.prenomA = prenomA;
    }

    public Compte getCompte() {
        return this.compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public Admin compte(Compte compte) {
        this.setCompte(compte);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Admin)) {
            return false;
        }
        return id != null && id.equals(((Admin) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Admin{" +
            "id=" + getId() +
            ", idA=" + getIdA() +
            ", nomA='" + getNomA() + "'" +
            ", prenomA='" + getPrenomA() + "'" +
            "}";
    }
}
