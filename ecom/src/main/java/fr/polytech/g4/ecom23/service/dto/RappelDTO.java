package fr.polytech.g4.ecom23.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link fr.polytech.g4.ecom23.domain.Rappel} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RappelDTO implements Serializable {

    private Long id;

    private LocalDate date;

    private String commentaire;

    private Boolean effectue;

    private MedecinDTO medecin;

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

    public Boolean getEffectue() {
        return effectue;
    }

    public void setEffectue(Boolean effectue) {
        this.effectue = effectue;
    }

    public MedecinDTO getMedecin() {
        return medecin;
    }

    public void setMedecin(MedecinDTO medecin) {
        this.medecin = medecin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RappelDTO)) {
            return false;
        }

        RappelDTO rappelDTO = (RappelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rappelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RappelDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", effectue='" + getEffectue() + "'" +
            ", medecin=" + getMedecin() +
            "}";
    }
}
