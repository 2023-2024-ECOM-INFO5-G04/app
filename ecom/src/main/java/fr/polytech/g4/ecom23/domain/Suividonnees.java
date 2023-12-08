package fr.polytech.g4.ecom23.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Suividonnees.
 */
@Entity
@Table(name = "suividonnees")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Suividonnees implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "poids")
    private Float poids;

    @Column(name = "epa")
    private Float epa;

    @Column(name = "massecorporelle")
    private Float massecorporelle;

    @Column(name = "quantitepoidsaliments")
    private Float quantitepoidsaliments;

    @Column(name = "quantitecaloriesaliments")
    private Float quantitecaloriesaliments;

    @Column(name = "absorptionreduite")
    private Boolean absorptionreduite;

    @Column(name = "agression")
    private Boolean agression;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "alerte", "notes", "etablissement", "suividonnees", "taches", "medecins", "soignants" },
        allowSetters = true
    )
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Suividonnees id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Suividonnees date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Float getPoids() {
        return this.poids;
    }

    public Suividonnees poids(Float poids) {
        this.setPoids(poids);
        return this;
    }

    public void setPoids(Float poids) {
        this.poids = poids;
    }

    public Float getEpa() {
        return this.epa;
    }

    public Suividonnees epa(Float epa) {
        this.setEpa(epa);
        return this;
    }

    public void setEpa(Float epa) {
        this.epa = epa;
    }

    public Float getMassecorporelle() {
        return this.massecorporelle;
    }

    public Suividonnees massecorporelle(Float massecorporelle) {
        this.setMassecorporelle(massecorporelle);
        return this;
    }

    public void setMassecorporelle(Float massecorporelle) {
        this.massecorporelle = massecorporelle;
    }

    public Float getQuantitepoidsaliments() {
        return this.quantitepoidsaliments;
    }

    public Suividonnees quantitepoidsaliments(Float quantitepoidsaliments) {
        this.setQuantitepoidsaliments(quantitepoidsaliments);
        return this;
    }

    public void setQuantitepoidsaliments(Float quantitepoidsaliments) {
        this.quantitepoidsaliments = quantitepoidsaliments;
    }

    public Float getQuantitecaloriesaliments() {
        return this.quantitecaloriesaliments;
    }

    public Suividonnees quantitecaloriesaliments(Float quantitecaloriesaliments) {
        this.setQuantitecaloriesaliments(quantitecaloriesaliments);
        return this;
    }

    public void setQuantitecaloriesaliments(Float quantitecaloriesaliments) {
        this.quantitecaloriesaliments = quantitecaloriesaliments;
    }

    public Boolean getAbsorptionreduite() {
        return this.absorptionreduite;
    }

    public Suividonnees absorptionreduite(Boolean absorptionreduite) {
        this.setAbsorptionreduite(absorptionreduite);
        return this;
    }

    public void setAbsorptionreduite(Boolean absorptionreduite) {
        this.absorptionreduite = absorptionreduite;
    }

    public Boolean getAgression() {
        return this.agression;
    }

    public Suividonnees agression(Boolean agression) {
        this.setAgression(agression);
        return this;
    }

    public void setAgression(Boolean agression) {
        this.agression = agression;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Suividonnees patient(Patient patient) {
        this.setPatient(patient);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Suividonnees)) {
            return false;
        }
        return id != null && id.equals(((Suividonnees) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Suividonnees{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", poids=" + getPoids() +
            ", epa=" + getEpa() +
            ", massecorporelle=" + getMassecorporelle() +
            ", quantitepoidsaliments=" + getQuantitepoidsaliments() +
            ", quantitecaloriesaliments=" + getQuantitecaloriesaliments() +
            ", absorptionreduite='" + getAbsorptionreduite() + "'" +
            ", agression='" + getAgression() + "'" +
            "}";
    }
}
