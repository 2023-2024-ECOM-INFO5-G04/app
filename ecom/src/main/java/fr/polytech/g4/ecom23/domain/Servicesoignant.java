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
 * A Servicesoignant.
 */
@Entity
@Table(name = "servicesoignant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Servicesoignant implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "nbsoignants")
    private String nbsoignants;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "patients", "medecins" }, allowSetters = true)
    private Etablissement etablissement;

    @OneToMany(mappedBy = "servicesoignant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "servicesoignant", "patients", "taches" }, allowSetters = true)
    private Set<Soignant> soignants = new HashSet<>();

    @OneToMany(mappedBy = "servicesoignant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "patient", "servicesoignant", "soigant", "medecin" }, allowSetters = true)
    private Set<Tache> taches = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Servicesoignant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public Servicesoignant type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNbsoignants() {
        return this.nbsoignants;
    }

    public Servicesoignant nbsoignants(String nbsoignants) {
        this.setNbsoignants(nbsoignants);
        return this;
    }

    public void setNbsoignants(String nbsoignants) {
        this.nbsoignants = nbsoignants;
    }

    public Etablissement getEtablissement() {
        return this.etablissement;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

    public Servicesoignant etablissement(Etablissement etablissement) {
        this.setEtablissement(etablissement);
        return this;
    }

    public Set<Soignant> getSoignants() {
        return this.soignants;
    }

    public void setSoignants(Set<Soignant> soignants) {
        if (this.soignants != null) {
            this.soignants.forEach(i -> i.setServicesoignant(null));
        }
        if (soignants != null) {
            soignants.forEach(i -> i.setServicesoignant(this));
        }
        this.soignants = soignants;
    }

    public Servicesoignant soignants(Set<Soignant> soignants) {
        this.setSoignants(soignants);
        return this;
    }

    public Servicesoignant addSoignant(Soignant soignant) {
        this.soignants.add(soignant);
        soignant.setServicesoignant(this);
        return this;
    }

    public Servicesoignant removeSoignant(Soignant soignant) {
        this.soignants.remove(soignant);
        soignant.setServicesoignant(null);
        return this;
    }

    public Set<Tache> getTaches() {
        return this.taches;
    }

    public void setTaches(Set<Tache> taches) {
        if (this.taches != null) {
            this.taches.forEach(i -> i.setServicesoignant(null));
        }
        if (taches != null) {
            taches.forEach(i -> i.setServicesoignant(this));
        }
        this.taches = taches;
    }

    public Servicesoignant taches(Set<Tache> taches) {
        this.setTaches(taches);
        return this;
    }

    public Servicesoignant addTaches(Tache tache) {
        this.taches.add(tache);
        tache.setServicesoignant(this);
        return this;
    }

    public Servicesoignant removeTaches(Tache tache) {
        this.taches.remove(tache);
        tache.setServicesoignant(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Servicesoignant)) {
            return false;
        }
        return id != null && id.equals(((Servicesoignant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Servicesoignant{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", nbsoignants='" + getNbsoignants() + "'" +
            "}";
    }
}
