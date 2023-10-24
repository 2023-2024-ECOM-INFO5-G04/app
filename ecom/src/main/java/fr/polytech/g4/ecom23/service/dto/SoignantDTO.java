package fr.polytech.g4.ecom23.service.dto;

import fr.polytech.g4.ecom23.domain.enumeration.SoignantMetier;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link fr.polytech.g4.ecom23.domain.Soignant} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SoignantDTO implements Serializable {

    private Long id;

    @NotNull
    private Long idS;

    private String nomS;

    private String prenomS;

    private SoignantMetier metier;

    private CompteDTO compte;

    private ServicesoignantDTO servicesoignant;

    private Set<PatientDTO> patients = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdS() {
        return idS;
    }

    public void setIdS(Long idS) {
        this.idS = idS;
    }

    public String getNomS() {
        return nomS;
    }

    public void setNomS(String nomS) {
        this.nomS = nomS;
    }

    public String getPrenomS() {
        return prenomS;
    }

    public void setPrenomS(String prenomS) {
        this.prenomS = prenomS;
    }

    public SoignantMetier getMetier() {
        return metier;
    }

    public void setMetier(SoignantMetier metier) {
        this.metier = metier;
    }

    public CompteDTO getCompte() {
        return compte;
    }

    public void setCompte(CompteDTO compte) {
        this.compte = compte;
    }

    public ServicesoignantDTO getServicesoignant() {
        return servicesoignant;
    }

    public void setServicesoignant(ServicesoignantDTO servicesoignant) {
        this.servicesoignant = servicesoignant;
    }

    public Set<PatientDTO> getPatients() {
        return patients;
    }

    public void setPatients(Set<PatientDTO> patients) {
        this.patients = patients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SoignantDTO)) {
            return false;
        }

        SoignantDTO soignantDTO = (SoignantDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, soignantDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SoignantDTO{" +
            "id=" + getId() +
            ", idS=" + getIdS() +
            ", nomS='" + getNomS() + "'" +
            ", prenomS='" + getPrenomS() + "'" +
            ", metier='" + getMetier() + "'" +
            ", compte=" + getCompte() +
            ", servicesoignant=" + getServicesoignant() +
            ", patients=" + getPatients() +
            "}";
    }
}
