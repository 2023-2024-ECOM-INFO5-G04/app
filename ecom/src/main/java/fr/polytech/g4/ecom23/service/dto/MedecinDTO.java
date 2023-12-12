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

    @NotNull
    private Long id;

    private String nom;

    private String prenom;

    private UserDTO user;

    private Set<PatientDTO> patients = new HashSet<>();

    private Set<EtablissementDTO> etablissements = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
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
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", user=" + getUser() +
            ", patients=" + getPatients() +
            ", etablissements=" + getEtablissements() +
            "}";
    }
}
