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

    @NotNull
    private Long id;

    private String nom;

    private String prenom;

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
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", metier='" + getMetier() + "'" +
            ", compte=" + getCompte() +
            ", servicesoignant=" + getServicesoignant() +
            ", patients=" + getPatients() +
            "}";
    }
}
