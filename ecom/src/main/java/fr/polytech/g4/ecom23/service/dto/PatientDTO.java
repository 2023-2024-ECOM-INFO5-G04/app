package fr.polytech.g4.ecom23.service.dto;

import fr.polytech.g4.ecom23.domain.enumeration.Sexe;
import fr.polytech.g4.ecom23.service.SuividonneesService;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link fr.polytech.g4.ecom23.domain.Patient} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PatientDTO implements Serializable {

    private static String COMMENTAIRE = "";

    @NotNull
    private Long id;

    private String nom;

    private String prenom;

    private Integer age;

    private LocalDate datearrivee;

    private Float poidsactuel;

    private Float albumine;

    private Float taille;

    private Sexe sexe;

    private Boolean favori;

    private Boolean sarcopenie;

    private Boolean absorptionreduite;

    private Boolean agression;

    private AlerteDTO alerte;

    private EtablissementDTO etablissement;

    public enum Donnee {
        POIDS, CALORIES
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getDatearrivee() {
        return datearrivee;
    }

    public void setDatearrivee(LocalDate datearrivee) {
        this.datearrivee = datearrivee;
    }

    public Float getPoidsactuel() {
        return poidsactuel;
    }

    public void setPoidsactuel(Float poidsactuel) {
        this.poidsactuel = poidsactuel;
    }

    public Float getAlbumine() {
        return albumine;
    }

    public void setAlbumine(Float albumine) {
        this.albumine = albumine;
    }

    public Float getTaille() {
        return taille;
    }

    public void setTaille(Float taille) {
        this.taille = taille;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public Boolean getFavori() {
        return favori;
    }

    public void setFavori(Boolean favori) {
        this.favori = favori;
    }

    public Boolean getSarcopenie() {
        return sarcopenie;
    }

    public void setSarcopenie(Boolean sarcopenie) {
        this.sarcopenie = sarcopenie;
    }

    public Boolean getAbsorptionreduite() {
        return absorptionreduite;
    }

    public void setAbsorptionreduite(Boolean absorptionreduite) {
        this.absorptionreduite = absorptionreduite;
    }

    public Boolean getAgression() {
        return agression;
    }

    public void setAgression(Boolean agression) {
        this.agression = agression;
    }

    public AlerteDTO getAlerte() {
        return alerte;
    }

    public void setAlerte(AlerteDTO alerte) {
        this.alerte = alerte;
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
        if (!(o instanceof PatientDTO)) {
            return false;
        }

        PatientDTO patientDTO = (PatientDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, patientDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", age=" + getAge() +
            ", datearrivee='" + getDatearrivee() + "'" +
            ", poidsactuel=" + getPoidsactuel() +
            ", albumine=" + getAlbumine() +
            ", taille=" + getTaille() +
            ", sexe='" + getSexe() + "'" +
            ", favori='" + getFavori() + "'" +
            ", sarcopenie='" + getSarcopenie() + "'" +
            ", absorptionreduite='" + getAbsorptionreduite() + "'" +
            ", agression='" + getAgression() + "'" +
            ", alerte=" + getAlerte() +
            ", etablissement=" + getEtablissement() +
            "}";
    }

    public boolean IMC(List<SuividonneesDTO> list, float limit) {
        if (taille == null)
            throw new RuntimeException("Le patient " + id + " n'a pas de taille");
        for (SuividonneesDTO suivi : list) {
            Float poidsActuel = suivi.getPoids();
            if (poidsActuel != null) {
                float IMC = poidsActuel * poidsActuel / taille;
                if (IMC < limit) {
                    COMMENTAIRE += "IMC: " + IMC + ", inférieur à " + limit + "\n";
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    private Float getValue(SuividonneesDTO suivi, Donnee donnee) throws Exception{
        switch (donnee) {
            case POIDS:
                Float poids = suivi.getPoids();
                if (poids != null)
                    return poids;
                break;
            case CALORIES:
                Float quantitecaloriesaliments = suivi.getQuantitecaloriesaliments();
                Float quantitepoidsaliments = suivi.getQuantitepoidsaliments();
                if (quantitecaloriesaliments != null && quantitepoidsaliments != null)
                    return quantitecaloriesaliments * quantitepoidsaliments / 100;
                break;
            default:
                throw new Exception("Invalid data type : " + donnee.toString());
        }
        return null;
    }

    private Float movingAverage(List<SuividonneesDTO> list, Donnee donnee, LocalDate d1, LocalDate d2) {
        List<Float> values = new LinkedList<Float>();
        for (SuividonneesDTO suivi : list) {
            if (!suivi.getDate().isAfter(d2)) {
                if (!suivi.getDate().isBefore(d1)) {
                    try {
                        Float value = getValue(suivi, donnee);
                        if (value != null)
                            values.add(value);
                    } catch (Exception e) {
                        return null;
                    }
                }
                else {
                    break;
                }
            }
        }
        if (values.isEmpty())
            return null;
        Float sum = 0f;
        for (Float value : values) {
            sum += value;
        }
        switch (donnee) {
            case POIDS:
                return sum / values.size();
            case CALORIES:
                return sum;
            default:
                return null;
        }
    }

    public Float evolution(List<SuividonneesDTO> list, Donnee donnee, int days) {
        Float movingAverage_t, movingAverage_t1;
        LocalDate t0 = LocalDate.now();
        LocalDate t1 = LocalDate.ofEpochDay(t0.toEpochDay() - days);
        LocalDate t2 = LocalDate.ofEpochDay(t0.toEpochDay() - 2L * days);
        movingAverage_t = movingAverage(list, donnee, t1, t0);
        if (movingAverage_t == null)
            return null;
        movingAverage_t1 = movingAverage(list, donnee, t2, t1);
        if (movingAverage_t1 == null)
            return null;
        return (movingAverage_t - movingAverage_t1) / movingAverage_t1;
    }

    public boolean weightLoss(List<SuividonneesDTO> list, int days1, float loss1, int days2, float loss2, float loss3) {
        boolean weightLoss = false;
        Float evo1 = evolution(list, Donnee.POIDS, days1);
        Float evo2 = evolution(list, Donnee.POIDS, days2);
        if (evo1 != null && evo1 <= -loss1) {
            COMMENTAIRE += "Perte de poids supérieure à " + loss1 + "% en " + days1 + " jours\n";
            weightLoss = true;
        }
        if (evo2 != null && evo2 <= -loss2) {
            COMMENTAIRE += "Perte de poids supérieure à " + loss2 + "% en " + days2 + " jours\n";
            weightLoss = true;
        }
        if (weightLoss3(list, loss3)) {
            COMMENTAIRE += "Perte de poids supérieure à " + loss3 + "% par rapport au poids habituel avant le début de la maladie\n";
            weightLoss = true;
        }
        return weightLoss;
    }

    public boolean weightLoss3(List<SuividonneesDTO> list, float loss3) {
        int size = list.size();
        Float firstPoids = null;
        Float lastPoids = null;
        for (int i = 0; i < size; i++) {
            if (lastPoids == null) {
                lastPoids = list.get(i).getPoids();
            }
            if (firstPoids == null) {
                int j = size - i - 1;
                if (j <= i)
                    return false;
                firstPoids = list.get(j).getPoids();
            }
            if (firstPoids != null && lastPoids != null) {
                return (lastPoids - firstPoids) / firstPoids < -loss3;
            }
        }
        return false;
    }

    public boolean phe1(List<SuividonneesDTO> list) {
        return weightLoss(list, 30, 0.05F, 183, 0.1F, 0.1F);
    }

    public boolean phe2(List<SuividonneesDTO> list) {
        if (age < 70)
            return IMC(list, 18.5f);
        return IMC(list, 22f);
    }

    public boolean phe3() {
        if (sarcopenie) {
            COMMENTAIRE += "Sarcopénie confirmée\n";
            return true;
        }
        return false;
    }

    public boolean eti1(List<SuividonneesDTO> list) {
        boolean critere = false;
        int days1 = 7, days2 = 14;
        float loss1 = 0.5f;
        Float evo1 = evolution(list, Donnee.CALORIES, days1);
        if (evo1 != null && evo1 <= -loss1) {
            COMMENTAIRE += "Réduction de la prise alimentaire supérieure à " + loss1 + "% en " + days1 + " jours";
            critere = true;
        }
        Float evo2 = evolution(list, Donnee.CALORIES, days2);
        if (evo2 != null && evo2 < 0) {
            COMMENTAIRE += "Réduction de la prise alimentaire en " + days2 + " jours";
            critere = true;
        }
        return critere;
    }

    public boolean eti2() {
        if (absorptionreduite) {
            COMMENTAIRE += "Absorption réduite\n";
            return true;
        }
        return false;
    }

    public boolean eti3() {
        if (agression) {
            COMMENTAIRE += "Situation d'agression\n";
            return true;
        }
        return false;
    }

    public boolean sev1(List<SuividonneesDTO> list) {
        return weightLoss(list, 30, 0.1F, 183, 0.15F, 0.15F);
    }

    public boolean sev2(List<SuividonneesDTO> list) {
        if (age < 70)
            return IMC(list, 17);
        return IMC(list, 20);
    }

    public boolean sev3() {
        if (albumine <= 30) {
            COMMENTAIRE += "Albuminémie < 30g/L\n";
            return true;
        }
        return false;
    }

    public boolean phenotypique(List<SuividonneesDTO> list) {
        boolean phenotypique = false;
        if (phe1(list))
            phenotypique = true;
        if (phe2(list))
            phenotypique = true;
        if (phe3())
            phenotypique = true;
        return phenotypique;
    }

    public boolean etiologique(List<SuividonneesDTO> list) {
        boolean etiologique = false;
        if (eti1(list))
            etiologique = true;
        if (eti2())
            etiologique = true;
        if (eti3())
            etiologique = true;
        return etiologique;
    }

    public boolean severe(List<SuividonneesDTO> list) {
        boolean severe = false;
        if (sev1(list))
            severe = true;
        if (sev2(list))
            severe = true;
        if (sev3())
            severe = true;
        return severe;
    }

    public AlerteDTO denutrition(List<SuividonneesDTO> allSuividonnesDTO) {
        List<SuividonneesDTO> list = new LinkedList<>();
        for (SuividonneesDTO suivi : allSuividonnesDTO) {
            if (suivi.getPatient().getId().equals(id)) {
                list.add(suivi);
            }
        }
        AlerteDTO alerteDTO = new AlerteDTO();
        alerteDTO.setDate(LocalDate.now());
        alerteDTO.setDenutrition(false);
        alerteDTO.setSeverite(false);
        alerteDTO.setCommentaire("");
        if (list.isEmpty())
            return alerteDTO;
        list.sort(new SuiviComparator());
        COMMENTAIRE = "";
        if (!phenotypique(list) && !etiologique(list))
            return alerteDTO;
        alerteDTO.setDenutrition(true);
        alerteDTO.setSeverite(severe(list));
        alerteDTO.setCommentaire(COMMENTAIRE);
        return alerteDTO;
    }



}

