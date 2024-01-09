package fr.polytech.g4.ecom23.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.polytech.g4.ecom23.domain.Notes} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotesDTO implements Serializable {

    private Long id;

    private String commentaire;

    private MedecinDTO medecin;

    private PatientDTO patient;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public MedecinDTO getMedecin() {
        return medecin;
    }

    public void setMedecin(MedecinDTO medecin) {
        this.medecin = medecin;
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotesDTO)) {
            return false;
        }

        NotesDTO notesDTO = (NotesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, notesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotesDTO{" +
            "id=" + getId() +
            ", commentaire='" + getCommentaire() + "'" +
            ", medecin=" + getMedecin() +
            ", patient=" + getPatient() +
            "}";
    }
}
