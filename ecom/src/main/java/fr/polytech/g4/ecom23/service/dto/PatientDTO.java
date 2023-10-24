package fr.polytech.g4.ecom23.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link fr.polytech.g4.ecom23.domain.Patient} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PatientDTO implements Serializable {

    private Long id;

    @NotNull
    private Long idP;

    private String nomP;

    private String prenomP;

    private Integer age;

    private LocalDate datearrivee;

    private Float poidsactuel;

    private Float albumine;

    private Float taille;

    private AlerteDTO alerte;

    private EtablissementDTO infrastructure;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdP() {
        return idP;
    }

    public void setIdP(Long idP) {
        this.idP = idP;
    }

    public String getNomP() {
        return nomP;
    }

    public void setNomP(String nomP) {
        this.nomP = nomP;
    }

    public String getPrenomP() {
        return prenomP;
    }

    public void setPrenomP(String prenomP) {
        this.prenomP = prenomP;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getDatearrivee() {
        return datearrivee;
    }

    public void setDatearrivee(LocalDate datearrivee) {
        this.datearrivee = datearrivee;
    }

    public Float getPoidsactuel() {
        return poidsactuel;
    }

    public void setPoidsactuel(Float poidsactuel) {
        this.poidsactuel = poidsactuel;
    }

    public Float getAlbumine() {
        return albumine;
    }

    public void setAlbumine(Float albumine) {
        this.albumine = albumine;
    }

    public Float getTaille() {
        return taille;
    }

    public void setTaille(Float taille) {
        this.taille = taille;
    }

    public AlerteDTO getAlerte() {
        return alerte;
    }

    public void setAlerte(AlerteDTO alerte) {
        this.alerte = alerte;
    }

    public EtablissementDTO getInfrastructure() {
        return infrastructure;
    }

    public void setInfrastructure(EtablissementDTO infrastructure) {
        this.infrastructure = infrastructure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientDTO)) {
            return false;
        }

        PatientDTO patientDTO = (PatientDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, patientDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientDTO{" +
            "id=" + getId() +
            ", idP=" + getIdP() +
            ", nomP='" + getNomP() + "'" +
            ", prenomP='" + getPrenomP() + "'" +
            ", age=" + getAge() +
            ", datearrivee='" + getDatearrivee() + "'" +
            ", poidsactuel=" + getPoidsactuel() +
            ", albumine=" + getAlbumine() +
            ", taille=" + getTaille() +
            ", alerte=" + getAlerte() +
            ", infrastructure=" + getInfrastructure() +
            "}";
    }
}
