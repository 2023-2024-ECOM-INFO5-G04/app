package fr.polytech.g4.ecom23.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link fr.polytech.g4.ecom23.domain.Alerte} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AlerteDTO implements Serializable {

    private Long id;

    private LocalDate date;

    private String commentaire;

    private Boolean denutrition;

    private Boolean severite;

    private Boolean consulte;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Boolean getDenutrition() {
        return denutrition;
    }

    public void setDenutrition(Boolean denutrition) {
        this.denutrition = denutrition;
    }

    public Boolean getSeverite() {
        return severite;
    }

    public void setSeverite(Boolean severite) {
        this.severite = severite;
    }

    public Boolean getConsulte() {
        return consulte;
    }

    public void setConsulte(Boolean consulte) {
        this.consulte = consulte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlerteDTO)) {
            return false;
        }

        AlerteDTO alerteDTO = (AlerteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, alerteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlerteDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", denutrition='" + getDenutrition() + "'" +
            ", severite='" + getSeverite() + "'" +
            ", consulte='" + getConsulte() + "'" +
            "}";
    }
}
