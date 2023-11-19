package fr.polytech.g4.ecom23.service.dto;

import fr.polytech.g4.ecom23.domain.enumeration.Role;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.polytech.g4.ecom23.domain.Compte} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompteDTO implements Serializable {

    private Long id;

    private String nomutilisateur;

    private String motdepasse;

    private String mail;

    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomutilisateur() {
        return nomutilisateur;
    }

    public void setNomutilisateur(String nomutilisateur) {
        this.nomutilisateur = nomutilisateur;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompteDTO)) {
            return false;
        }

        CompteDTO compteDTO = (CompteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, compteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompteDTO{" +
            "id=" + getId() +
            ", nomutilisateur='" + getNomutilisateur() + "'" +
            ", motdepasse='" + getMotdepasse() + "'" +
            ", mail='" + getMail() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }
}
