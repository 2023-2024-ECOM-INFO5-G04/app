package fr.polytech.g4.ecom23.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link fr.polytech.g4.ecom23.domain.Suividonnees} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SuividonneesDTO implements Serializable, Comparable<SuividonneesDTO> {

    @NotNull
    private Long id;

    @NotNull
    private LocalDate date;

    private Float poids;

    private Float epa;

    private Float massecorporelle;

    private Float quantitepoidsaliments;

    private Float quantitecaloriesaliments;

    private Boolean absorptionreduite;

    private Boolean agression;

    private PatientDTO patient;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Float getPoids() {
        return poids;
    }

    public void setPoids(Float poids) {
        this.poids = poids;
    }

    public Float getEpa() {
        return epa;
    }

    public void setEpa(Float epa) {
        this.epa = epa;
    }

    public Float getMassecorporelle() {
        return massecorporelle;
    }

    public void setMassecorporelle(Float massecorporelle) {
        this.massecorporelle = massecorporelle;
    }

    public Float getQuantitepoidsaliments() {
        return quantitepoidsaliments;
    }

    public void setQuantitepoidsaliments(Float quantitepoidsaliments) {
        this.quantitepoidsaliments = quantitepoidsaliments;
    }

    public Float getQuantitecaloriesaliments() {
        return quantitecaloriesaliments;
    }

    public void setQuantitecaloriesaliments(Float quantitecaloriesaliments) {
        this.quantitecaloriesaliments = quantitecaloriesaliments;
    }

    public Boolean getAbsorptionreduite() {
        return absorptionreduite;
    }

    public void setAbsorptionreduite(Boolean absorptionreduite) {
        this.absorptionreduite = absorptionreduite;
    }

    public Boolean getAgression() {
        return agression;
    }

    public void setAgression(Boolean agression) {
        this.agression = agression;
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SuividonneesDTO)) {
            return false;
        }

        SuividonneesDTO suividonneesDTO = (SuividonneesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, suividonneesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SuividonneesDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", poids=" + getPoids() +
            ", epa=" + getEpa() +
            ", massecorporelle=" + getMassecorporelle() +
            ", quantitepoidsaliments=" + getQuantitepoidsaliments() +
            ", quantitecaloriesaliments=" + getQuantitecaloriesaliments() +
            ", absorptionreduite='" + getAbsorptionreduite() + "'" +
            ", agression='" + getAgression() + "'" +
            ", patient=" + getPatient() +
            "}";
    }

    @Override
    public int compareTo(SuividonneesDTO suividonneesDTO) {
        return - suividonneesDTO.getDate().compareTo(date);
    }
}
