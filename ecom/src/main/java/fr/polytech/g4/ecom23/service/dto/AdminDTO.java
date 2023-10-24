package fr.polytech.g4.ecom23.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link fr.polytech.g4.ecom23.domain.Admin} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdminDTO implements Serializable {

    private Long id;

    @NotNull
    private Long idA;

    private String nomA;

    private String prenomA;

    private CompteDTO compte;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdA() {
        return idA;
    }

    public void setIdA(Long idA) {
        this.idA = idA;
    }

    public String getNomA() {
        return nomA;
    }

    public void setNomA(String nomA) {
        this.nomA = nomA;
    }

    public String getPrenomA() {
        return prenomA;
    }

    public void setPrenomA(String prenomA) {
        this.prenomA = prenomA;
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
        if (!(o instanceof AdminDTO)) {
            return false;
        }

        AdminDTO adminDTO = (AdminDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, adminDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminDTO{" +
            "id=" + getId() +
            ", idA=" + getIdA() +
            ", nomA='" + getNomA() + "'" +
            ", prenomA='" + getPrenomA() + "'" +
            ", compte=" + getCompte() +
            "}";
    }
}
