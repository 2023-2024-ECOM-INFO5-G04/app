package fr.polytech.g4.ecom23.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link fr.polytech.g4.ecom23.domain.Servicesoignant} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServicesoignantDTO implements Serializable {

    @NotNull
    private Long id;

    private String type;

    private String nbsoignants;

    private EtablissementDTO etablissement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNbsoignants() {
        return nbsoignants;
    }

    public void setNbsoignants(String nbsoignants) {
        this.nbsoignants = nbsoignants;
    }

    public EtablissementDTO getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(EtablissementDTO etablissement) {
        this.etablissement = etablissement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServicesoignantDTO)) {
            return false;
        }

        ServicesoignantDTO servicesoignantDTO = (ServicesoignantDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, servicesoignantDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServicesoignantDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", nbsoignants='" + getNbsoignants() + "'" +
            ", etablissement=" + getEtablissement() +
            "}";
    }
}
