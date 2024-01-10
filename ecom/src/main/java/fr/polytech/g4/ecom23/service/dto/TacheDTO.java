package fr.polytech.g4.ecom23.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link fr.polytech.g4.ecom23.domain.Tache} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TacheDTO implements Serializable {

    private Long id;

    private LocalDate date;

    private String commentaire;

    private Boolean effectuee;

    private PatientDTO patient;

    private ServicesoignantDTO servicesoignant;

    private SoignantDTO soignant;

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

    public Boolean getEffectuee() {
        return effectuee;
    }

    public void setEffectuee(Boolean effectuee) {
        this.effectuee = effectuee;
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }

    public ServicesoignantDTO getServicesoignant() {
        return servicesoignant;
    }

    public void setServicesoignant(ServicesoignantDTO servicesoignant) {
        this.servicesoignant = servicesoignant;
    }

    public SoignantDTO getSoignant() {
        return soignant;
    }

    public void setSoignant(SoignantDTO soignant) {
        this.soignant = soignant;
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
        if (!(o instanceof TacheDTO)) {
            return false;
        }

        TacheDTO tacheDTO = (TacheDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tacheDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TacheDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", effectuee='" + getEffectuee() + "'" +
            ", patient=" + getPatient() +
            ", servicesoignant=" + getServicesoignant() +
            ", soignant=" + getSoignant() +
            ", medecin=" + getMedecin() +
            "}";
    }
}
