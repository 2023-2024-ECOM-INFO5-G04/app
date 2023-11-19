package fr.polytech.g4.ecom23.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.polytech.g4.ecom23.domain.enumeration.Role;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Compte.
 */
@Entity
@Table(name = "compte")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Compte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nomutilisateur")
    private String nomutilisateur;

    @Column(name = "motdepasse")
    private String motdepasse;

    @Column(name = "mail")
    private String mail;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @JsonIgnoreProperties(value = { "compte", "servicesoignant", "patients", "taches" }, allowSetters = true)
    @OneToOne(mappedBy = "compte")
    private Soignant soignant;

    @JsonIgnoreProperties(value = { "compte", "taches", "alertes", "notes", "patients", "etablissements" }, allowSetters = true)
    @OneToOne(mappedBy = "compte")
    private Medecin medecin;

    @JsonIgnoreProperties(value = { "compte" }, allowSetters = true)
    @OneToOne(mappedBy = "compte")
    private Administrateur administrateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Compte id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomutilisateur() {
        return this.nomutilisateur;
    }

    public Compte nomutilisateur(String nomutilisateur) {
        this.setNomutilisateur(nomutilisateur);
        return this;
    }

    public void setNomutilisateur(String nomutilisateur) {
        this.nomutilisateur = nomutilisateur;
    }

    public String getMotdepasse() {
        return this.motdepasse;
    }

    public Compte motdepasse(String motdepasse) {
        this.setMotdepasse(motdepasse);
        return this;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public String getMail() {
        return this.mail;
    }

    public Compte mail(String mail) {
        this.setMail(mail);
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Role getRole() {
        return this.role;
    }

    public Compte role(Role role) {
        this.setRole(role);
        return this;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Soignant getSoignant() {
        return this.soignant;
    }

    public void setSoignant(Soignant soignant) {
        if (this.soignant != null) {
            this.soignant.setCompte(null);
        }
        if (soignant != null) {
            soignant.setCompte(this);
        }
        this.soignant = soignant;
    }

    public Compte soignant(Soignant soignant) {
        this.setSoignant(soignant);
        return this;
    }

    public Medecin getMedecin() {
        return this.medecin;
    }

    public void setMedecin(Medecin medecin) {
        if (this.medecin != null) {
            this.medecin.setCompte(null);
        }
        if (medecin != null) {
            medecin.setCompte(this);
        }
        this.medecin = medecin;
    }

    public Compte medecin(Medecin medecin) {
        this.setMedecin(medecin);
        return this;
    }

    public Administrateur getAdministrateur() {
        return this.administrateur;
    }

    public void setAdministrateur(Administrateur administrateur) {
        if (this.administrateur != null) {
            this.administrateur.setCompte(null);
        }
        if (administrateur != null) {
            administrateur.setCompte(this);
        }
        this.administrateur = administrateur;
    }

    public Compte administrateur(Administrateur administrateur) {
        this.setAdministrateur(administrateur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Compte)) {
            return false;
        }
        return id != null && id.equals(((Compte) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Compte{" +
            "id=" + getId() +
            ", nomutilisateur='" + getNomutilisateur() + "'" +
            ", motdepasse='" + getMotdepasse() + "'" +
            ", mail='" + getMail() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }
}
