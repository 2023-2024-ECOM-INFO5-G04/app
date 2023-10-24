package fr.polytech.g4.ecom23.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link fr.polytech.g4.ecom23.domain.Medecin} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MedecinDTO implements Serializable {

    private Long id;

    @NotNull
    private Long idM;

    private String nomM;

    private String prenomM;

    private CompteDTO compte;

    private Set<PatientDTO> patients = new HashSet<>();

    private Set<EtablissementDTO> etablissements = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdM() {
        return idM;
    }

    public void setIdM(Long idM) {
        this.idM = idM;
    }

    public String getNomM() {
        return nomM;
    }

    public void setNomM(String nomM) {
        this.nomM = nomM;
    }

    public String getPrenomM() {
        return prenomM;
    }

    public void setPrenomM(String prenomM) {
        this.prenomM = prenomM;
    }

    public CompteDTO getCompte() {
        return compte;
    }

    public void setCompte(CompteDTO compte) {
        this.compte = compte;
    }

    public Set<PatientDTO> getPatients() {
        return patients;
    }

    public void setPatients(Set<PatientDTO> patients) {
        this.patients = patients;
    }

    public Set<EtablissementDTO> getEtablissements() {
        return etablissements;
    }

    public void setEtablissements(Set<EtablissementDTO> etablissements) {
        this.etablissements = etablissements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedecinDTO)) {
            return false;
        }

        MedecinDTO medecinDTO = (MedecinDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, medecinDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MedecinDTO{" +
            "id=" + getId() +
            ", idM=" + getIdM() +
            ", nomM='" + getNomM() + "'" +
            ", prenomM='" + getPrenomM() + "'" +
            ", compte=" + getCompte() +
            ", patients=" + getPatients() +
            ", etablissements=" + getEtablissements() +
            "}";
    }
}
