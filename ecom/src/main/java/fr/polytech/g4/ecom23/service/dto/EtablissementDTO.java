package fr.polytech.g4.ecom23.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link fr.polytech.g4.ecom23.domain.Etablissement} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EtablissementDTO implements Serializable {

    private Long id;

    @NotNull
    private Long idE;

    private String nomE;

    private String adresse;

    @Size(min = 10, max = 14)
    private String telephone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdE() {
        return idE;
    }

    public void setIdE(Long idE) {
        this.idE = idE;
    }

    public String getNomE() {
        return nomE;
    }

    public void setNomE(String nomE) {
        this.nomE = nomE;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EtablissementDTO)) {
            return false;
        }

        EtablissementDTO etablissementDTO = (EtablissementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, etablissementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EtablissementDTO{" +
            "id=" + getId() +
            ", idE=" + getIdE() +
            ", nomE='" + getNomE() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", telephone='" + getTelephone() + "'" +
            "}";
    }
}
