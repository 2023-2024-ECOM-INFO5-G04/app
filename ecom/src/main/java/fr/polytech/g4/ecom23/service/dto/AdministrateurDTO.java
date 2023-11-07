package fr.polytech.g4.ecom23.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link fr.polytech.g4.ecom23.domain.Administrateur} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdministrateurDTO implements Serializable {

    @NotNull
    private Long id;

    private String nom;

    private String prenom;

    private CompteDTO compte;

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

    public CompteDTO getCompte() {
        return compte;
    }

    public void setCompte(CompteDTO compte) {
        this.compte = compte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdministrateurDTO)) {
            return false;
        }

        AdministrateurDTO administrateurDTO = (AdministrateurDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, administrateurDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdministrateurDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", compte=" + getCompte() +
            "}";
    }
}
