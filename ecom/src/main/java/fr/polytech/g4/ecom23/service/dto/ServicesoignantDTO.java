package fr.polytech.g4.ecom23.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link fr.polytech.g4.ecom23.domain.Servicesoignant} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServicesoignantDTO implements Serializable {

    private Long id;

    @NotNull
    private Long idSer;

    private String type;

    private String nbsoignants;

    private EtablissementDTO infrastructure;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdSer() {
        return idSer;
    }

    public void setIdSer(Long idSer) {
        this.idSer = idSer;
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
            ", idSer=" + getIdSer() +
            ", type='" + getType() + "'" +
            ", nbsoignants='" + getNbsoignants() + "'" +
            ", infrastructure=" + getInfrastructure() +
            "}";
    }
}
